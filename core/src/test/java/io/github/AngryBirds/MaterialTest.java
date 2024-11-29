package io.github.AngryBirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class MaterialTest {
    private World world;
    private Texture mockTexture;

    @Before
    public void setUp() {
        // Initialize the Box2D world
        world = new World(new Vector2(0, -9.8f), true);

        // Use Mockito to mock the Texture
        mockTexture = mock(Texture.class);
    }

    @After
    public void tearDown() {
        // Dispose of resources
        if (world != null) {
            world.dispose();
        }
    }

    @Test
    public void testMaterial() {
        try {
            // Use the mocked texture
            Material wood = new Wood(new TextureRegion(mockTexture), 0, 0, 10, 10, world, "wood.png");
            Material glass = new Glass(new TextureRegion(mockTexture), 0, 0, 10, 10, world, "glass.png");
            Material stone = new Stone(new TextureRegion(mockTexture), 0, 0, 10, 10, world, "stone.png");

            int expectedHp = 0;

            for (int i = 0; i < 1000; i++) {
                wood.hit();
                glass.hit();
                stone.hit();
            }

            assertEquals("Wood Material TEST FAILED", expectedHp, wood.getHp());
            assertEquals("Glass Material TEST FAILED", expectedHp, glass.getHp());
            assertEquals("Stone Material TEST FAILED", expectedHp, stone.getHp());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
