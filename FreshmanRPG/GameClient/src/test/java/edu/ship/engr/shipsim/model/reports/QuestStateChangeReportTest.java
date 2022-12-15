package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Ryan
 */
@GameTest("GameClient")
public class QuestStateChangeReportTest
{

    /**
     * Test creating a report and test its getters
     */
    @Test
    public void testInitialization()
    {
        QuestStateChangeReport r = new QuestStateChangeReport(42, 1, "Big Quest", QuestStateEnum.TRIGGERED);

        assertEquals(42, r.getPlayerID());
        assertEquals(1, r.getQuestID());
        assertEquals("Big Quest", r.getQuestDescription());
        assertEquals(QuestStateEnum.TRIGGERED, r.getNewState());
    }

}
