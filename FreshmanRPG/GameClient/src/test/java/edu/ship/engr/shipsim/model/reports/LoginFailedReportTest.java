package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author mk3969
 * <p>
 * Tests to see if LoginFailedReport is working properly.
 */
@GameTest("GameClient")
public class LoginFailedReportTest
{

    /**
     * make sure it gets built correctly
     */
    @Test
    public void creation()
    {
        LoginFailedReport report = new LoginFailedReport("Login Failed");
        assertEquals("Login Failed", report.toString());
    }

    /**
     * Make sure the equals contract is obeyed
     */
    @Test
    public void equalsContract()
    {
        EqualsVerifier.forClass(LoginFailedReport.class).verify();
    }

}
