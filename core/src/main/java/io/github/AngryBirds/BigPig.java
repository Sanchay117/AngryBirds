package io.github.AngryBirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class BigPig extends Pig{
    public BigPig(Texture texture, float x, float y,World world) {
        super(1,25,texture,x,y,200,200,world);
    }
}
