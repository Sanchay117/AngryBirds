package io.github.AngryBirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

import static java.lang.Math.sqrt;

public class LevelScreen extends ScreenAdapter {

    private final Main game;
    private int viewHeight = Gdx.graphics.getHeight();
    private int viewWidth = Gdx.graphics.getWidth();

    SpriteBatch batch;
    ShapeRenderer shapeRenderer;

    private Stage stage;
    private Stage pauseStage;
    private Stage gameOverStage;
    private Texture pauseTexture;
    private ImageButton pauseButton;
    private Texture settingsTexture;
    private ImageButton settingsButton;
    private Texture progressBarTexture;
    private boolean isPaused;
    private boolean isGameOver;

    private int score;

    private boolean isTutorialEnabled = true;
    private ImageButton tutorialToggle;
    private Texture tutorialOnTexture = new Texture("tutOn.png");
    private Texture tutorialOffTexture = new Texture("tutOff.png");
    private Texture handTexture = new Texture("hand.png");
    private Image handImage;

    private float handX, handY;
    private float handStartX = 90, handStartY = 128;
    private float handEndX = 180, handEndY = 265;

    private float handAnimationTime = 0;
    private float handAnimationDuration = 2.0f;
    private boolean handVisible = true;

    private final World world = new World(new Vector2(0, -9.8f), true);
    private final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    private final OrthographicCamera camera = new OrthographicCamera();

    private final Texture redTexture = new Texture(Gdx.files.internal("red.png"));
    private final ArrayList<Bird> birds = new ArrayList<>();
    private final Bird testBird = new Red(redTexture,120,128,100,100,world);

    private final Texture slingShotTexture = new Texture(Gdx.files.internal("slingShot.png"));
    private final SlingShot slingShot = new SlingShot(slingShotTexture,100,128,300,270,world);

    private final Texture floor = new Texture("wood_line.png");
    private final Texture wall = new Texture("wall.png");
    private final Texture block = new Texture("wood_block.png");
    private final ArrayList<Material> materials = new ArrayList<>();
    private final Texture pigTexture = new Texture("pig.png");
    private final ArrayList<Pig> pigs = new ArrayList<>();

    public LevelScreen(Main game) {
        this.game = game;
        game.background = new Texture("lvlBG.png");
        progressBarTexture = new Texture("bar.png");
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();

        game.music.setVolume(0.4f);
    }

    public void createGameOverScreen(){
        Skin skin = new Skin();
        Texture pauseBg = new Texture("pausepanel.png");
        Image pauseBackground = new Image(pauseBg);
        pauseBackground.setSize(viewWidth/2, viewHeight/2);
        pauseBackground.setPosition(viewWidth/4, viewHeight/4);

        Texture forward = new Texture("next1.png");
        Texture back = new Texture("menu.png");
        Texture restart = new Texture("restart1.png");

        skin.add("next",forward);
        skin.add("menu",back);
        skin.add("restart",restart);

        ImageButton nextlvlBtn = new ImageButton(new ImageButton.ImageButtonStyle());
        ImageButton menuBtn = new ImageButton(new ImageButton.ImageButtonStyle());
        ImageButton restartlvlBtn = new ImageButton(new ImageButton.ImageButtonStyle());
        nextlvlBtn.getStyle().imageUp = skin.getDrawable("next");
        menuBtn.getStyle().imageUp = skin.getDrawable("menu");
        restartlvlBtn.getStyle().imageUp = skin.getDrawable("restart");


        menuBtn.setSize(135, 135);
        menuBtn.setPosition(viewWidth*0.33f, viewHeight*0.28f);

        nextlvlBtn.setSize(135, 135);
        nextlvlBtn.setPosition(viewWidth*0.57f, viewHeight*0.28f);

        restartlvlBtn.setSize(135,135);
        restartlvlBtn.setPosition(viewWidth*0.45f, viewHeight*0.28f);

        menuBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainScreen(game));
            }
        });

        nextlvlBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelScreen(game));
            }
        });

        restartlvlBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelScreen(game));
            }
        });

        gameOverStage.addActor(pauseBackground);
        gameOverStage.addActor(nextlvlBtn);
        gameOverStage.addActor(menuBtn);
        gameOverStage.addActor(restartlvlBtn);
    }

    private void createPauseScreen() {
        Skin skin = new Skin();
        Texture pauseBg = new Texture("pausepanel.png");
        Image pauseBackground = new Image(pauseBg);
        pauseBackground.setSize(viewWidth/2, viewHeight/2);
        pauseBackground.setPosition(viewWidth/4, viewHeight/4);

        Texture play = new Texture("resume.png");
        Texture back = new Texture("back1.png");
        Texture restart = new Texture("restart.png");

        skin.add("play",play);
        skin.add("back",back);
        skin.add("restart",restart);

        ImageButton playBtn = new ImageButton(new ImageButton.ImageButtonStyle());
        ImageButton backBtn = new ImageButton(new ImageButton.ImageButtonStyle());
        ImageButton restartBtn = new ImageButton(new ImageButton.ImageButtonStyle());
        playBtn.getStyle().imageUp = skin.getDrawable("play");
        backBtn.getStyle().imageUp = skin.getDrawable("back");
        restartBtn.getStyle().imageUp = skin.getDrawable("restart");

        backBtn.setSize(125, 125);
        backBtn.setPosition(viewWidth*0.33f, viewHeight*0.27f);

        playBtn.setSize(125, 125);
        playBtn.setPosition(viewWidth*0.57f, viewHeight*0.27f);

        restartBtn.setSize(125,125);
        restartBtn.setPosition(viewWidth*0.45f, viewHeight*0.27f);

        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainScreen(game));
            }
        });

        playBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isPaused=!isPaused;
            }
        });

        restartBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelScreen(game));
            }
        });

        pauseStage.addActor(pauseBackground);
        pauseStage.addActor(playBtn);
        pauseStage.addActor(backBtn);
        pauseStage.addActor(restartBtn);
    }

    @Override
    public void show(){
        stage = new Stage();
        pauseStage = new Stage();
        gameOverStage = new Stage();

        camera.viewportWidth = Gdx.graphics.getWidth(); // Convert screen width to meters
        camera.viewportHeight = Gdx.graphics.getHeight() ; // Convert screen height to meters

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        camera.update();

        handImage = new Image(handTexture);
        handImage.setSize(300, 400);
        handX = handStartX;
        handY = handStartY;

        pauseTexture = new Texture("pause.png");
        settingsTexture = new Texture("settings.png");
        Texture gameOver = new Texture("gameOver.png");

        Skin tutorialSkin = new Skin();
        tutorialSkin.add("on", tutorialOnTexture);
        tutorialSkin.add("off", tutorialOffTexture);

        tutorialToggle = new ImageButton(new ImageButton.ImageButtonStyle());
        tutorialToggle.getStyle().imageUp = tutorialSkin.getDrawable("on");

        tutorialToggle.setPosition(0, -50);  // Centered above Game Over
        // Position above GameOver button
        tutorialToggle.setSize(190, 190);

        tutorialToggle.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isTutorialEnabled = !isTutorialEnabled;
                tutorialToggle.getStyle().imageUp = isTutorialEnabled ?
                        tutorialSkin.getDrawable("on") : tutorialSkin.getDrawable("off");
                handImage.setVisible(isTutorialEnabled);
            }
        });


        Skin skin = new Skin();
        skin.add("pause", pauseTexture);
        skin.add("settings", settingsTexture);
        skin.add("gameOver", gameOver);

        handImage = new Image(handTexture);
        handImage.setPosition(handStartX+70, handStartY);
        handImage.setSize(80, 80);
        handImage.setVisible(isTutorialEnabled);

        pauseButton = new ImageButton(new ImageButton.ImageButtonStyle());
        settingsButton = new ImageButton(new ImageButton.ImageButtonStyle());
        ImageButton gameOverBtn = new ImageButton(new ImageButton.ImageButtonStyle());
        pauseButton.getStyle().imageUp = skin.getDrawable("pause");
        settingsButton.getStyle().imageUp = skin.getDrawable("settings");
        gameOverBtn.getStyle().imageUp = skin.getDrawable("gameOver");

        pauseButton.setPosition(viewWidth - 100, viewHeight - 100);
        pauseButton.setSize(90, 90);

        settingsButton.setPosition(10, viewHeight - 100);
        settingsButton.setSize(90, 90);

        gameOverBtn.setPosition(viewWidth-200,-50);
        gameOverBtn.setSize(200,200);


        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isPaused = !isPaused;
            }
        });
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingsScreen(game));
            }
        });
        gameOverBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isGameOver=!isGameOver;
            }
        });

        stage.addActor(pauseButton);
        stage.addActor(settingsButton);
        stage.addActor(gameOverBtn);
        stage.addActor(tutorialToggle);
        stage.addActor(handImage);

        stage.draw();

        createPauseScreen();
        createGameOverScreen();

        Bird r1 = new Red(redTexture,0,128,100,100,world);
        Bird r2 = new Red(redTexture,60,128,100,100,world);

        Material floor1 = new Wood(floor,viewWidth*0.43f, 128+248,300,30);
        Material wall1 = new Wood(wall,viewWidth*0.51f,128+0,50,250);

        Pig p1 = new AveragePig(pigTexture,viewWidth*0.485f,128+276,130,130);

        materials.add(wall1);
        materials.add(floor1);

        birds.add(r1);
        birds.add(r2);

        pigs.add(p1);

        Gdx.input.setInputProcessor(stage);

        // Create our body definition
        BodyDef groundBodyDef = new BodyDef();
        // Set its world position
        groundBodyDef.position.set(new Vector2(0, 10));

        // Create a body from the definition and add it to the world
        Body groundBody = world.createBody(groundBodyDef);

        // Create a polygon shape
        PolygonShape groundBox = new PolygonShape();
        // Set the polygon shape as a box which is twice the size of our view port and 20 high
        // (setAsBox takes half-width and half-height as arguments)
        groundBox.setAsBox(camera.viewportWidth, 120f);
        // Create a fixture from our polygon shape and add it to our ground body
        groundBody.createFixture(groundBox, 0.0f);
        // Clean up after ourselves
        groundBox.dispose();
    }

    private final float g = 9.8f;

    private Vector2 startPoint1 = new Vector2(228, 346);
    private Vector2 startPoint2 = new Vector2(293, 356);
    private Vector2 slingShotMiddle = new Vector2(259,354);
    private Vector2 endPoint = new Vector2();   // Point B (moving)
    private boolean dragging = false;

    private boolean inRange(float x, float y) {
        if(200f <= x && x <= 300f && 300f <= y && y <= 400f) {
            return true;
        }
        return false;
    }

    private float getX(float cos,float x,float velocity_initial,float t) {
        return x + cos*velocity_initial*t;
    }

    private float getY(float sin,float y,float velocity_initial,float t) {
        return (float) (y + sin*velocity_initial*t - 0.5*g*t*t);
    }

    private float getInitialVelocity(){
        float stringLength = (float) sqrt((endPoint.x-slingShotMiddle.x)*(endPoint.x-slingShotMiddle.x) + (endPoint.y-slingShotMiddle.y)*(endPoint.y-slingShotMiddle.y) );

        if(endPoint.x>slingShotMiddle.x) return -1*stringLength/2;
        return stringLength/2;
    }

    private void drawTrajectory(float x,float y,float velocity_initial,ShapeRenderer shapeRenderer) {
        float tan = y/x;
        float hypotenuse = (float) (float) sqrt((endPoint.x-slingShotMiddle.x)*(endPoint.x-slingShotMiddle.x) + (endPoint.y-slingShotMiddle.y)*(endPoint.y-slingShotMiddle.y) );;

        float sin = (y)/hypotenuse;
        float cos = x/hypotenuse;

        float timeOfFlight = 2*velocity_initial*sin/g;
        float timeStep = 0.35f;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(20, 20, 20, 0.8f)); // Semi-transparent red

        for (float t = 0; t <= timeOfFlight; t += timeStep) {
            float xCoord = getX(cos, x, velocity_initial, t);
            float yCoord = getY(sin, y, velocity_initial, t);

            shapeRenderer.circle(xCoord, yCoord, 7);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//        if (isTutorialEnabled) {
//            updateHandAnimation(delta);
//        }

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        batch.draw(game.background, 0, 0,viewWidth,viewHeight);
        game.smallFont.draw(batch,"Score: 0",viewWidth*0.425f,viewHeight-30);
        batch.draw(progressBarTexture, viewWidth * 0.4f, viewHeight - 150, 300, 60);

        if (isPaused) {
            if (Gdx.input.getInputProcessor() != pauseStage) {
                Gdx.input.setInputProcessor(pauseStage);
            }

            game.background = new Texture("settingsBackground.png");

            pauseStage.act(delta);
            pauseStage.draw();

            game.font.draw(batch,"Game",viewWidth*0.415f,viewHeight*0.68f);
            game.font.draw(batch,"Paused",viewWidth*0.375f,viewHeight*0.54f);


        }
        else if(isGameOver){

            if(Gdx.input.getInputProcessor() != gameOverStage){
                Gdx.input.setInputProcessor(gameOverStage);
            }

            game.background = new Texture("settingsBackground.png");

            gameOverStage.act(delta);
            gameOverStage.draw();

            game.mediumFont.draw(batch,"Game Over",viewWidth*0.355f,viewHeight*0.68f);
            game.mediumFont.draw(batch,"Score: 0",viewWidth*0.375f,viewHeight*0.52f);

        }
        else {
            if (Gdx.input.getInputProcessor() != stage) {
                Gdx.input.setInputProcessor(stage);
            }

            game.background = new Texture("lvlBG.png");

            for(Bird bird : birds){
                batch.draw(bird.texture,bird.getX(),bird.getY(),bird.width,bird.height);
            }

            for(Material material:materials){
                batch.draw(material.texture,material.x,material.y,material.width,material.height);
            }

            for(Pig pig : pigs){
                batch.draw(pig.texture,pig.x,pig.y,pig.width,pig.height);
            }

//            handImage.setPosition(handX, handY);
//            handImage.draw(batch, 1);

            batch.draw(SlingShot.texture,SlingShot.x,SlingShot.y,SlingShot.width,SlingShot.height);

            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.BLACK);

            float thickness = 4f;

            if (dragging) {
                // For the first line (startPoint1 to endPoint)
                for (float offset = -thickness / 2; offset <= thickness / 2; offset++) {
                    shapeRenderer.line(startPoint1.x + offset, startPoint1.y, endPoint.x + offset, endPoint.y);
                }

                // For the second line (startPoint2 to endPoint)
                for (float offset = -thickness / 2; offset <= thickness / 2; offset++) {
                    shapeRenderer.line(startPoint2.x + offset, startPoint2.y, endPoint.x + offset, endPoint.y);
                }

                shapeRenderer.end();

                testBird.setPos(endPoint.x - testBird.width/4f - 20,endPoint.y - testBird.height/4f + 10);

                drawTrajectory(endPoint.x, endPoint.y, getInitialVelocity(), shapeRenderer);

                shapeRenderer.end();
            }else{
                testBird.setPos(slingShotMiddle.x - testBird.width/4f - 20,slingShotMiddle.y - testBird.height/4f - 10);
            }

            // Update the end point as the user moves
            if (Gdx.input.isTouched()) {
                float touchX = Gdx.input.getX();
                float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

                if (!dragging && inRange(touchX, touchY)) {
                    // Start dragging from the current touch position
                    dragging = true;
                }
                // Update the end point (B) as the user moves
                endPoint.set(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            } else {
                dragging = false;
            }

            batch.draw(testBird.texture, testBird.getX(), testBird.getY(), testBird.width, testBird.height);

            stage.act(delta);
            stage.draw();
        }

        world.step(1/60f, 6, 2);
        debugRenderer.render(world, camera.combined);

        shapeRenderer.end();
        batch.end();
    }

    private void updateHandAnimation(float delta) {
        handAnimationTime += delta;
        float animationProgress = handAnimationTime / handAnimationDuration;

        if (animationProgress <= 1.0f) {
            handX = handStartX + (handEndX - handStartX) * animationProgress;
            handY = handStartY + (handEndY - handStartY) * animationProgress;
        } else {
            handVisible = false;

            if (handAnimationTime >= handAnimationDuration + 1.0f) {
                handAnimationTime = 0;
                handX = handStartX;
                handY = handStartY;
                handVisible = true;
            }
        }
    }


    @Override
    public void resize(int width, int height) {
        viewWidth = width;
        viewHeight = height;
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
        stage.dispose();
        pauseStage.dispose();
        gameOverStage.dispose();
        pauseTexture.dispose();
        batch.dispose();
        shapeRenderer.dispose();
    }
}
