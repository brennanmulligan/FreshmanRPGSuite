package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Merlin
 */
@GameTest("LoginServer")
public class LoginSuccessfulReportTest
{
    /**
     * make sure it gets built correctly
     */
    @Test
    public void creation()
    {
        LoginSuccessfulReport report = new LoginSuccessfulReport(42, "LLL", 56, 0.76);
        assertEquals(42, report.getPlayerID());
        assertEquals("LLL", report.getHostname());
        assertEquals(56, report.getPort());
        assertEquals(0.76, report.getPin(), 0.00001);
    }

    /**
     * Make sure the equals contract is obeyed
     */
    @Test
    public void equalsContract()
    {
        EqualsVerifier.forClass(LoginSuccessfulReport.class).withRedefinedSuperclass().withIgnoredFields("pin").verify();
    }
}
