package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.Player;
import edu.ship.engr.shipsim.model.PlayerManager;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Matthew Croft
 */
@GameTest("GameServer")
public class DoubloonsChangeReportTest
{

    /**
     * Tests that we can create a DoubloonChangeReport and get its
     * doubloons and playerID
     */
    @Test
    public void testCreateReport()
    {
        Player john = PlayerManager.getSingleton().addPlayer(1);
        DoubloonChangeReport report = new DoubloonChangeReport(john.getPlayerID(),
                john.getDoubloons(), john.getBuffPool());
        assertEquals(john.getDoubloons(), report.getDoubloons());
        assertEquals(john.getBuffPool(), report.getBuffPool());
    }

    /**
     * Make sure the equals contract is obeyed
     */
    @Test
    public void equalsContract()
    {
        EqualsVerifier.forClass(DoubloonChangeReport.class).withRedefinedSuperclass().verify();
    }
}
