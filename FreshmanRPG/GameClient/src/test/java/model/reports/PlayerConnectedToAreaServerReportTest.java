package model.reports;

import datatypes.PlayersForTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Ryan C and Adam W
 */
public class PlayerConnectedToAreaServerReportTest
{

    /**
     * Make sure it is built correctly
     */
    /*
    @Test
    public void creation()
    {
        PlayerConnectedToAreaServerReport report = new PlayerConnectedToAreaServerReport(
                PlayersForTest.MERLIN.getPlayerID()
                , PlayersForTest.MERLIN.getPlayerName()
                , PlayersForTest.MERLIN.getPosition()
                , PlayersForTest.MERLIN.getCrew()
                , PlayersForTest.MERLIN.getMajor()
                , true
                , new "123"
                , "456"
        );

        assertEquals(report.getPlayerID(), PlayersForTest.MERLIN.getPlayerID());
        assertEquals(report.getPlayerName(), PlayersForTest.MERLIN.getPlayerName());
        assertEquals(report.getPlayerPosition(), PlayersForTest.MERLIN.getPosition());
        assertEquals(report.getCrew(), PlayersForTest.MERLIN.getCrew());
        assertEquals(report.getMajor(), PlayersForTest.MERLIN.getMajor());
        assertTrue(report.isThisClientsPlayer());
        assertEquals(report.getHatID(), "123");
        assertEquals(report.getBodyID(), "456");
    }

    /**
     * Test for equals contract
     */
    @Test
    public void equalsContract()
    {
        EqualsVerifier.forClass(PlayerConnectedToAreaServerReport.class).verify();
    }
}
