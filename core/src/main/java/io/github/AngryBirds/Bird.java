package io.github.AngryBirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

public class Bird {
    private String type;
    private float size;
    public Texture texture;
    public int height; // Height in pixels
    public int width;  // Width in pixels

    private Body body;
    private World world;

    public static final float PPM = 100.0f; // Pixels per meter

    public Bird(String type, float size, Texture texture, float x, float y, int width, int height, World world) {
        this.type = type;
        this.size = size;
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.world = world;

        // Create a body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // Set body's starting position (convert pixels to meters)
        bodyDef.position.set(x / PPM, y / PPM);

        // Create the body in the world
        body = world.createBody(bodyDef);

        // Create a circle shape for the body (radius in meters)
        CircleShape circle = new CircleShape();
        circle.setRadius((width / 2f) / PPM);

        // Create a fixture definition and attach it to the body
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little

        body.createFixture(fixtureDef);

        // Dispose of the shape to free memory
        circle.dispose();
    }

    public void render(SpriteBatch batch) {
        // Get body position (in meters) and convert to pixels
        float renderX = (body.getPosition().x * PPM) - (width / 2f); // Center texture
        float renderY = (body.getPosition().y * PPM) - (height / 2f);

        // Draw the texture at the adjusted position
        batch.draw(texture, renderX, renderY, width, height);
    }

    public void setPos(float x, float y) {
        // Set body position (convert pixels to meters)
        body.setTransform(x / PPM, y / PPM, body.getAngle());
    }

    public float getX() {
        // Convert body position from meters to pixels
        return body.getPosition().x * PPM;
    }

    public float getY() {
        // Convert body position from meters to pixels
        return body.getPosition().y * PPM;
    }
}
