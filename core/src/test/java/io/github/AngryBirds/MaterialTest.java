package io.github.AngryBirds;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class MaterialTest {
    private World world;

    @Before
    public void setUp() {
        world = new World(new Vector2(0, -9.8f), true);
    }

    @Test
    public void testMaterial() {
        // Create mock objects or use null where Texture is required
        Material w = new Wood(null,0,0,0,0,world,"wood.png");
        Material g = new Glass(null,0,0,0,0,world,"Glass_flat.jpg");
        Material s = new Stone(null,0,0,0,0,world,"stone_flat.jpg");

        int expectedHp = 0;
        for(int i = 0; i < 1000; i++){
            w.hit();
            g.hit();
            s.hit();
        }

        assertEquals("Wood Material TEST FAILED", expectedHp, w.getHp());
        assertEquals("Stone MaterialTEST FAILED", expectedHp, s.getHp());
        assertEquals("Glass Material TEST FAILED", expectedHp, g.getHp());
    }
}
