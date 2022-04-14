package view.player;

import com.badlogic.gdx.Gdx;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerBodyFactoryTest
{
    @Test
    public void testCreateBody()
    {
        PlayerBodyFactory bf = new PlayerBodyFactory(Gdx.files.internal("ui-data/characters.pack"));
        Vanity body = bf.create("body");
        assertEquals(Direction.South, body.getFacing());
    }
}
