package io.github.AngryBirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class AveragePig extends Pig{
    public AveragePig(Texture texture, float x, float y,World world,String name) {
        super(20,texture,x,y,130,130,world,name);
    }
}
