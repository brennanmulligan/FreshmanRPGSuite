/**
 *
 */
package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datatypes.PlayerScoreRecord;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author nk3668
 */
@GameTest("GameClient")
public class HighScoreResponseReportTest
{
    /**
     * Test the basic construction of a High Score Response Report
     */
    @Test
    public void testInitialization()
    {
        ArrayList<PlayerScoreRecord> list = new ArrayList<>();
        list.add(new PlayerScoreRecord("Ethan", 0));
        list.add(new PlayerScoreRecord("Weaver", 3));
        list.add(new PlayerScoreRecord("John", 25));
        list.add(new PlayerScoreRecord("Merlin", 9001));
        list.add(new PlayerScoreRecord("Nate", 984257));

        HighScoreResponseReport rep = new HighScoreResponseReport(list);

        assertEquals(5, rep.getScoreList().size());
    }

    /**
     * Make sure the equals contract is obeyed
     */
    @Test
    public void equalsContract()
    {
        EqualsVerifier.forClass(HighScoreResponseReport.class).verify();
    }
}
