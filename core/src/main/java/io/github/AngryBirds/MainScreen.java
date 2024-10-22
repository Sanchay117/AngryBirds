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
    private Texture lvlBtnTexture;
    private ImageButton backBtn;
    private ImageButton lvlBtn1;
    private ImageButton lvlBtn2;
    private ImageButton lvlBtn3;
    private ImageButton lvlBtn4;
    private ImageButton lvlBtn5;

    private float x = 0.08f;

    public MainScreen(Main game) {
        this.game = game;
        game.background = new Texture("MainScreenBG.jpg");
    }

    @Override
    public void show(){
        stage = new Stage();

        backBtnTexture = new Texture("back.png");
        lvlBtnTexture = new Texture("lvlBG.png");

        Skin skin = new Skin();
        skin.add("back", backBtnTexture);
        skin.add("lvl", lvlBtnTexture);

        ImageButton.ImageButtonStyle playStyle = new ImageButton.ImageButtonStyle();
        ImageButton.ImageButtonStyle lvlStyle = new ImageButton.ImageButtonStyle();
        playStyle.imageUp = new TextureRegionDrawable(new TextureRegion(backBtnTexture));
        lvlStyle.imageUp = new TextureRegionDrawable(new TextureRegion(lvlBtnTexture));
        backBtn = new ImageButton(playStyle);
        lvlBtn1 = new ImageButton(lvlStyle);
        lvlBtn2 = new ImageButton(lvlStyle);
        lvlBtn3 = new ImageButton(lvlStyle);
        lvlBtn4 = new ImageButton(lvlStyle);
        lvlBtn5 = new ImageButton(lvlStyle);

        backBtn.setPosition(0.05f,viewHeight-200);
        backBtn.setSize(200, 200);

        lvlBtn1.setSize(250, 250);
        lvlBtn2.setSize(250, 250);
        lvlBtn3.setSize(250, 250);
        lvlBtn4.setSize(250, 250);
        lvlBtn5.setSize(250, 250);

        lvlBtn1.setPosition(viewWidth*x, viewHeight*0.5f);
        x+=0.175f;
        lvlBtn2.setPosition(viewWidth*x, viewHeight*0.5f);
        x+=0.175f;
        lvlBtn3.setPosition(viewWidth*x, viewHeight*0.5f);
        x+=0.175f;
        lvlBtn4.setPosition(viewWidth*x, viewHeight*0.5f);
        x+=0.175f;
        lvlBtn5.setPosition(viewWidth*x, viewHeight*0.5f);
        x=0.08f;

        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new HomeScreen(game));
            }
        });

        lvlBtn1.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelScreen(game));
            }

        });

        lvlBtn2.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelScreen(game));
            }

        });

        lvlBtn3.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelScreen(game));
            }

        });

        lvlBtn4.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelScreen(game));
            }

        });

        lvlBtn5.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelScreen(game));
            }

        });

        stage.addActor(backBtn);
        stage.addActor(lvlBtn1);
        stage.addActor(lvlBtn2);
        stage.addActor(lvlBtn3);
        stage.addActor(lvlBtn4);
        stage.addActor(lvlBtn5);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(game.background, 0, 0,viewWidth,viewHeight);
        game.font.draw(game.batch, "LEVELS", viewWidth*0.38f, viewHeight*0.95f);

        stage.act(delta);
        stage.draw();

        game.mediumFont.draw(game.batch, "1", viewWidth*x - 120, viewHeight*.5f + 145);
        x+=0.175f;
        game.mediumFont.draw(game.batch, "2", viewWidth*x - 120, viewHeight*.5f + 145);
        x+=0.175f;
        game.mediumFont.draw(game.batch, "3", viewWidth*x - 120, viewHeight*.5f + 145);
        x+=0.175f;
        game.mediumFont.draw(game.batch, "4", viewWidth*x - 120, viewHeight*.5f + 145);
        x+=0.175f;
        game.mediumFont.draw(game.batch, "5", viewWidth*x - 120, viewHeight*.5f + 145);

        x=0.23f;

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
        backBtnTexture.dispose();
        stage.dispose();
    }
}
