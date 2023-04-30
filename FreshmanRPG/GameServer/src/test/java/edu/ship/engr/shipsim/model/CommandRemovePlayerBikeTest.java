package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.datatypes.VanityType;
import edu.ship.engr.shipsim.model.reports.PlayerAppearanceChangeReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Create a test to remove bike from player
 */
@GameTest("gameServer")
@ResetPlayerManager
public class CommandRemovePlayerBikeTest
{
    //Create test to execute the command to remove bike from player
    @Test
    public void executeTest() throws DatabaseException
    {
        //set up the player
        PlayersForTest player = PlayersForTest.BIKE_PLAYER;
        List<VanityDTO> newWearing =
                player.getVanityItems();
        PlayerManager.getSingleton().addPlayerSilently(player.getPlayerID());
        assertTrue(checkIfHasABike(newWearing));

        //set up a mock observer, so we can make sure the report is sent
        ReportObserver obs = mock(ReportObserver.class);
        ReportObserverConnector.getSingleton()
                .registerObserver(obs, PlayerAppearanceChangeReport.class);

        //create and execute the command
        CommandRemovePlayerBike cmd = new CommandRemovePlayerBike(
                PlayersForTest.BIKE_PLAYER.getPlayerID());
        cmd.execute();

        //check that the bike is no longer in the player's list of vanity items
        ArrayList<VanityDTO> updatedWearing = PlayerManager.getSingleton()
                .getPlayerFromID(PlayersForTest.BIKE_PLAYER.getPlayerID())
                .getVanityItems();
        assertFalse(checkIfHasABike(updatedWearing));

        //verify that the right report got sent
        verify(obs, times(1)).receiveReport(eq(new PlayerAppearanceChangeReport(36,
                (ArrayList<VanityDTO>) updatedWearing)));
    }

    private boolean checkIfHasABike(List<VanityDTO> newWearing)
    {
        int i = 0;
        while ((i < newWearing.size()) &&
                (!isABike(newWearing.get(i))))
        {
            i++;
        }
        return (i < newWearing.size() && (isABike(newWearing.get(i))));
    }

    private boolean isABike(VanityDTO vanityDTO)
    {
        return vanityDTO.getVanityType() == VanityType.BIKE;
    }

}