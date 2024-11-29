package io.github.AngryBirds;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class MockTexture {
    public static Texture createMockTexture() {
        // Create a Pixmap with width, height, and format
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888); // 1x1 pixel

        // Fill it with a solid color (e.g., white)
        pixmap.setColor(1, 1, 1, 1); // RGBA (white)
        pixmap.fill();

        // Create a Texture from the Pixmap
        Texture texture = new Texture(pixmap);

        // Dispose the Pixmap as it is no longer needed
        pixmap.dispose();

        return texture;
    }
}
