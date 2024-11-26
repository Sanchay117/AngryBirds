package io.github.AngryBirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Bird {
    private String type;
    private float size;
    public Texture texture;
    public float x;
    public float y;
    public int height;
    public int width;

    private Body body;
    public World world;

    public Bird(String type, float size, Texture texture, float x, float y,int width, int height,World world) {
        this.type = type;
        this.size = size;
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.world = world;
    }

    public void render(SpriteBatch batch){
        batch.draw(texture, x, y, width, height);
    }

}
