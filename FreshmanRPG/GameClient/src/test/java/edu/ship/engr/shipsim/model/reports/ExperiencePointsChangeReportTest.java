package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datasource.LevelRecord;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author nk3668
 */
@GameTest("GameClient")
public class ExperiencePointsChangeReportTest
{

    /**
     * Basic Initialization of the report containing the experience points
     * and the level record.
     */
    @Test
    public void testReportInitialization()
    {
        int exp = 1000;
        LevelRecord rec = new LevelRecord("Master Exploder", 100, 10, 7);
        ExperiencePointsChangeReport report = new ExperiencePointsChangeReport(exp, rec);

        assertEquals(exp, report.getExperiencePoints());
        assertEquals(rec, report.getLevelRecord());
    }

    /**
     * Testing the equality of two instances of this class
     */
    @Test
    public void testEqualsContract()
    {
        EqualsVerifier.forClass(DoubloonChangeReport.class).verify();
    }
}
