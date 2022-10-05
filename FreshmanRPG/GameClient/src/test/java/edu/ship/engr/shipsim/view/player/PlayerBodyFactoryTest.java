package edu.ship.engr.shipsim.view.player;

import com.badlogic.gdx.Gdx;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@GameTest("GameClient")
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
