package io.github.AngryBirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class Blue extends Bird {
    public Blue(Texture texture, float x, float y, int width, int height, World world,String name) {
        super("Blue",texture,x,y,width,height,world,name);
    }
}
