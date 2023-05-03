package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.Player;
import edu.ship.engr.shipsim.model.PlayerManager;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Merlin
 */
@GameTest("GameServer")
@ResetPlayerManager
public class PlayerConnectionReportTest
{
    /**
     * make sure it gets built correctly
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void creation() throws DatabaseException
    {
        Player john = PlayerManager.getSingleton().addPlayer(1);

        PlayerConnectionReport report = new PlayerConnectionReport(
                john.getPlayerID(),
                john.getPlayerName(),
                john.getAppearanceType(),
                john.getPosition(),
                john.getCrew(),
                john.getMajor(),
                john.getSection(),
                john.getVanityItems()
        );

        assertEquals(1, report.getPlayerID());
        assertEquals(PlayersForTest.JOHN.getPlayerName(), report.getPlayerName());
        assertEquals(PlayersForTest.JOHN.getAppearanceType(), report.getAppearanceType());
        assertEquals(PlayersForTest.JOHN.getPosition(), report.getPosition());
        assertEquals(PlayersForTest.JOHN.getCrew(), report.getCrew());
        assertEquals(PlayersForTest.JOHN.getMajor(), report.getMajor());
        assertEquals(PlayersForTest.JOHN.getSection(), report.getSection());
    }

    /**
     * Make sure the equals contract is obeyed
     */
    @Test
    public void equalsContract()
    {
        EqualsVerifier.forClass(PlayerConnectionReport.class).withRedefinedSuperclass().verify();
    }
}
