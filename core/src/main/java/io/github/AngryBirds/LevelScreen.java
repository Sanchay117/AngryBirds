package io.github.AngryBirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
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
import com.badlogic.gdx.math.MathUtils;
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
import com.badlogic.gdx.utils.Array;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

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

    private final Texture slingShotTexture = new Texture(Gdx.files.internal("slingShot.png"));
    private final SlingShot slingShot = new SlingShot(slingShotTexture,100,128,300,270,world);

    private final Texture floor = new Texture("wood_line.png");
    private final TextureRegion floorRegion = new TextureRegion(floor);
    private final Texture wall = new Texture("wall.png");
    private final TextureRegion wallRegion = new TextureRegion(wall);
    private final Texture block = new Texture("wood_block.png");
    private final Texture stoneFloor = new Texture("stone_flat.jpg");
    private final Texture stoneWall = new Texture("stone_standing.jpg");
    private final TextureRegion stoneFloorRegion = new TextureRegion(stoneFloor);
    private final TextureRegion stoneWallRegion = new TextureRegion(stoneWall);
    private final Texture stoneBlock = new Texture("stone_block.png");
    private final TextureRegion stoneBlockRegion = new TextureRegion(stoneBlock);
    private final Texture glassFloor = new Texture("Glass_flat.jpg");
    private final Texture glassWall = new Texture("Glass_standing.jpg");
    private final TextureRegion glassFloorRegion = new TextureRegion(glassFloor);
    private final TextureRegion glassWallRegion = new TextureRegion(glassWall);

    private final ArrayList<Material> materials = new ArrayList<>();

    private final Texture pigTexture = new Texture("pig.png");
    private final ArrayList<Pig> pigs = new ArrayList<>();

    private int last;
    private int lvl;

    private boolean loaded = false;

    private AssetManager assetManager;

    public LevelScreen(Main game,int lvl) {
        this.game = game;
        game.background = new Texture("lvlBG.png");
        progressBarTexture = new Texture("bar.png");
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();

        this.lvl = lvl;

        game.music.setVolume(0.4f);

        assetManager = new AssetManager();
        assetManager.load("lvlBG.png", Texture.class);
        assetManager.load("settingsBackground.png",Texture.class);
        assetManager.finishLoading();
    }

    public LevelScreen(Main game, int level, GameState gameState) {
        this.game = game;
        game.background = new Texture("lvlBG.png");
        progressBarTexture = new Texture("bar.png");
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();

        this.lvl = level;

        loaded = true;

        game.music.setVolume(0.4f);

        assetManager = new AssetManager();
        assetManager.load("lvlBG.png", Texture.class);
        assetManager.load("settingsBackground.png",Texture.class);
        assetManager.finishLoading();

        for (Bird bird : gameState.getBirds()) {
            birds.add(new Bird(bird.getType(),new Texture(bird.getFile_name()),bird.getXStraightUp(), bird.getYStraightUp(), bird.getWidth(),bird.getHeight(),world,bird.getFile_name()));
        }
        for (Pig pig : gameState.getPigs()) {
            pigs.add(new Pig(pig.getHp(),new Texture(pig.getFile_name()), pig.getXStraightUp(), pig.getYStraightUp(), pig.getWidth(),pig.getHeight(),world, pig.getFile_name()));
        }
        for (Material material : gameState.getMaterials()) {
            materials.add(new Material(material.getName(),new TextureRegion(new Texture(material.getFile_name())),material.getXStraightUp(), material.getYStraightUp(), material.getWidth(),material.getHeight(),world, material.getFile_name()));
        }
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
                game.setScreen(new LevelScreen(game,lvl+1));
            }
        });

        restartlvlBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelScreen(game,lvl));
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
                game.setScreen(new LevelScreen(game,lvl));
            }
        });

        pauseStage.addActor(pauseBackground);
        pauseStage.addActor(playBtn);
        pauseStage.addActor(backBtn);
        pauseStage.addActor(restartBtn);
    }

    public void saveGame() {
        System.out.println("----------SAVE_GAME-----------------");
        ArrayList<Bird> b = new ArrayList<>();
        for (Bird bird : birds) {
            System.out.println(bird + " 1 ");
            b.add(new Bird(bird.getType(),bird.getFile_name(),bird.getX(), bird.getY(), bird.getWidth(),bird.getHeight()));
            System.out.println(bird + " 2 ");
        }

        System.out.println("----------BIRB ADDED-----------------");

        ArrayList<Pig> p = new ArrayList<>();
        for (Pig pig : pigs) {
            p.add(new Pig(pig.getHp(),pig.getFile_name(), pig.getX(), pig.getY(), pig.getWidth(),pig.getHeight()));
        }

        ArrayList<Material> m = new ArrayList<>();
        for (Material material : materials) {
            m.add(new Material(material.getName(),material.getFile_name(),material.getX(), material.getY(), material.getWidth(),material.getHeight()));
        }

        System.out.println("abcdefghj->0");

        GameState gameState = new GameState(lvl, score, b, p, m);

        System.out.println("abcdefghj");

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("savedGame.ser"))) {
            oos.writeObject(gameState);
            System.out.println("Game saved successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to save the game.");
        }
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

        Skin skin = new Skin();
        skin.add("pause", pauseTexture);
        skin.add("settings", settingsTexture);
        skin.add("gameOver", gameOver);

        handImage = new Image(handTexture);
        handImage.setPosition(handStartX+70, handStartY);
        handImage.setSize(80, 80);
        handImage.setVisible(isTutorialEnabled);

        tutorialToggle.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isTutorialEnabled = !isTutorialEnabled;
                tutorialToggle.getStyle().imageUp = isTutorialEnabled ?
                    tutorialSkin.getDrawable("on") : tutorialSkin.getDrawable("off");
                if(!isTutorialEnabled){
                    handImage.setSize(0,0);
                }else handImage.setSize(80,80);
            }
        });

        pauseButton = new ImageButton(new ImageButton.ImageButtonStyle());
        settingsButton = new ImageButton(new ImageButton.ImageButtonStyle());
        ImageButton gameOverBtn = new ImageButton(new ImageButton.ImageButtonStyle());
        pauseButton.getStyle().imageUp = skin.getDrawable("pause");
        settingsButton.getStyle().imageUp = skin.getDrawable("settings");

        pauseButton.setPosition(viewWidth - 100, viewHeight - 100);
        pauseButton.setSize(90, 90);

        settingsButton.setPosition(10, viewHeight - 100);
        settingsButton.setSize(90, 90);


        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isPaused = !isPaused;
            }
        });
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingsScreen(game,LevelScreen.this,lvl));
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

        Bird r1 = new Red(redTexture,0,128,100,100,world,"red.png");
        Bird r2 = new Red(redTexture,60,128,100,100,world,"red.png");
        Bird r3 = new Red(redTexture,120,128,100,100,world,"red.png");

        if(!loaded){
            if(lvl==1){
                Material floor1 = new Wood(floorRegion,viewWidth*0.33f, 128+248,350,30,world,"wood_line.png");
                Material wall1 = new Wood(wallRegion,viewWidth*0.53f,128+0,50,250,world,"wall.png");
                Material wall2 = new Wood(wallRegion,viewWidth*0.33f,128+0,50,250,world,"wall.png");

                Pig p1 = new AveragePig(pigTexture,viewWidth*0.485f,128,world,"pig.png");

                materials.add(wall1);
                materials.add(floor1);
                materials.add(wall2);

                pigs.add(p1);
            }else if(lvl==2){
                Material wall1 = new Wood(wallRegion,viewWidth*0.55f,128+0,30,250,world,"wall.png");
                Material wall2 = new Wood(wallRegion,viewWidth*0.75f,128+0,30,300,world,"wall.png");

                Material floor1 = new Stone(stoneFloorRegion,viewWidth*0.55f - 90,128+250,200,20,world,"stone_flat.jpg");
                Material floor2 = new Stone(stoneFloorRegion,viewWidth*0.75f - 140,128+300,300,20,world,"stone_flat.jpg");

                Pig p1 = new AveragePig(pigTexture,viewWidth*0.555f ,128 + 250 + 20 + 65,world,"pig.png");
                Pig p2 = new AveragePig(pigTexture,viewWidth*0.77f ,128 + 300 + 20 + 65,world,"pig.png");
                Pig p3 = new SmallPig(pigTexture,viewWidth*0.705f ,128 + 300 + 20 + 25,world,"pig.png");

                materials.add(wall1);
                materials.add(wall2);
                materials.add(floor1);
                materials.add(floor2);

                pigs.add(p1);
                pigs.add(p2);
                pigs.add(p3);
            }else{
                Material b1 = new Stone(stoneBlockRegion,viewWidth*0.55f,128,50,50,world,"stone_block.png");
                Material b2 = new Stone(stoneBlockRegion,viewWidth*0.75f,128,50,50,world,"stone_block.png");
                Material f1 = new Wood(floorRegion,viewWidth*0.525f,128+50, (viewWidth*28)/100,25,world,"wood_line.png");

                Material wL = new Glass(glassWallRegion,viewWidth*0.55f + 12,128+50+25,25,250,world,"Glass_standing.jpg");
                Material wR = new Glass(glassWallRegion,viewWidth*0.75f + 12,128+50+25,25,250,world,"Glass_standing.jpg");

                Material c1 = new Wood(floorRegion,viewWidth*0.55f + 12 - (float) (viewWidth * 6) /100,128+50+25+250,viewWidth*14/100,35,world,"wood_line.png");
                Material c2 = new Wood(floorRegion,viewWidth*0.75f + 12 - (float) (viewWidth * 6) /100,128+50+25+250,viewWidth*14/100,35,world,"wood_line.png");
                Material c3 = new Stone(stoneFloorRegion,viewWidth*0.65f - (float) (viewWidth * 5.25) /100 , 128+25+50+250+35,viewWidth*14/100,25,world,"stone_flat.jpg");

                materials.add(b1);
                materials.add(b2);
                materials.add(f1);
                materials.add(wL);
                materials.add(wR);
                materials.add(c1);
                materials.add(c2);
                materials.add(c3);

                Pig bigBoy = new BigPig(pigTexture,viewWidth*0.65f + 25,128+75 + 100,world,"pig.png");
                Pig l = new AveragePig(pigTexture,viewWidth*0.55f , 128+75+285 + 65,world,"pig.png");
                Pig r = new AveragePig(pigTexture,viewWidth*0.75f + 40, 128+75+285 + 65,world,"pig.png");
                Pig c = new SmallPig(pigTexture,viewWidth*0.65f + 25,128+75+285 + 25 + 25,world,"pig.png");

                pigs.add(bigBoy);
                pigs.add(l);
                pigs.add(r);
                pigs.add(c);
            }

            birds.add(r1);
            birds.add(r2);
            birds.add(r3);
        }



        last = birds.size()-1;

        Gdx.input.setInputProcessor(stage);

        world.setContactListener(new MyContactListener());

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

        BodyDef ceilingBodyDef = new BodyDef();
        // Set its world position
        ceilingBodyDef.position.set(new Vector2(camera.viewportWidth, camera.viewportHeight));

        // Create a body from the definition and add it to the world
        Body ceilingBody = world.createBody(ceilingBodyDef);

        // Create a polygon shape
        PolygonShape ceilingBox = new PolygonShape();
        // Set the polygon shape as a box which is twice the size of our view port and 20 high
        // (setAsBox takes half-width and half-height as arguments)
        ceilingBox.setAsBox(camera.viewportWidth, 120f);
        // Create a fixture from our polygon shape and add it to our ceiling body
        ceilingBody.createFixture(ceilingBox, 0.0f);
        // Clean up after ourselves
        ceilingBox.dispose();

        BodyDef wallDef1 = new BodyDef();
        // Set its world position
        wallDef1.position.set(new Vector2(0, 10));

        // Create a body from the definition and add it to the world
        Body wallBody1 = world.createBody(wallDef1);

        // Create a polygon shape
        PolygonShape wall1Box = new PolygonShape();
        // Set the polygon shape as a box which is twice the size of our view port and 20 high
        // (setAsBox takes half-width and half-height as arguments)
        wall1Box.setAsBox(0, camera.viewportHeight);
        // Create a fixture from our polygon shape and add it to our ground body
        wallBody1.createFixture(wall1Box, 0.0f);
        // Clean up after ourselves
        wall1Box.dispose();

        BodyDef wallDef2 = new BodyDef();
        // Set its world position
        wallDef2.position.set(new Vector2(camera.viewportWidth, 0));

        // Create a body from the definition and add it to the world
        Body wallBody2 = world.createBody(wallDef2);

        // Create a polygon shape
        PolygonShape wall2Box = new PolygonShape();
        // Set the polygon shape as a box which is twice the size of our view port and 20 high
        // (setAsBox takes half-width and half-height as arguments)
        wall2Box.setAsBox(2, camera.viewportHeight);
        // Create a fixture from our polygon shape and add it to our ground body
        wallBody2.createFixture(wall2Box, 0.0f);
        // Clean up after ourselves
        wall2Box.dispose();
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

    private void drawTrajectory(Bird testBird,ShapeRenderer shapeRenderer) {
        float stringLen =  (float) sqrt( (slingShotMiddle.y - testBird.getY())* (slingShotMiddle.y - testBird.getY()) + (slingShotMiddle.x - testBird.getX())*(slingShotMiddle.x - testBird.getX()));

        float magnitude = 0.75f * stringLen;  // Magnitude of the impulse
        float angle = MathUtils.atan2(slingShotMiddle.y - testBird.getY(), slingShotMiddle.x - testBird.getX());

        float impulseX = magnitude * MathUtils.cos(angle);
        float impulseY = magnitude * MathUtils.sin(angle);

        // Create an array or list to store trajectory points
        Array<Vector2> trajectoryPoints = new Array<>();

        float timeStep = 0.25f;
        float totalTime = 2*impulseY/g;

        trajectoryPoints.clear();
        for (float t = 0; t <= totalTime; t += timeStep) {
            float predictedX = testBird.getX() + impulseX * t;
            float predictedY = testBird.getY() + impulseY * t - (0.5f * 9.8f * t * t); // Accounting for gravity

            trajectoryPoints.add(new Vector2(predictedX, predictedY));
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(20, 20, 20, 0.8f));

        for (Vector2 point : trajectoryPoints) {
            shapeRenderer.circle(point.x, point.y, 7);
        }
    }

    private boolean thrown = false;
    private boolean left = false;
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (isTutorialEnabled) {
            updateHandAnimation(delta);
        }

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        batch.draw(game.background, 0, 0,viewWidth,viewHeight);
        game.smallFont.draw(batch,"Score: " + score,viewWidth*0.425f,viewHeight-30);
        batch.draw(progressBarTexture, viewWidth * 0.4f, viewHeight - 150, 300, 60);

        if (isPaused) {
            if (Gdx.input.getInputProcessor() != pauseStage) {
                Gdx.input.setInputProcessor(pauseStage);
            }

            game.background = assetManager.get("settingsBackground.png",Texture.class);

            pauseStage.act(delta);
            pauseStage.draw();

            game.font.draw(batch,"Game",viewWidth*0.415f,viewHeight*0.68f);
            game.font.draw(batch,"Paused",viewWidth*0.375f,viewHeight*0.54f);

            batch.end();
        }
        else if(isGameOver){

            if(Gdx.input.getInputProcessor() != gameOverStage){
                Gdx.input.setInputProcessor(gameOverStage);
            }

            game.background = assetManager.get("settingsBackground.png",Texture.class);

            gameOverStage.act(delta);
            gameOverStage.draw();

            game.mediumFont.draw(batch,"Game Over",viewWidth*0.355f,viewHeight*0.68f);
            game.smallFont.draw(batch,"Score: " + score,viewWidth*0.375f,viewHeight*0.52f);

            batch.end();
        }
        else {
            if (Gdx.input.getInputProcessor() != stage) {
                Gdx.input.setInputProcessor(stage);
            }

            game.background = assetManager.get("lvlBG.png", Texture.class);

            for(Bird bird : birds){
                batch.draw(bird.getTexture(),bird.getX()- bird.getWidth() / 2f,bird.getY()- bird.getHeight()/4f,bird.getWidth(),bird.getHeight());
            }

            for(Material material:materials){
                // Draw with rotation
                batch.draw(material.getTexture(),
                    material.getX() - material.getWidth() / 2f,  // x position (adjusting for center)
                    material.getY() - material.getHeight() / 2f, // y position (adjusting for center)
                    material.getWidth() / 2f,                    // originX (for rotation)
                    material.getHeight() / 2f,                   // originY (for rotation)
                    material.getWidth(),                         // width of the texture
                    material.getHeight(),                        // height of the texture
                    1,                                      // scaleX
                    1,                                      // scaleY
                    (float) Math.toDegrees(material.body.getAngle()));
//                System.out.println( (float) Math.toDegrees(material.body.getAngle()));
            }

            int hp = 0;
            for(Pig pig : pigs){
                if(pig.getHp()>0) batch.draw(pig.getTexture(),pig.getX() - pig.getWidth()/2f,pig.getY() - pig.getHeight()/2f,pig.getWidth(),pig.getHeight());
                else {
                    score += pig.getHP_OG() * 100;
                    pig.setHP_OG(0);
                }
                hp+=pig.getHp();
            }

            if(hp==0) isGameOver = true;

            handImage.setPosition(handX, handY);
            handImage.draw(batch, 1);

            batch.draw(SlingShot.texture,SlingShot.x,SlingShot.y,SlingShot.width,SlingShot.height);
            batch.end();

            Bird testBird = null;
            if(last>=0) {
                testBird = birds.get(last);
            }

            float thickness = 4f;

            if (dragging && testBird != null) {
                thrown = true;
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(Color.BLACK);
                // For the first line (startPoint1 to endPoint)
                for (float offset = -thickness / 2; offset <= thickness / 2; offset++) {
                    shapeRenderer.line(startPoint1.x + offset, startPoint1.y, endPoint.x + offset, endPoint.y);
                }

                // For the second line (startPoint2 to endPoint)
                for (float offset = -thickness / 2; offset <= thickness / 2; offset++) {
                    shapeRenderer.line(startPoint2.x + offset, startPoint2.y, endPoint.x + offset, endPoint.y);
                }

                shapeRenderer.end();

                testBird.setPos(endPoint.x ,endPoint.y);

                drawTrajectory(testBird, shapeRenderer);

                shapeRenderer.end();
            }else{

                if(thrown && !left && testBird != null ) {
                    float stringLen =  (float) sqrt( (slingShotMiddle.y - testBird.getY())* (slingShotMiddle.y - testBird.getY()) + (slingShotMiddle.x - testBird.getX())*(slingShotMiddle.x - testBird.getX()));

                    float magnitude = 0.75f * stringLen;  // Magnitude of the impulse
                    float angle = MathUtils.atan2(slingShotMiddle.y - testBird.getY(), slingShotMiddle.x - testBird.getX());

                    float impulseX = magnitude * MathUtils.cos(angle);
                    float impulseY = magnitude * MathUtils.sin(angle);

                    testBird.body.setLinearVelocity(impulseX,impulseY);
                    testBird.body.setLinearDamping(0.0f);
                    testBird.setPos(testBird.getX() + 10, testBird.getY());
                    left = true;
                    System.out.println("Velocity: " + testBird.body.getLinearVelocity());
                }else{
//                    if(!left && testBird!=null) testBird.setPos(slingShotMiddle.x - testBird.width/4f - 20,slingShotMiddle.y - testBird.height/4f - 10);
                    if(left){
                        if(last>=0){
                            last--;
                            thrown = false;
                            left = false;
                        }
                    }
                }
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

            batch.begin();
            if(testBird!=null) batch.draw(testBird.getTexture(), testBird.getX()- testBird.getWidth() / 2f,testBird.getY()- testBird.getHeight()/4f, testBird.getWidth(), testBird.getHeight());
            batch.end();

            stage.act(delta);
            stage.draw();
        }

        world.step(1/60f, 6, 2);
        debugRenderer.render(world, camera.combined);

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
        world.dispose();
        debugRenderer.dispose();
        assetManager.dispose();
    }
}
