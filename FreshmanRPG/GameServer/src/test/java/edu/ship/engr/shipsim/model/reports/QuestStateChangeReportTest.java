package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.ServerSideTest;
import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import edu.ship.engr.shipsim.datatypes.QuestsForTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Merlin
 */
public class QuestStateChangeReportTest extends ServerSideTest
{


    /**
     * make sure it gets built correctly
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void creation() throws DatabaseException
    {
        QuestStateChangeReport report = new QuestStateChangeReport(1, QuestsForTest.ONE_BIG_QUEST.getQuestID(),
                QuestsForTest.ONE_BIG_QUEST.getQuestTitle(), QuestsForTest.ONE_BIG_QUEST.getQuestDescription(),
                QuestStateEnum.COMPLETED);

        assertEquals(1, report.getPlayerID());
        assertEquals(QuestsForTest.ONE_BIG_QUEST.getQuestID(), report.getQuestID());
        assertEquals(QuestsForTest.ONE_BIG_QUEST.getQuestDescription(), report.getQuestDescription());
        assertEquals(QuestStateEnum.COMPLETED, report.getNewState());
    }

    /**
     * Make sure the equals contract is obeyed
     */
    @Test
    public void equalsContract()
    {
        EqualsVerifier.forClass(QuestStateChangeReport.class).verify();
    }
}
