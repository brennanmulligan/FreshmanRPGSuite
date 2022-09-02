package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.dataDTO.LevelManagerDTO;
import edu.ship.engr.shipsim.datasource.LevelRecord;
import edu.ship.engr.shipsim.datasource.ServerSideTest;
import edu.ship.engr.shipsim.model.Player;
import edu.ship.engr.shipsim.model.PlayerManager;
import edu.ship.engr.shipsim.model.QuestManager;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the ExperienceChangedReport class functionality
 *
 * @author Olivia
 * @author LaVonne
 */
public class ExperienceChangedReportTest extends ServerSideTest
{
    /**
     * reset the necessary singletons
     */
    @Before
    public void localSetUp()
    {
        QuestManager.resetSingleton();
    }

    /**
     * Tests that we can create a ExperienceChangedReport and get its experience
     * points and playerID
     */
    @Test
    public void testCreateReport()
    {
        Player john = PlayerManager.getSingleton().addPlayer(1);
        LevelRecord expected = LevelManagerDTO.getSingleton().getLevelForPoints(john.getExperiencePoints());
        ExperienceChangedReport report = new ExperienceChangedReport(john.getPlayerID(), john.getExperiencePoints(),
                expected);
        assertEquals(john.getExperiencePoints(), report.getExperiencePoints());
        assertEquals(expected, report.getRecord());
    }

    /**
     * Make sure the equals contract is obeyed
     */
    @Test
    public void equalsContract()
    {
        EqualsVerifier.forClass(ExperienceChangedReport.class).verify();
    }
}
