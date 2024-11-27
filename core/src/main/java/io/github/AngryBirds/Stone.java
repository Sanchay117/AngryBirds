package io.github.AngryBirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class Stone extends Material{
    public Stone(TextureRegion texture, float x, float y, int width, int height, World world) {
        super("Stone",texture,x,y,width,height,world);
    }
}
