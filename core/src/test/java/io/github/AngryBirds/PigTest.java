package io.github.AngryBirds;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class PigTest {
    private World world;

    @Before
    public void setUp() {
        world = new World(new Vector2(0, -9.8f), true);
    }

    @Test
    public void testPig() {
        // Create mock objects or use null where Texture is required
        Pig p1 = new AveragePig(null, 0, 0, world, "pig.png");
        Pig p2 = new SmallPig(null, 0, 0, world, "pig.png");
        Pig p3 = new BigPig(null, 0, 0, world, "pig.png");

        int expectedHp = 0;
        for(int i = 0; i < 1000; i++){
            p1.hit();
            p2.hit();
            p3.hit();
        }

        assertEquals("Average PIG TEST FAILED", expectedHp, p1.getHp());
        assertEquals("Small PIG TEST FAILED", expectedHp, p2.getHp());
        assertEquals("Big PIG TEST FAILED", expectedHp, p3.getHp());
    }
}
