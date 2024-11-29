package io.github.AngryBirds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;

import java.io.Serializable;
import java.util.Objects;

public class Material implements Serializable {
    private String name;
    private transient TextureRegion texture;
    private float x;
    private float y;
    private int width;
    private int height;

    public transient Body body;
    private transient World world;

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public World getWorld() {
        return world;
    }

    private transient BodyDef bodyDef;

    private int hp;
    private String file_name;

    public String getFile_name(){
        return file_name;
    }

    public Material(String name, TextureRegion texture, float x, float y, int width, int height, World world,String file_name) {
        this.name = name;
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.world = world;
        this.file_name = file_name;

        if(Objects.equals(name, "Wood")) hp = 5;
        else if(Objects.equals(name, "Stone")) hp = 15;
        else hp = 10; // glass

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

    public void hit(){
        this.hp=Math.max(0, this.hp-5);
    }

    public Material(String name, String file_name, float x, float y, int width, int height) {
        this.name = name;
        this.file_name = file_name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public float getXStraightUp(){
        return x;
    }

    public float getYStraightUp(){
        return y;
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

    public void clear(){
        world.destroyBody(body);
    }


}
