package io.github.AngryBirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;

import java.io.Serializable;
import java.util.HashMap;

public class Pig implements Serializable {
    private int hp;
    private float x;
    private float y;
    private transient Texture texture;
    private int width;
    private int height;

    private int HP_OG;

    public transient Body body;
    private transient World world;
    private transient BodyDef bodyDef;

    private String file_name;

    private HashMap<Material,Integer> hits = new HashMap<Material, Integer>();

    public Pig(int hp, String file_name, float x, float y, int width, int height) {
        this.hp = hp;
        this.x = x;
        this.y = y;
        this.file_name = file_name;
        this.texture = new Texture(file_name);
        this.width = width;
        this.height = height;

        this.HP_OG = hp;
    }

    public Pig(int hp, Texture texture, float x, float y, int width, int height, World world,String file_name) {
        this.hp = hp;
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.width = width;
        this.height = height;

        this.world = world;

        this.HP_OG  = hp;

        this.file_name = file_name;

        // First we create a body definition
        bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // Set our body's starting position in the world
        bodyDef.position.set(x ,y);

        // Create our body in the world using our body definition
        body = world.createBody(bodyDef);
        body.setUserData(this); // Attach the Pig object itself to the body
//
//        // Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(width/2f);
//
//        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = .5f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f; // Make it bounce a little bit
//
//        // Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(fixtureDef);
//
//        // Remember to dispose of any shapes after you're done with them!
//        // BodyDef and FixtureDef don't need disposing, but shapes do.
        circle.dispose();
    }

    public String getFile_name() {
        return file_name;
    }

    public int getHP_OG(){
        return HP_OG;
    }

    public float getXStraightUp(){
        return x;
    }

    public float getYStraightUp(){
        return y;
    }

    public void setHP_OG(int hp){
        HP_OG = hp;
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
        this.hp = Math.max(hp, 0);
    }

    public void hit(Material material){
        if(!hits.containsKey(material)) {
            this.hp = Math.max(hp - 5, 0);
            hits.put(material,1);
        }
    }

    public Texture getTexture(){
        return texture;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public World getWorld(){
        return world;
    }

    public void setTexture(Texture texture,String name){
        this.texture = texture;
        this.file_name = name;
    }

    public void hit(){
        setHp(hp-5);
    }
}
