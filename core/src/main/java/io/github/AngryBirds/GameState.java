package io.github.AngryBirds;
import java.io.Serializable;
import java.util.List;
public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;
    private int level;
    private int score;
    private List<BirdState> birds;
    private List<PigState> pigs;
    private List<MaterialState> materials;
    public GameState(int level, int score, List<BirdState> birds, List<PigState> pigs, List<MaterialState> materials) {
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
    public List<BirdState> getBirds() {
        return birds;
    }
    public List<PigState> getPigs() {
        return pigs;
    }
    public List<MaterialState> getMaterials() {
        return materials;
    }
}
