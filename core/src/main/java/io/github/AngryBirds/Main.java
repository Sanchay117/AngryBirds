package io.github.AngryBirds;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Main extends ApplicationAdapter {

    enum Screen{
        TITLE, MAIN_GAME, GAME_OVER;
    }

    Screen currentScreen = Screen.TITLE;

    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    BitmapFont font;
    Texture backgroundImage;
    Music music;

    @Override
    public void create () {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();

        // bg music
        music = Gdx.audio.newMusic(Gdx.files.internal("bg_music.mp3"));
        music.setLooping(true);
        music.setVolume(.5f);
        music.play();
    }

    @Override
    public void render () {

        if(currentScreen == Screen.TITLE){
            backgroundImage = new Texture("bg.jpg");

            batch.begin();

            batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.draw(new Texture("play.png"), (float) (Gdx.graphics.getWidth() /2 - 205 ),50,350,150);
            batch.draw(new Texture("settings.jpg"), 0,50,150,50);

            batch.end();
        }
    }

    @Override
    public void dispose () {
        shapeRenderer.dispose();
    }
}
