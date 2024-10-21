package io.github.AngryBirds;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
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
    Music music;
    Texture background;

    @Override
    public void create () {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        font = new BitmapFont();

        // bg music
        music = Gdx.audio.newMusic(Gdx.files.internal("fine.mp3"));
        music.setLooping(true);
        music.setVolume(.5f);
        music.play();

        // font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("angrybirds-regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 130;  // Set the font size
        font = generator.generateFont(parameter); // Generate the BitmapFont
        generator.dispose(); // Don't forget to dispose of the generator after use
        shapeRenderer = new ShapeRenderer();

        setScreen(new LevelScreen(this));
//        setScreen(new MainScreen(this));
//        setScreen(new HomeScreen(this));
    }

    @Override
    public void dispose () {
        shapeRenderer.dispose();
        batch.dispose();
        font.dispose();
        music.dispose();
    }
}
