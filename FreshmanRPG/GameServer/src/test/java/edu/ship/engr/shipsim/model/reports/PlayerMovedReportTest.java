package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Merlin
 */
@GameTest("GameServer")
public class PlayerMovedReportTest
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
        assertEquals(new Position(3, 4), report.getPosition());
        assertEquals("x", report.getMapName());
    }

    /**
     * Make sure the equals contract is obeyed
     */
    @Test
    public void equalsContract()
    {
        EqualsVerifier.forClass(PlayerMovedReport.class).withRedefinedSuperclass().verify();
    }
}
