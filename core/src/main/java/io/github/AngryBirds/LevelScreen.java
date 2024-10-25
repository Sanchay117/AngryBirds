package io.github.AngryBirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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

    private Texture red;
    private Texture pig;
    private Texture wall;
    private Texture floor;
    private Texture slingshot;

    public LevelScreen(Main game) {
        this.game = game;
        game.background = new Texture("lvlBG.jpg");

        red = new Texture("red.png");
        pig = new Texture("pig.png");
        wall = new Texture("wood_wall.png");
        floor = new Texture("wood.png");
        slingshot = new Texture("slingShot.png");
    }

    public void createGameOverScreen(){
        Skin skin = new Skin();
        Texture pauseBg = new Texture("pausepanel.png");  // Your pause background image
        Image pauseBackground = new Image(pauseBg);
        pauseBackground.setSize(viewWidth/2, viewHeight/2);  // Set the size of the pause screen
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
        backBtn.setPosition(viewWidth*0.33f, viewHeight*0.28f);

        playBtn.setSize(160, 160);
        playBtn.setPosition(viewWidth*0.55f, viewHeight*0.25f);

        restartBtn.setSize(135,135);
        restartBtn.setPosition(viewWidth*0.45f, viewHeight*0.28f);

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

        gameOverStage.addActor(pauseBackground);
        gameOverStage.addActor(playBtn);
        gameOverStage.addActor(backBtn);
        gameOverStage.addActor(restartBtn);
    }

    private void createPauseScreen() {
        Skin skin = new Skin();
        Texture pauseBg = new Texture("pausepanel.png");  // Your pause background image
        Image pauseBackground = new Image(pauseBg);
        pauseBackground.setSize(viewWidth/2, viewHeight/2);  // Set the size of the pause screen
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
        backBtn.setPosition(viewWidth*0.33f, viewHeight*0.28f);

        playBtn.setSize(135, 135);
        playBtn.setPosition(viewWidth*0.57f, viewHeight*0.28f);

        restartBtn.setSize(135,135);
        restartBtn.setPosition(viewWidth*0.45f, viewHeight*0.28f);

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

        // Set the button's position and size
        pauseButton.setPosition(viewWidth - 100, viewHeight - 100);
        pauseButton.setSize(80, 80);

        settingsButton.setPosition(viewWidth-200, viewHeight-100);
        settingsButton.setSize(80, 80);

        gameOverBtn.setPosition(viewWidth-200,viewHeight);
        gameOverBtn.setSize(200,200);

        // Add a ClickListener to toggle the pause state
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isPaused = !isPaused;  // Toggle pause state
            }
        });

        gameOverBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isGameOver=!isGameOver;
            }
        });

        // Add the buttons to the stage
        stage.addActor(pauseButton);
        stage.addActor(settingsButton);
        stage.addActor(gameOverBtn);

        createPauseScreen();
        createGameOverScreen();

        // Set the input processor to the stage
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(game.background, 0, 0,viewWidth,viewHeight);
        game.smallFont.draw(game.batch,"Score: 0",viewWidth*0.425f,viewHeight-30);

        if (isPaused) {
            if (Gdx.input.getInputProcessor() != pauseStage) {
                Gdx.input.setInputProcessor(pauseStage);
            }

            game.background = new Texture("pauseBG.png");

            // Draw the pause screen
            pauseStage.act(delta);
            pauseStage.draw();

            game.font.draw(game.batch,"Game",viewWidth*0.415f,viewHeight*0.68f);
            game.font.draw(game.batch,"Paused",viewWidth*0.375f,viewHeight*0.54f);


        }else if(isGameOver){

            if(Gdx.input.getInputProcessor() != gameOverStage){
                Gdx.input.setInputProcessor(gameOverStage);
            }

            game.background = new Texture("pauseBG.png");

            // Draw the pause screen
            gameOverStage.act(delta);
            gameOverStage.draw();

            game.font.draw(game.batch,"Game",viewWidth*0.415f,viewHeight*0.68f);
            game.font.draw(game.batch,"Over",viewWidth*0.375f,viewHeight*0.54f);

        }else {
            // Game is not paused, update game logic
            // Update game objects, handle input, etc.

            // Switch input processor back to game stage when not paused
            if (Gdx.input.getInputProcessor() != stage) {
                Gdx.input.setInputProcessor(stage);
            }

            game.background = new Texture("lvlBG.jpg");

            game.batch.draw(red,0,viewHeight*0.22f,100,100);
            game.batch.draw(red,60,viewHeight*0.22f,100,100);
            game.batch.draw(slingshot,100,viewHeight*0.22f,150,200);

            game.batch.draw(floor,viewWidth*0.43f,viewHeight*0.17f + 250,300,30);
            game.batch.draw(pig,viewWidth*0.43f + 50,viewHeight*0.17f + 250 + 30,125,100);
            game.batch.draw(wall,viewWidth*0.5f,viewHeight*0.19f,50,250);

            // Game is not paused, update game logic
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
