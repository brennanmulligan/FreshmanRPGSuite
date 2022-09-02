package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.Player;
import edu.ship.engr.shipsim.model.PlayerManager;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Matthew Croft
 */
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
        EqualsVerifier.forClass(DoubloonChangeReport.class).verify();
    }
}
