package io.github.AngryBirds;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Main extends Game {

    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    BitmapFont font;
    BitmapFont smallFont;
    BitmapFont mediumFont;
    Music[] songs;
    Texture background;

    int currentSongIndex;

    @Override
    public void create () {

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        font = new BitmapFont();
        songs = new Music[]{
            Gdx.audio.newMusic(Gdx.files.internal("birds_angry.mp3")),
            Gdx.audio.newMusic(Gdx.files.internal("song2.mp3")),
            Gdx.audio.newMusic(Gdx.files.internal("song3.mp3"))
        };
        currentSongIndex = 0;

        songs[currentSongIndex].setLooping(true);
        songs[currentSongIndex].setVolume(0.5f);
        songs[currentSongIndex].play();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("angrybirds-regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 130;
        font = generator.generateFont(parameter);

        parameter.size = 60;
        smallFont = generator.generateFont(parameter);

        parameter.size = 105;
        mediumFont = generator.generateFont(parameter);

        generator.dispose();
        shapeRenderer = new ShapeRenderer();
        setScreen(new HomeScreen(this));
    }

    public void nextSong() {
        songs[currentSongIndex].stop();
        currentSongIndex = (currentSongIndex + 1) % songs.length;
        songs[currentSongIndex].setLooping(true);
        songs[currentSongIndex].play();
    }

    @Override
    public void dispose () {
        shapeRenderer.dispose();
        batch.dispose();
        font.dispose();
        smallFont.dispose();
        mediumFont.dispose();
        for(Music x:songs){
            x.dispose();
        }
    }
}
