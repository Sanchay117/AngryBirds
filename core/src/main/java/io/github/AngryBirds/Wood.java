package io.github.AngryBirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class Wood extends Material{
    public Wood(Texture texture, float x, float y, int width, int height, World world) {
        super("Wood",texture,x,y,width,height,world);
    }
}
