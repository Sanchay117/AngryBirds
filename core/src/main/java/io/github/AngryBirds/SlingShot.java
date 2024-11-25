package io.github.AngryBirds;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

public class SlingShot {
    public Texture texture;
    public float x;
    public float y;
    public int width;
    public int height;

    public SlingShot(Texture texture, float x, float y, int width, int height) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {

        batch.draw(texture,x,y,width,height);
    }
}
