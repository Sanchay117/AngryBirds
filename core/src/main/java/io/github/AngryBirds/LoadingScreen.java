package com.mygdx.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;

public class LoadingScreen implements Screen {
    private final MainGame game;
    private SpriteBatch batch;
    private Texture background;
    private Texture loadingBarTexture;
    private BitmapFont font;
    private ProgressBar loadingBar;
    private Stage stage;
    private float progress;

    public LoadingScreen(MainGame game) {
        this.game = game;
        batch = new SpriteBatch();
        background = new Texture("loading_bg.jpg");
        loadingBarTexture = new Texture("loading.jpg");
        
        font = new BitmapFont(); 
        font.setColor(Color.GREEN);
        
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        createLoadingBar();
    }

    private void createLoadingBar() {
        Skin skin = new Skin();
        ProgressBarStyle style = new ProgressBarStyle();
        
        style.background = new TextureRegionDrawable(new Texture("empty_bar.jpg"));
        
        style.knobBefore = new TextureRegionDrawable(new Texture("loading.jpg")); 

        loadingBar = new ProgressBar(0, 1, 0.01f, false, style);
        loadingBar.setSize(400, 50); 
        loadingBar.setPosition(Gdx.graphics.getWidth() / 2f - 200, Gdx.graphics.getHeight() / 2f - 50); 
        loadingBar.setValue(0);

        stage.addActor(loadingBar);
    }

    @Override
    public void show() {
       
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                game.setScreen(new MenuScreen(game)); 
            }
        }, 2);
    }

    @Override
    public void render(float delta) {
        progress += delta / 2; 
        if (progress > 1) progress = 1;
        loadingBar.setValue(progress);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        font.draw(batch, "LOADING...", Gdx.graphics.getWidth() / 2f - 190, Gdx.graphics.getHeight() / 2f + 20); 
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
        font.dispose();
        loadingBarTexture.dispose();
        stage.dispose();
    }
}
