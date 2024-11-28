package io.github.AngryBirds;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;
    private int level;
    private int score;
    private ArrayList<Bird> birds;
    private ArrayList<Pig> pigs;
    private ArrayList<Material> materials;
    public GameState(int level, int score, ArrayList<Bird> birds, ArrayList<Pig> pigs, ArrayList<Material> materials) {
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
    public ArrayList<Bird> getBirds() {
        return birds;
    }
    public ArrayList<Pig> getPigs() {
        return pigs;
    }
    public ArrayList<Material> getMaterials() {
        return materials;
    }
}
