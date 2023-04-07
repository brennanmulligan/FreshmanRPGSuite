package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Merlin
 */
@GameTest("GameClient")
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
        EqualsVerifier.forClass(LoginInitiatedReport.class).withRedefinedSuperclass().verify();
    }
}
