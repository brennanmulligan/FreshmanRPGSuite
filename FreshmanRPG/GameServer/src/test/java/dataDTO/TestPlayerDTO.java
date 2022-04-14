package dataDTO;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import datatypes.Crew;
import datatypes.Major;
import datatypes.Position;

/**
 * Test that the player DTO constructors set the defaults correctly.
 * @author Ben Uleau and Christopher Boyer
 */
public class TestPlayerDTO
{

	/**
	 * Test that the game manager constructor sets the correct defaults.
	 */
	@Test
	public void testPlayerDTOConstructorForGameManager()
	{
		PlayerDTO playerDTO = new PlayerDTO(1, "Password", Crew.NULL_POINTER, Major.SOFTWARE_ENGINEERING, 1, "Name");
		assertEquals("Ninja", playerDTO.getAppearanceType());
		assertEquals(0, playerDTO.getDoubloons());
		assertEquals(new Position(0, 0), playerDTO.getPosition());
		assertEquals("sortingroom.tmx", playerDTO.getMapName());
		assertEquals(0, playerDTO.getExperiencePoints());
	}

}
