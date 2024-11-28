package io.github.AngryBirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;

import java.util.Objects;

public class Material {
    private String name;
    public TextureRegion texture;
    private float x;
    private float y;
    public int width;
    public int height;

    public Body body;
    private World world;
    private BodyDef bodyDef;

    private int hp;

    public Material(String name, TextureRegion texture, float x, float y, int width, int height, World world) {
        this.name = name;
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.world = world;

        if(Objects.equals(name, "Wood")) hp = 10;
        else if(Objects.equals(name, "Stone")) hp = 20;
        else hp = 15;

        bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // Set our body's starting position in the world
        bodyDef.position.set(x + width/2f ,y + height/2f);

        // Create our body in the world using our body definition
        body = world.createBody(bodyDef);
        body.setUserData(this); // Attach the Pig object itself to the body
//
//        // Create a circle shape and set its radius to 6
        // Create a PolygonShape for the rectangle
        PolygonShape rectangle = new PolygonShape();
        rectangle.setAsBox(width / 2f, height / 2f); // Half-width and half-height

// Create a fixture definition to apply the rectangle shape
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectangle;
        fixtureDef.density = 0.5f;
        if(Objects.equals(name, "Stone")) fixtureDef.density = 0.7f;
        if(Objects.equals(name, "Glass")) fixtureDef.density = 0.4f;
        fixtureDef.friction = 0.2f;
        if(Objects.equals(name, "Wood")) fixtureDef.friction = 0.4f;
        if(Objects.equals(name, "Stone")) fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit

// Create the fixture and attach it to the body
        Fixture fixture = body.createFixture(fixtureDef);

// Dispose of the shape after creating the fixture
        rectangle.dispose();
    }

    public int getHp() {
        return hp;
    }

    public void setPos(float x, float y){
        this.x = x + width/4f;
        this.y = y + height/4f;
        body.setTransform(x, y, body.getAngle());
    }

    public float getX(){
        return body.getPosition().x;
    }

    public float getY(){
        return body.getPosition().y;
    }

    public void setHp(int hp){
        this.hp = hp;
    }

    public String getName(){
        return name;
    }
}
