package edu.ship.engr.shipsim.view.player;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author nhydock
 */
@GameTest("GameClient")
public class PlayerSpriteTest
{

    /**
     * Test creating a sprite (headless without texture)
     */
    @Test
    public void testInitialize()
    {
        PlayerSprite sprite = new PlayerSprite();
    }

    @Test
    public void testSetPosition()
    {
//		Vanity vanity = new Vanity();
//		HashMap<VanityType,Vanity> vanities = new HashMap<>();
//		vanities.put(VanityType.BODY, vanity);
        PlayerSprite sprite = new PlayerSprite();

        sprite.setPosition(10, 10);
        assertEquals(10, sprite.getX(), 0);
        assertEquals(10, sprite.getY(), 0);
    }
}
