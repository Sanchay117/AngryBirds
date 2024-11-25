package io.github.AngryBirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

import static java.lang.Math.sqrt;

public class SlingShot {
    public Texture texture;
    public float x;
    public float y;
    public int width;
    public int height;

    private final float g = 9.8f;

    private Vector2 startPoint1;
    private Vector2 startPoint2;
    private Vector2 slingShotMiddle = new Vector2(259,354);
    private Vector2 endPoint;   // Point B (moving)
    private boolean dragging = false;

    boolean inRange(float x, float y) {
        if(200f <= x && x <= 300f && 300f <= y && y <= 400f) {
            return true;
        }
        return false;
    }

    float getX(float cos,float x,float velocity_initial,float t) {
        return x + cos*velocity_initial*t;
    }

    float getY(float sin,float y,float velocity_initial,float t) {
        return (float) (y + sin*velocity_initial*t - 0.5*g*t*t);
    }

    float getInitialVelocity(){
        float stringLength = (float) sqrt((endPoint.x-slingShotMiddle.x)*(endPoint.x-slingShotMiddle.x) + (endPoint.y-slingShotMiddle.y)*(endPoint.y-slingShotMiddle.y) );

        if(endPoint.x>slingShotMiddle.x) return -1*stringLength/2;
        return stringLength/2;
    }

    void drawTrajectory(float x,float y,float velocity_initial,ShapeRenderer shapeRenderer) {
        float tan = x/y;
        float hypotenuse = (float) sqrt(x*x + y*y);

        float sin = y/hypotenuse;
        float cos = x/hypotenuse;

        float timeOfFlight = 2*velocity_initial*sin/g;
        float timeStep = 0.35f;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(10, 10, 10, 0.8f)); // Semi-transparent red

        for (float t = 0; t <= timeOfFlight; t += timeStep) {
            float xCoord = getX(cos, x, velocity_initial, t);
            float yCoord = getY(sin, y, velocity_initial, t);

            shapeRenderer.circle(xCoord, yCoord, 7);
        }
    }

    public SlingShot(Texture texture, float x, float y, int width, int height) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        startPoint1 = new Vector2(228, 346);
        startPoint2 = new Vector2(293, 356);
        endPoint = new Vector2();
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        batch.draw(texture,x,y,width,height);

        float thickness = 4f;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);

        if (dragging) {
            // For the first line (startPoint1 to endPoint)
            for (float offset = -thickness / 2; offset <= thickness / 2; offset++) {
                shapeRenderer.line(startPoint1.x + offset, startPoint1.y, endPoint.x + offset, endPoint.y);
            }

            // For the second line (startPoint2 to endPoint)
            for (float offset = -thickness / 2; offset <= thickness / 2; offset++) {
                shapeRenderer.line(startPoint2.x + offset, startPoint2.y, endPoint.x + offset, endPoint.y);
            }

            shapeRenderer.end();

            drawTrajectory(endPoint.x, endPoint.y, getInitialVelocity(), shapeRenderer);

            shapeRenderer.end();
        }

        // Update the end point as the user moves
        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

//            System.out.println(touchX + " " + touchY);

            if (!dragging && inRange(touchX, touchY)) {
                // Start dragging from the current touch position
                dragging = true;
            }
            // Update the end point (B) as the user moves
            endPoint.set(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
        } else {
            dragging = false;
        }
    }
}
