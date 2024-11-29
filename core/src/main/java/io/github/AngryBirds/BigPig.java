package io.github.AngryBirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class BigPig extends Pig{
    public BigPig(Texture texture, float x, float y,World world,String file_name) {
        super(30,texture,x,y,200,200,world,file_name);
    }
}
