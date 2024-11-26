package io.github.AngryBirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

public class Bird {
    private String type;
    private float size;
    public Texture texture;
    private float x;
    private float y;
    public int height;
    public int width;

    private Body body;
    public World world;
    private BodyDef bodyDef;

    public Bird(String type, float size, Texture texture, float x, float y,int width, int height,World world) {
        this.type = type;
        this.size = size;
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.world = world;

        // First we create a body definition
        bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // Set our body's starting position in the world
        bodyDef.position.set(x,y);

        // Create our body in the world using our body definition
        Body body = world.createBody(bodyDef);

        // Create a circle shape and set its radius to 6
//        CircleShape circle = new CircleShape();
//        circle.setRadius(width/2f);
//
//        // Create a fixture definition to apply our shape to
//        FixtureDef fixtureDef = new FixtureDef();
//        fixtureDef.shape = circle;
//        fixtureDef.density = 0.5f;
//        fixtureDef.friction = 0.4f;
//        fixtureDef.restitution = 0.6f; // Make it bounce a little bit
//
//        // Create our fixture and attach it to the body
//        Fixture fixture = body.createFixture(fixtureDef);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
//        circle.dispose();

    }

    public void render(SpriteBatch batch){
        batch.draw(texture, x, y, width, height);
    }

    public void setPos(float x, float y){
        this.x = x;
        this.y = y;

        bodyDef.position.set(x,y);
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

}
