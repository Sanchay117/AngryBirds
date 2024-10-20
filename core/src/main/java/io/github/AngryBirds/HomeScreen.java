package com.mygdx.angrybirds;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygame.MainGame;

public class HomeScreen implements Screen {
    private final MainGame game;
    private SpriteBatch batch;
    private Texture background;
    private BitmapFont titleFont;
    private Stage stage;

    public HomeScreen(MainGame game) {
        this.game = game;
        batch = new SpriteBatch();
        background = new Texture("angry_bird_bg.jpg");
        titleFont = new BitmapFont(); // Use a custom font if you want
        
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        
        createButtons();
    }

    private void createButtons() {
        Skin skin = new Skin();

        // Play Button
        Texture playTexture = new Texture("play.png");
        ImageButton playButton = new ImageButton(new Image(playTexture).getDrawable());
        playButton.setPosition(Gdx.graphics.getWidth() / 2f - playTexture.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayScreen(game)); // Navigate to the PlayScreen
            }
        });

        // Settings Button
        Texture settingsTexture = new Texture("settings.jpg");
        ImageButton settingsButton = new ImageButton(new Image(settingsTexture).getDrawable());
        settingsButton.setPosition(Gdx.graphics.getWidth() / 2f - settingsTexture.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - 100);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingsScreen(game)); // Navigate to SettingsScreen
            }
        });

        // Quit Button
        Texture quitTexture = new Texture("play.png");
        ImageButton quitButton = new ImageButton(new Image(quitTexture).getDrawable());
        quitButton.setPosition(Gdx.graphics.getWidth() / 2f - quitTexture.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - 200);
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit(); // Quit the game
            }
        });

        // Add buttons to the stage
        stage.addActor(playButton);
        stage.addActor(settingsButton);
        stage.addActor(quitButton);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        titleFont.draw(batch, "Angry Birds", Gdx.graphics.getWidth() / 2f - 50, Gdx.graphics.getHeight() - 50); // Center title at top
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        titleFont.dispose();
        stage.dispose();
    }
}
