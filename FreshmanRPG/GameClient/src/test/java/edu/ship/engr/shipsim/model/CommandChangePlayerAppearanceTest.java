package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datatypes.Crew;
import edu.ship.engr.shipsim.datatypes.Major;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.datatypes.VanityType;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetClientPlayerManager;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the functionality of CommandChangePlayerAppearance.
 */
@GameTest("GameClient")
@ResetClientPlayerManager
public class CommandChangePlayerAppearanceTest
{

    /**
     * When this command is executed, the client player's appearance should be updated.
     */
    @Test
    public void testChangesAppearance()
    {
        final ClientPlayerManager manager = ClientPlayerManager.getSingleton();
        final int playerId = 1;
        List<VanityDTO> vanityDTOS = new ArrayList<>();
        VanityDTO vanityDTO = new VanityDTO();
        vanityDTOS.add(vanityDTO);
        manager.initializePlayer(playerId, "Robert", vanityDTOS, new Position(1, 1),
                Crew.FORTY_PERCENT, Major.COMPUTER_ENGINEERING, 3);
        final VanityDTO newHat = new VanityDTO(1, "", "", "", VanityType.HAT, 0);
        final VanityDTO newBody = new VanityDTO(2, "", "", "", VanityType.BODY, 0);
        List<VanityDTO> vanityDTOS2 = new ArrayList<>();
        vanityDTOS2.add(newHat);
        vanityDTOS2.add(newBody);
        final CommandChangePlayerAppearance command = new CommandChangePlayerAppearance(playerId, vanityDTOS2);
        command.execute();

        assertEquals(newBody, manager.getPlayerFromID(1).getVanities().get(0));
        assertEquals(newHat, manager.getPlayerFromID(1).getVanities().get(1));
    }

}
