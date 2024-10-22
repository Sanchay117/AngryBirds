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
    private final Texture backArrow = new Texture(Gdx.files.internal("LeftArrow.png"));
    private ShapeRenderer shapeRenderer;
    private GlyphLayout layout;

    private Stage stage;
    private Stage pauseStage;
    private Texture pauseTexture;
    private ImageButton pauseButton;
    private Texture settingsTexture;
    private ImageButton settingsButton;
    private boolean isPaused;

    public LevelScreen(Main game) {
        this.game = game;
        game.background = new Texture("lvlBG_enhanced.jpg");
    }

    private void createPauseScreen() {
        Skin skin = new Skin();
        Texture pauseBg = new Texture("bg.png");  // Your pause background image
        Image pauseBackground = new Image(pauseBg);
        pauseBackground.setSize(viewWidth/2, viewHeight/2);  // Set the size of the pause screen
        pauseStage.addActor(pauseBackground);  // Add the background to the pause stage

        // Create Resume Button
//        TextButton resumeButton = new TextButton("Resume", skin);
//        resumeButton.setPosition(viewWidth / 2 - 100, viewHeight / 2);
//        resumeButton.setSize(200, 80);
//
//        // Add Resume Button Listener
//        resumeButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                isPaused = false;  // Resume the game
//                Gdx.input.setInputProcessor(stage);  // Set input back to the game stage
//            }
//        });

        // Add the resume button to the pause stage
//        pauseStage.addActor(resumeButton);
    }

    @Override
    public void show(){
        layout = new GlyphLayout();
        stage = new Stage();
        pauseStage = new Stage();

        // Load the button textures
        pauseTexture = new Texture("pause.png");
        settingsTexture = new Texture("settings.jpg");

        // Create an ImageButton from the texture
        Skin skin = new Skin();
        skin.add("pause", pauseTexture);
        skin.add("settings", settingsTexture);

        pauseButton = new ImageButton(new ImageButton.ImageButtonStyle());
        settingsButton = new ImageButton(new ImageButton.ImageButtonStyle());
        pauseButton.getStyle().imageUp = skin.getDrawable("pause");
        settingsButton.getStyle().imageUp = skin.getDrawable("settings");

        // Set the button's position and size
        pauseButton.setPosition(viewWidth - 100, viewHeight - 100);
        pauseButton.setSize(80, 80);

        settingsButton.setPosition(viewWidth-200, viewHeight-100);
        settingsButton.setSize(80, 80);

        // Add a ClickListener to toggle the pause state
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isPaused = !isPaused;  // Toggle pause state
            }
        });

        // Add the buttons to the stage
        stage.addActor(pauseButton);
        stage.addActor(settingsButton);

        createPauseScreen();

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
            // Draw the pause screen
            pauseStage.act(delta);
            pauseStage.draw();
        } else {
            // Game is not paused, update game logic
            // Update game objects, handle input, etc.
        }

        game.batch.end();

        // Render the stage (which includes the pause button)
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewWidth = width;
        viewHeight = height;
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
        shapeRenderer.dispose();
        stage.dispose();
        pauseTexture.dispose();
    }
}
