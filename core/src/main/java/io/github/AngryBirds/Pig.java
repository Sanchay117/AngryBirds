package io.github.AngryBirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;

public class Pig {
    public float size;
    private int hp;
    private float x;
    private float y;
    public Texture texture;
    public int width;
    public int height;

    private int HP_OG;

    public Body body;
    private World world;
    private BodyDef bodyDef;

    public Pig(float size, int hp, Texture texture, float x, float y, int width, int height, World world) {
        this.size = size;
        this.hp = hp;
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.width = width;
        this.height = height;

        this.world = world;

        this.HP_OG  = hp;

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
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit
//
//        // Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(fixtureDef);
//
//        // Remember to dispose of any shapes after you're done with them!
//        // BodyDef and FixtureDef don't need disposing, but shapes do.
        circle.dispose();
    }

    public int getHP_OG(){
        return HP_OG;
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
}
