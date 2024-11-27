package io.github.AngryBirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class Red extends Bird {
    public Red(Texture texture, float x, float y, int width, int height, World world) {
        super("red",1,texture,x,y,width,height,world);
    }
}
