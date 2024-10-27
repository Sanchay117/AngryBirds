package io.github.AngryBirds;

import com.badlogic.gdx.graphics.Texture;

public class Material {
    private String name;
    public Texture texture;
    public float x;
    public float y;
    public int width;
    public int height;

    public Material(String name, Texture texture, float x, float y, int width, int height) {
        this.name = name;
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
