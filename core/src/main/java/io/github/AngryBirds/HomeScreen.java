package io.github.AngryBirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.Color;

public class HomeScreen extends ScreenAdapter {

    private final Main game;
    private int viewHeight = Gdx.graphics.getHeight();
    private int viewWidth = Gdx.graphics.getWidth();

    private Stage stage;
    private Texture playTexture;
    private ImageButton playButton;
    private Texture quitTexture;
    private ImageButton quitButton;

    private SpriteBatch batch;


    public HomeScreen(Main game) {
        this.game = game;
        game.background = new Texture("bg.png");

        game.music.setVolume(0.4f);

        batch = new SpriteBatch();
    }

    @Override
    public void show(){
        stage = new Stage();

        playTexture = new Texture("play.png");
        quitTexture = new Texture("quit.png");

        Skin skin = new Skin();
        skin.add("play", playTexture);
        skin.add("quit", quitTexture);

        ImageButton.ImageButtonStyle playStyle = new ImageButton.ImageButtonStyle();
        playStyle.imageUp = new TextureRegionDrawable(new TextureRegion(playTexture));
        playButton = new ImageButton(playStyle);

        ImageButton.ImageButtonStyle quitStyle = new ImageButton.ImageButtonStyle();
        quitStyle.imageUp = new TextureRegionDrawable(new TextureRegion(quitTexture));
        quitButton = new ImageButton(quitStyle);

        playButton.setPosition(viewWidth - 300, -75);
        playButton.setSize(300, 300);

        quitButton.setPosition(0, -75);
        quitButton.setSize(300, 300);

        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainScreen(game));
            }
        });
        playButton.setTransform(true);
        playButton.setOrigin(playButton.getWidth()/2, playButton.getHeight()/2);
        playButton.addAction(Actions.forever(
            Actions.sequence(
                Actions.scaleTo(1.2f, 1.2f, 0.5f),
                Actions.scaleTo(1.0f, 1.0f, 0.5f)
            )
        ));

        stage.addActor(playButton);
        stage.addActor(quitButton);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(game.background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.end();

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
        stage.dispose();
        playTexture.dispose();
        quitTexture.dispose();

        batch.dispose();
    }
}
