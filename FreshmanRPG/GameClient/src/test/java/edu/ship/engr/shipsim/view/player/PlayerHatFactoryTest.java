package edu.ship.engr.shipsim.view.player;

import com.badlogic.gdx.Gdx;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@GameTest("GameClient")
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
