package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.dataDTO.LevelManagerDTO;
import edu.ship.engr.shipsim.datasource.LevelRecord;
import edu.ship.engr.shipsim.model.Player;
import edu.ship.engr.shipsim.model.PlayerManager;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetQuestManager;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the ExperienceChangedReport class functionality
 *
 * @author Olivia
 * @author LaVonne
 */
@GameTest("GameServer")
@ResetQuestManager
public class ExperienceChangedReportTest
{
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
        EqualsVerifier.forClass(ExperienceChangedReport.class).withRedefinedSuperclass().verify();
    }
}
