package io.github.AngryBirds;

import java.io.Serializable;

public class PigState implements Serializable {
    private static final long serialVersionUID = 1L;

    private float x;
    private float y;
    private int hp;

    public PigState(float x, float y, int hp) {
        this.x = x;
        this.y = y;
        this.hp = hp;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getHp() {
        return hp;
    }
}
