package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.Player;
import edu.ship.engr.shipsim.model.PlayerManager;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the PlayerFinishedInitializingReport
 *
 * @author Brad Olah
 */
@GameTest("GameServer")
@ResetPlayerManager
public class PlayerFinishedInitializingReportTest
{
    /**
     * make sure it gets built correctly
     */
    @Test
    public void creation()
    {
        Player john = PlayerManager.getSingleton().addPlayer(1);

        PlayerFinishedInitializingReport report = new PlayerFinishedInitializingReport(
                john.getPlayerID(),
                john.getPlayerName(),
                john.getAppearanceType()
        );

        assertEquals(1, report.getPlayerID());
        assertEquals(PlayersForTest.JOHN.getPlayerName(), report.getPlayerName());
        assertEquals(PlayersForTest.JOHN.getAppearanceType(), report.getAppearanceType());
    }

    /**
     * Make sure the equals contract is obeyed
     */
    @Test
    public void equalsContract()
    {
        EqualsVerifier.forClass(PlayerFinishedInitializingReport.class).verify();
    }
}
