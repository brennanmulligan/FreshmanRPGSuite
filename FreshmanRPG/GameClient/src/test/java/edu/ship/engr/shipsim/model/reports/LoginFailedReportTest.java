package edu.ship.engr.shipsim.model.reports;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author mk3969
 * <p>
 * Tests to see if LoginFailedReport is working properly.
 */
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
