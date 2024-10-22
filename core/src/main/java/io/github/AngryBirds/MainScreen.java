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
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.Color;

public class MainScreen extends ScreenAdapter {

    private final Main game;
    private int viewHeight = Gdx.graphics.getHeight();
    private int viewWidth = Gdx.graphics.getWidth();

    private Stage stage;
    private Texture backBtnTexture;
    private ImageButton backBtn;


    public MainScreen(Main game) {
        this.game = game;
        game.background = new Texture("MainScreenBG.jpg");
    }

    @Override
    public void show(){
        stage = new Stage();

        backBtnTexture = new Texture("back.png");

        Skin skin = new Skin();
        skin.add("back", backBtnTexture);

        ImageButton.ImageButtonStyle playStyle = new ImageButton.ImageButtonStyle();
        playStyle.imageUp = new TextureRegionDrawable(new TextureRegion(backBtnTexture));
        backBtn = new ImageButton(playStyle);

        backBtn.setPosition(0.05f,viewHeight-200);
        backBtn.setSize(200, 200);

        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new HomeScreen(game));
            }
        });

        stage.addActor(backBtn);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(game.background, 0, 0,viewWidth,viewHeight);
        game.font.draw(game.batch, "LEVELS", viewWidth*0.38f, viewHeight*0.95f);

        float x = 0.23f;
        game.font.draw(game.batch, "1", viewHeight*x, viewHeight*0.68f);
        x+=0.23f;
        game.font.draw(game.batch, "2", viewHeight*x, viewHeight*0.68f);
        x+=0.23f;
        game.font.draw(game.batch, "3", viewHeight*x, viewHeight*0.68f);
        x+=0.23f;
        game.font.draw(game.batch, "4", viewHeight*x, viewHeight*0.68f);
        x+=0.23f;
        game.font.draw(game.batch, "5", viewHeight*x, viewHeight*0.68f);

        game.batch.end();

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
        backBtnTexture.dispose();
        stage.dispose();
    }
}
