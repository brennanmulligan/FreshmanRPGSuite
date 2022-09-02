package edu.ship.engr.shipsim.model.reports;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Merlin
 */
public class LoginInitiatedReportTest
{

    /**
     * make sure it gets built correctly
     */
    @Test
    public void creation()
    {
        LoginInitiatedReport report = new LoginInitiatedReport("playername", "pw");
        assertEquals("playername", report.getPlayerName());
        assertEquals("pw", report.getPassword());
    }

    /**
     * Make sure the equals contract is obeyed
     */
    @Test
    public void equalsContract()
    {
        EqualsVerifier.forClass(LoginInitiatedReport.class).verify();
    }
}
