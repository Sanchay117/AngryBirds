package io.github.AngryBirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import java.util.Vector;

import static java.lang.Math.sqrt;

public class SlingShot {
    public static Texture texture;
    public static float x;
    public static float y;
    public static int width;
    public static int height;

    private static final float PPM = 100.0f; // Pixels per meter

    private World world = null;
    private Bird birb = null;

    public SlingShot(Texture texture, float x, float y, int width, int height,World world) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
