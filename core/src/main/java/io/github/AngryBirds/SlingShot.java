package io.github.AngryBirds;

import com.badlogic.gdx.graphics.Texture;

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
}
