package io.github.AngryBirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

public class LevelScreen extends ScreenAdapter {

    private final Main game;
    private int viewHeight = Gdx.graphics.getHeight();
    private int viewWidth = Gdx.graphics.getWidth();

    private Stage stage;
    private Stage pauseStage;
    private Stage gameOverStage;
    private Texture pauseTexture;
    private ImageButton pauseButton;
    private Texture settingsTexture;
    private ImageButton settingsButton;
    private boolean isPaused;
    private boolean isGameOver;

    private int score;

    private final Texture redTexture = new Texture(Gdx.files.internal("red.png"));
    private final ArrayList<Bird> birds = new ArrayList<>();
    private final Texture slingShotTexture = new Texture(Gdx.files.internal("slingShot.png"));
    private final SlingShot slingShot = new SlingShot(slingShotTexture,100,128,200,200);
    private final Texture floor = new Texture("wood_line.png");
    private final Texture wall = new Texture("wall.png");
    private final Texture block = new Texture("wood_block.png");
    private final ArrayList<Material> materials = new ArrayList<>();
    private final Texture pigTexture = new Texture("pig.png");
    private final ArrayList<Pig> pigs = new ArrayList<>();

    public LevelScreen(Main game) {
        this.game = game;
        game.background = new Texture("lvlBG.png");
    }

    public void createGameOverScreen(){
        Skin skin = new Skin();
        Texture pauseBg = new Texture("pausepanel.png");
        Image pauseBackground = new Image(pauseBg);
        pauseBackground.setSize(viewWidth/2, viewHeight/2);
        pauseBackground.setPosition(viewWidth/4, viewHeight/4);

        Texture forward = new Texture("forward.png");
        Texture back = new Texture("back1.png");
        Texture restart = new Texture("restart.png");

        skin.add("play",forward);
        skin.add("back",back);
        skin.add("restart",restart);

        ImageButton nextBtn = new ImageButton(new ImageButton.ImageButtonStyle());
        ImageButton backBtn = new ImageButton(new ImageButton.ImageButtonStyle());
        ImageButton restartBtn = new ImageButton(new ImageButton.ImageButtonStyle());
        nextBtn.getStyle().imageUp = skin.getDrawable("play");
        backBtn.getStyle().imageUp = skin.getDrawable("back");
        restartBtn.getStyle().imageUp = skin.getDrawable("restart");

        backBtn.setSize(135, 135);
        backBtn.setPosition(viewWidth*0.33f, viewHeight*0.28f);

        nextBtn.setSize(135, 135);
        nextBtn.setPosition(viewWidth*0.57f, viewHeight*0.28f);

        restartBtn.setSize(135,135);
        restartBtn.setPosition(viewWidth*0.45f, viewHeight*0.28f);

        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainScreen(game));
            }
        });

        nextBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelScreen(game));
            }
        });

        restartBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelScreen(game));
            }
        });

        gameOverStage.addActor(pauseBackground);
        gameOverStage.addActor(nextBtn);
        gameOverStage.addActor(backBtn);
        gameOverStage.addActor(restartBtn);
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

        backBtn.setSize(135, 135);
        backBtn.setPosition(viewWidth*0.33f, viewHeight*0.25f);

        playBtn.setSize(135, 135);
        playBtn.setPosition(viewWidth*0.57f, viewHeight*0.25f);

        restartBtn.setSize(135,135);
        restartBtn.setPosition(viewWidth*0.45f, viewHeight*0.25f);

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

        // Load the button textures
        pauseTexture = new Texture("pause.png");
        settingsTexture = new Texture("settings.png");
        Texture gameOver = new Texture("gameOver.png");

//        // Load the entire sprite sheet
//        Texture spriteSheet = new Texture(Gdx.files.internal("woods.png"));
//
//        // Split into blocks, assuming each block is 64x64 pixels
//        TextureRegion[][] blocks = TextureRegion.split(spriteSheet, 64, 64);
//
//        // Access specific blocks (e.g., blocks[0][1] for the second block in the first row)
//        TextureRegion block1 = blocks[0][0];
//        TextureRegion block2 = blocks[0][1];

        // Create an ImageButton from the texture
        Skin skin = new Skin();
        skin.add("pause", pauseTexture);
        skin.add("settings", settingsTexture);
        skin.add("gameOver", gameOver);

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

        stage.draw();

        createPauseScreen();
        createGameOverScreen();

        Bird r1 = new Red(redTexture,0,128,100,100);
        Bird r2 = new Red(redTexture,60,128,100,100);

        Material floor1 = new Wood(floor,viewWidth*0.43f, 128+248,300,30);
        Material wall1 = new Wood(wall,viewWidth*0.51f,128+0,50,250);

        Pig p1 = new AveragePig(pigTexture,viewWidth*0.485f,128+276,130,130);

        materials.add(wall1);
        materials.add(floor1);

        birds.add(r1);
        birds.add(r2);

        pigs.add(p1);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(game.background, 0, 0,viewWidth,viewHeight);
        game.smallFont.draw(game.batch,"Score: 0",viewWidth*0.425f,viewHeight-30);

        if (isPaused) {
            if (Gdx.input.getInputProcessor() != pauseStage) {
                Gdx.input.setInputProcessor(pauseStage);
            }

            game.background = new Texture("lvlBGblur.png");

            pauseStage.act(delta);
            pauseStage.draw();

            game.font.draw(game.batch,"Game",viewWidth*0.4f,viewHeight*0.7f);
            game.font.draw(game.batch,"Paused",viewWidth*0.35f,viewHeight*0.6f);


        }else if(isGameOver){

            if(Gdx.input.getInputProcessor() != gameOverStage){
                Gdx.input.setInputProcessor(gameOverStage);
            }

            game.background = new Texture("lvlBGblur.png");

            gameOverStage.act(delta);
            gameOverStage.draw();

            game.mediumFont.draw(game.batch,"Game Over",viewWidth*0.355f,viewHeight*0.68f);
            game.mediumFont.draw(game.batch,"Score: 0",viewWidth*0.375f,viewHeight*0.52f);

        }else {
            if (Gdx.input.getInputProcessor() != stage) {
                Gdx.input.setInputProcessor(stage);
            }

            game.background = new Texture("lvlBG.png");

            for(Bird bird : birds){
                game.batch.draw(bird.texture,bird.x,bird.y,bird.width,bird.height);
            }

            game.batch.draw(slingShot.texture,slingShot.x,slingShot.y,slingShot.width,slingShot.height);

            for(Material material:materials){
                game.batch.draw(material.texture,material.x,material.y,material.width,material.height);
            }

            for(Pig pig : pigs){
                game.batch.draw(pig.texture,pig.x,pig.y,pig.width,pig.height);
            }

            stage.act(delta);
            stage.draw();
        }

        game.batch.end();
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
    }
}
