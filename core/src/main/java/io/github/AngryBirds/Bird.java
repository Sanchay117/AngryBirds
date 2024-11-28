package io.github.AngryBirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

import java.io.Serializable;

public class Bird implements Serializable {
    private String type;
    private transient Texture texture;
    private float x;
    private float y;
    private int height;
    private int width;



    public transient Body body;
    private transient World world;
    private transient BodyDef bodyDef;

    private String file_name;

    public String getFile_name() {
        return file_name;
    }

    public Bird(String type,  Texture texture, float x, float y,int width, int height,World world,String file_name) {
        this.type = type;
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.world = world;

        this.file_name = file_name;

        // First we create a body definition
        bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // Set our body's starting position in the world
        bodyDef.position.set(x ,y);

        // Create our body in the world using our body definition
        body = world.createBody(bodyDef);
//
//        // Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(width/4f);
//
//        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = .5f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit
//
//        // Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(fixtureDef);
//
//        // Remember to dispose of any shapes after you're done with them!
//        // BodyDef and FixtureDef don't need disposing, but shapes do.
        circle.dispose();

    }

    public Bird(String type,  String f, float x, float y,int width, int height) {
        this.type = type;
        this.file_name = f;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setPos(float x, float y){
        this.x = x + width/4f;
        this.y = y + height/4f;
        body.setTransform(x, y, body.getAngle());
    }

    public float getX(){
        return body.getPosition().x;
    }

    public float getXStraightUp(){
        return x;
    }

    public float getYStraightUp(){
        return y;
    }

    public float getY(){
        return body.getPosition().y;
    }

    public String getType(){
        return type;
    }

    public Texture getTexture(){
        return texture;
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }


}
