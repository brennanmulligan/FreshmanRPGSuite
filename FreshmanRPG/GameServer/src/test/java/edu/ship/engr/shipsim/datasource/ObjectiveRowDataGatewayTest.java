package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.datatypes.QuestsForTest;
import edu.ship.engr.shipsim.model.ObjectiveRecord;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@GameTest("GameServer")
public class ObjectiveRowDataGatewayTest
{



    /**
     * Tests to see if the finder constructor of the ObjectiveRowDataGateway work
     * @throws DatabaseException
     */
    @Test
    public void testFindConstructor() throws DatabaseException
    {
        final int questId = QuestsForTest.CHAT_TO_AN_NPC_QUEST.getQuestID();
        final ArrayList<ObjectiveRecord> objectives =
                ObjectiveTableDataGateway.getSingleton().getObjectivesForQuest(questId);
        ObjectiveRecord objectiveToBeCompared = objectives.get(0);
        ObjectiveRowDataGateway gateway = new ObjectiveRowDataGateway(
                objectiveToBeCompared.getObjectiveID(),
                objectiveToBeCompared.getQuestID());

        assertEquals(gateway.getObjectiveId(), objectiveToBeCompared.getObjectiveId());
        assertEquals(gateway.getObjectiveDescription(), objectiveToBeCompared.getObjectiveDescription());
        assertEquals(gateway.getQuestId(), objectiveToBeCompared.getQuestId());

    }

    @Test
    public void testDeleteMethod() throws DatabaseException
    {
        //1,5
        ObjectiveRowDataGateway testQuest = new ObjectiveRowDataGateway(1, 5);
        testQuest.remove();

        assertThrows(DatabaseException.class, () ->
        {
            ObjectiveRowDataGateway deletedData = new ObjectiveRowDataGateway(1, 5);
        });

        ObjectiveTableDataGateway.createRow(testQuest.getObjectiveId(),
                testQuest.getObjectiveDescription(),
                testQuest.getQuestId(),
                testQuest.getExperiencePointsEarned(),
                testQuest.getCompletionType(),
                testQuest.getCompletionCriteria());
    }
}
