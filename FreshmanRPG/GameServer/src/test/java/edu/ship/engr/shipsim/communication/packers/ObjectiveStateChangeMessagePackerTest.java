package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.StateAccumulator;
import edu.ship.engr.shipsim.communication.messages.ObjectiveStateChangeMessage;
import edu.ship.engr.shipsim.datasource.ServerSideTest;
import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;
import edu.ship.engr.shipsim.datatypes.ObjectivesForTest;
import edu.ship.engr.shipsim.model.QuestManager;
import edu.ship.engr.shipsim.model.reports.ObjectiveStateChangeReport;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Ryan
 */
public class ObjectiveStateChangeMessagePackerTest extends ServerSideTest
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
     * Test that we pack a Objective State Change Report
     */
    @Test
    public void testReportTypeWePack()
    {
        ObjectiveStateChangeMessagePacker packer = new ObjectiveStateChangeMessagePacker();
        assertEquals(ObjectiveStateChangeReport.class, packer.getReportTypesWePack().get(0));
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

        ObjectiveStateChangeReport report = new ObjectiveStateChangeReport(stateAccumulator.getPlayerID(),
                ObjectivesForTest.QUEST1_OBJECTIVE_1.getQuestID(),
                ObjectivesForTest.QUEST1_OBJECTIVE_1.getObjectiveID(),
                ObjectivesForTest.QUEST1_OBJECTIVE_1.getObjectiveDescription(), ObjectiveStateEnum.TRIGGERED, true,
                "Henry");
        ObjectiveStateChangeMessagePacker packer = new ObjectiveStateChangeMessagePacker();
        packer.setAccumulator(stateAccumulator);

        ObjectiveStateChangeMessage msg = (ObjectiveStateChangeMessage) packer.pack(report);
        assertEquals(ObjectivesForTest.QUEST1_OBJECTIVE_1.getObjectiveID(), msg.getObjectiveID());
        assertEquals(ObjectivesForTest.QUEST1_OBJECTIVE_1.getObjectiveDescription(), msg.getObjectiveDescription());
        assertEquals(ObjectiveStateEnum.TRIGGERED, msg.getNewState());
        assertTrue(msg.isRealLifeObjective());
        assertEquals("Henry", msg.getWitnessTitle());

    }

}
