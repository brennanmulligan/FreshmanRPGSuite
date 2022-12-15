package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.StateAccumulator;
import edu.ship.engr.shipsim.communication.messages.QuestStateChangeMessage;
import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import edu.ship.engr.shipsim.datatypes.QuestsForTest;
import edu.ship.engr.shipsim.model.reports.QuestStateChangeReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetQuestManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Andrew
 * @author Steve
 * @author Matt
 * @author Olivia
 * @author LaVonne
 */
@GameTest("GameServer")
@ResetQuestManager
public class QuestStateChangeMessagePackerTest
{
    /**
     * Test that we pack a PlayerMovedReport
     */
    @Test
    public void testReportTypeWePack()
    {
        QuestStateChangeMessagePacker packer = new QuestStateChangeMessagePacker();
        assertEquals(QuestStateChangeReport.class, packer.getReportTypesWePack().get(0));
        assertEquals(1, packer.getReportTypesWePack().size());
    }

    /**
     * If we are notified about a player other than the one we are associated
     * with, we shouldn't pack a message
     */
    @Test
    public void ifThePlayerIsNotOnThisConnection()
    {
        StateAccumulator stateAccumulator = new StateAccumulator(null);
        stateAccumulator.setPlayerId(1);

        QuestStateChangeReport report = new QuestStateChangeReport(2, QuestsForTest.ONE_BIG_QUEST.getQuestID(),
                QuestsForTest.ONE_BIG_QUEST.getQuestTitle(), QuestsForTest.ONE_BIG_QUEST.getQuestDescription(),
                QuestStateEnum.AVAILABLE);
        QuestStateChangeMessagePacker packer = new QuestStateChangeMessagePacker();
        packer.setAccumulator(stateAccumulator);

        QuestStateChangeMessage msg = (QuestStateChangeMessage) packer.pack(report);
        assertNull(msg);
    }

    /**
     * If the msg is to the player we are packing, the message should contain
     * the details of the quest
     */
    @Test
    public void testPackedMessageIsBuiltCorrectly()
    {
        StateAccumulator stateAccumulator = new StateAccumulator(null);
        stateAccumulator.setPlayerId(1);

        QuestStateChangeReport report = new QuestStateChangeReport(stateAccumulator.getPlayerID(),
                QuestsForTest.ONE_BIG_QUEST.getQuestID(), QuestsForTest.ONE_BIG_QUEST.getQuestTitle(),
                QuestsForTest.ONE_BIG_QUEST.getQuestDescription(), QuestStateEnum.TRIGGERED);
        QuestStateChangeMessagePacker packer = new QuestStateChangeMessagePacker();
        packer.setAccumulator(stateAccumulator);

        QuestStateChangeMessage msg = (QuestStateChangeMessage) packer.pack(report);
        assertEquals(QuestsForTest.ONE_BIG_QUEST.getQuestID(), msg.getQuestID());
        assertEquals(QuestsForTest.ONE_BIG_QUEST.getQuestDescription(), msg.getQuestDescription());
        assertEquals(QuestStateEnum.TRIGGERED, msg.getNewState());

    }
}
