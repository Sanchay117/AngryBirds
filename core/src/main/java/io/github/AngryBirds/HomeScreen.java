package io.github.AngryBirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

public class HomeScreen implements Screen {
    private final io.github.AngryBirds.Main game;
    private OrthographicCamera camera;
    private Texture background;
    private BitmapFont titleFont, buttonFont;
    private Stage stage;
    private ImageButton playButton, quitButton, settingsButton;
    private SpriteBatch batch;

    public HomeScreen(io.github.AngryBirds.Main game) {
        this.game = game;
        batch = new SpriteBatch();

        // Setup the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);  // Set a fixed resolution

        // Load the background texture
        background = new Texture(Gdx.files.internal("angry_birds_bg.jpg"));

        // Generate fonts from TTF files
        FreeTypeFontGenerator generator1 = new FreeTypeFontGenerator(Gdx.files.internal("Comicartoon-3DExtrude.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter titleParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        titleParameter.size = 72;  // Title font size
        titleParameter.color = Color.RED;
        titleFont = generator1.generateFont(titleParameter);  // Generate title font
        generator1.dispose();

        FreeTypeFontGenerator generator2 = new FreeTypeFontGenerator(Gdx.files.internal("Comicartoon-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter buttonParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        buttonParameter.size = 36;  // Button font size
        buttonParameter.color = Color.WHITE;
        buttonFont = generator2.generateFont(buttonParameter);  // Generate button font
        generator2.dispose();

        // Initialize the stage
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // Create buttons with textures
        playButton = createButton("play.png", 400, 240);
        quitButton = createButton("play.png", 400, 160);
        settingsButton = createButton("settings.jpg", 400, 80);

        // Add buttons to the stage
        stage.addActor(playButton);
        stage.addActor(quitButton);
        stage.addActor(settingsButton);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();  // Quit the game
            }
        });

    settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingsScreen(game));  // Switch to SettingsScreen
            }
        });
    }

    private ImageButton createButton(String texturePath, float x, float y) {
        Texture buttonTexture = new Texture(Gdx.files.internal(texturePath));
        Drawable drawable = new TextureRegionDrawable(buttonTexture);
        ImageButton button = new ImageButton(drawable);
        button.setPosition(x - button.getWidth() / 2, y - button.getHeight() / 2);  // Center the button
        return button;
    }

    @Override
    public void show() {
        // Not needed for now
    }

    @Override
    public void render(float delta) {
        // Clear the screen with a black background
        ScreenUtils.clear(0, 0, 0, 1);

        // Update the camera
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Draw the background and title
        batch.begin();
        batch.draw(background, 0, 0, 800, 480);  // Draw background full screen
        titleFont.draw(batch, "Angry Birds", 400 - 150, 400);  // Draw title at the center
        batch.end();

        // Draw the buttons (handled by the stage)
        stage.act(delta);
        stage.draw();
        
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // Not needed for now
    }

    @Override
    public void resume() {
        // Not needed for now
    }

    @Override
    public void hide() {
        // Not needed for now
    }

    @Override
    public void dispose() {
        // Dispose of assets
        batch.dispose();
        background.dispose();
        titleFont.dispose();
        buttonFont.dispose();
        stage.dispose();
    }
}
