package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datasource.ServerSideTest;
import edu.ship.engr.shipsim.datatypes.Position;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Merlin
 */
public class PlayerMovedReportTest extends ServerSideTest
{

    /**
     * make sure it gets built correctly
     */
    @Test
    public void creation()
    {
        PlayerMovedReport report = new PlayerMovedReport(33, "fred", new Position(3, 4), "x");
        assertEquals(33, report.getPlayerID());
        assertEquals("fred", report.getPlayerName());
        assertEquals(new Position(3, 4), report.getNewPosition());
        assertEquals("x", report.getMapName());
    }

    /**
     * Make sure the equals contract is obeyed
     */
    @Test
    public void equalsContract()
    {
        EqualsVerifier.forClass(PlayerMovedReport.class).verify();
    }
}
