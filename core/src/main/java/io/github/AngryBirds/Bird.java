package io.github.AngryBirds;

import com.badlogic.gdx.graphics.Texture;

public class Bird {
    private String type;
    private float size;
    public Texture texture;
    public float x;
    public float y;
    public int height;
    public int width;

    public Bird(String type, float size, Texture texture, float x, float y,int width, int height) {
        this.type = type;
        this.size = size;
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
