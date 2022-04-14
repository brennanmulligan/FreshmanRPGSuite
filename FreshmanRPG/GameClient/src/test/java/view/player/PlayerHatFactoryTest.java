package view.player;

import com.badlogic.gdx.Gdx;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerHatFactoryTest
{
    @Test
    public void testCreateHat()
    {
        PlayerHatFactory hf = new PlayerHatFactory(Gdx.files.internal("ui-data/characters.pack"));
        //create a player hat
        Vanity hat = hf.create("duck");
        assertEquals(Direction.South, hat.getFacing());
    }

}
