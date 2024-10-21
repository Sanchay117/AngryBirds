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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.Color;

public class MainScreen extends ScreenAdapter {

    private final Main game;
    private int viewHeight = Gdx.graphics.getHeight();
    private int viewWidth = Gdx.graphics.getWidth();
    private final Texture backArrow = new Texture(Gdx.files.internal("LeftArrow.png"));
    private ShapeRenderer shapeRenderer;
    private GlyphLayout layout;


    public MainScreen(Main game) {
        this.game = game;
        game.background = new Texture("MainScreenBG.jpg");
    }

    @Override
    public void show(){



    }

    @Override
    public void render(float delta) {

        game.batch.begin();

        game.batch.draw(game.background, 0, 0,viewWidth,viewHeight);
        game.font.draw(game.batch, "LEVELS", viewWidth*0.38f, viewHeight*0.95f);
        game.batch.draw(backArrow,viewWidth*0.03f,viewHeight*0.95f - 130,120,150);

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
    }
}
