package io.github.AngryBirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class SmallPig extends Pig{
    public SmallPig(Texture texture, float x, float y, World world,String name) {
        super(20,texture,x,y,50,50,world,name);
    }
}
