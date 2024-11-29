package io.github.AngryBirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class CreditScreen extends ScreenAdapter {

    private final Main game;
    private final SettingsScreen settingsScreen;
    private Stage stage;
    private Texture creditTexture;
    private Texture crossBtnTexture;
    private ImageButton crossBtn;

    public CreditScreen(Main game, SettingsScreen settingsScreen) {
        this.game = game;
        this.settingsScreen = settingsScreen;

        creditTexture = new Texture("credit.png"); 
        crossBtnTexture = new Texture("cross.png"); 
    }

    @Override
    public void show() {
        stage = new Stage();

        Skin skin = new Skin();
        skin.add("cross", crossBtnTexture);

        ImageButton.ImageButtonStyle crossStyle = new ImageButton.ImageButtonStyle();
        crossStyle.imageUp = new TextureRegionDrawable(new TextureRegion(crossBtnTexture));
        crossBtn = new ImageButton(crossStyle);
        crossBtn.setSize(110, 110);
        crossBtn.setPosition(Gdx.graphics.getWidth() - crossBtn.getWidth() - 20,
            Gdx.graphics.getHeight() - crossBtn.getHeight() - 20);

        crossBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(settingsScreen); 
            }
        });

        stage.addActor(crossBtn);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(creditTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        stage.dispose();
        creditTexture.dispose();
        crossBtnTexture.dispose();
    }
}
