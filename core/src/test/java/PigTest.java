
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import io.github.AngryBirds.AveragePig;
import io.github.AngryBirds.BigPig;
import io.github.AngryBirds.Pig;
import io.github.AngryBirds.SmallPig;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class PigTest {
    private Texture pigTexture;
    private World world = new World(new Vector2(0, -9.8f), true);

    @Test
    public void testPig() {
        Pig p1 = new AveragePig(pigTexture,0,0,world,"pig.png");
        Pig p2 = new SmallPig(pigTexture,0,0,world,"pig.png");
        Pig p3 = new BigPig(pigTexture,0,0,world,"pig.png");
        int hp = 0;
        for(int i = 0;i<1000;i++){
            p1.hit();
            p2.hit();
            p3.hit();
        }
        System.out.println("Pig Hit: "+hp);
        assertEquals("Average PIG TEST FAILED",hp,p1.getHp());
        assertEquals("Small PIG TEST FAILED",hp,p2.getHp());
        assertEquals("Big PIG TEST FAILED",hp,p3.getHp());
    }
}
