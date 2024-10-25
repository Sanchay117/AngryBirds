package io.github.AngryBirds;

import com.badlogic.gdx.graphics.Texture;

public class Pig {
    public float size;
    public int hp;
    public float x;
    public float y;
    public Texture texture;
    public int width;
    public int height;

    public Pig(float size, int hp, Texture texture, float x, float y, int width, int height) {
        this.size = size;
        this.hp = hp;
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.width = width;
        this.height = height;
    }
}
