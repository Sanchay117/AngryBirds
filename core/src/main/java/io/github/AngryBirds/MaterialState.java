package io.github.AngryBirds;

import java.io.Serializable;

public class MaterialState implements Serializable {
    private static final long serialVersionUID = 1L;

    private float x;
    private float y;
    private int hp;
    private String name;

    public MaterialState(float x, float y, int hp, String name) {
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.name = name;
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

    public String getName() {
        return name;
    }
}
