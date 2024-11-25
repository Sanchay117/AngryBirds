package io.github.AngryBirds;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

public class SlingShot {
    public Texture texture;
    public float x;
    public float y;
    public int width;
    public int height;

//    private final Vector2 anchorPoint;
//    private final Vector2 dragPoint;
//    private boolean isDragging;

    public SlingShot(Texture texture, float x, float y, int width, int height) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

//        this.anchorPoint = new Vector2(x + width / 2, y + height / 2);
//        this.dragPoint = new Vector2(anchorPoint); // Initially the same as the anchor
//        this.isDragging = false;
    }

//    public void startDrag(float x, float y) {
//        isDragging = true;
//        dragPoint.set(x, y);
//    }
//
//    public void updateDrag(float x, float y) {
//        if (isDragging) {
//            dragPoint.set(x, y);
//        }
//    }
//
//    public void endDrag() {
//        isDragging = false;
//        dragPoint.set(anchorPoint); // Reset to anchor point
//    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        // Draw the slingshot base
        batch.draw(texture,x,y,width,height);

//        // Draw the stretched sling
//        if (isDragging) {
//            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//            shapeRenderer.setColor(Color.BLACK);
//            shapeRenderer.line(anchorPoint.x, anchorPoint.y, dragPoint.x, dragPoint.y);
//            shapeRenderer.end();
//        }
    }
}
