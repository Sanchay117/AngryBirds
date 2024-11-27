package io.github.AngryBirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class AveragePig extends Pig{
    public AveragePig(Texture texture, float x, float y, int width, int height, World world) {
        super(1,15,texture,x,y,width,height,world);
    }
}
