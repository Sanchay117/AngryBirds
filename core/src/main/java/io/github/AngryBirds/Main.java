package io.github.AngryBirds;

import com.badlogic.gdx.Game;
import com.mygame.screens.HomeScreen;

public class MainGame extends Game {
    @Override
    public void create() {
        this.setScreen(new HomeScreen(this)); // Start with HomeScreen
    }

    @Override
    public void render() {
        super.render(); // This calls the render method of the active screen
    }

    @Override
    public void dispose() {
        // Dispose any global resources here
    }
}
