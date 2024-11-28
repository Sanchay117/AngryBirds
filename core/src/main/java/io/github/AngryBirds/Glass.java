package io.github.AngryBirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class Glass extends Material{
    public Glass(TextureRegion texture, float x, float y, int width, int height, World world,String name) {
        super("Glass",texture,x,y,width,height,world,name);
    }
}
