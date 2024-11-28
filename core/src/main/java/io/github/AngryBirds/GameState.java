package io.github.AngryBirds;

import java.io.Serializable;
import java.util.List;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    private int level;
    private int score;
    private List<Bird> birds;
    private List<Pig> pigs;
    private List<Material> materials;

    public GameState(int level, int score, List<Bird> birds, List<Pig> pigs, List<Material> materials) {
        this.level = level;
        this.score = score;
        this.birds = birds;
        this.pigs = pigs;
        this.materials = materials;
    }

    public int getLevel() {
        return level;
    }

    public int getScore() {
        return score;
    }

    public List<Bird> getBirds() {
        return birds;
    }

    public List<Pig> getPigs() {
        return pigs;
    }

    public List<Material> getMaterials() {
        return materials;
    }
}
