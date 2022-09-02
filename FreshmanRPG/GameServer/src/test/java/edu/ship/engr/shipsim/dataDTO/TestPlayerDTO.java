package edu.ship.engr.shipsim.dataDTO;

import edu.ship.engr.shipsim.datasource.ServerSideTest;
import edu.ship.engr.shipsim.datatypes.Crew;
import edu.ship.engr.shipsim.datatypes.Major;
import edu.ship.engr.shipsim.datatypes.Position;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test that the player DTO constructors set the defaults correctly.
 *
 * @author Ben Uleau and Christopher Boyer
 */
public class TestPlayerDTO extends ServerSideTest
{

    /**
     * Test that the game manager constructor sets the correct defaults.
     */
    @Test
    public void testPlayerDTOConstructorForGameManager()
    {
        PlayerDTO playerDTO = new PlayerDTO(1, "Password", Crew.FORTY_PERCENT, Major.SOFTWARE_ENGINEERING, 1, "Name");
        assertEquals("Ninja", playerDTO.getAppearanceType());
        assertEquals(0, playerDTO.getDoubloons());
        assertEquals(new Position(0, 0), playerDTO.getPosition());
        assertEquals("sortingroom.tmx", playerDTO.getMapName());
        assertEquals(0, playerDTO.getExperiencePoints());
    }

}
