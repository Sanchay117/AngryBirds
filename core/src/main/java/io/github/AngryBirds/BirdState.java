package io.github.AngryBirds;

import java.io.Serializable;

public class BirdState implements Serializable {
    private static final long serialVersionUID = 1L;

    private float x;
    private float y;
    private String type;

    public BirdState(float x, float y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public String getType() {
        return type;
    }
}
