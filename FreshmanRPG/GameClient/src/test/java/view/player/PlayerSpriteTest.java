package view.player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import datatypes.VanityType;
import org.junit.Test;

import java.util.HashMap;

/**
 * 
 * @author nhydock
 */
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

		sprite.setPosition(10,10);
		assertEquals(10, sprite.getX(), 0);
		assertEquals(10, sprite.getY(), 0);
	}
}
