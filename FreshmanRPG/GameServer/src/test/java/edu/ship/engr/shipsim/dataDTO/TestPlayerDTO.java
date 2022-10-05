package edu.ship.engr.shipsim.dataDTO;

import edu.ship.engr.shipsim.datatypes.Crew;
import edu.ship.engr.shipsim.datatypes.Major;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test that the player DTO constructors set the defaults correctly.
 *
 * @author Ben Uleau and Christopher Boyer
 */
@GameTest("GameServer")
public class TestPlayerDTO
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
