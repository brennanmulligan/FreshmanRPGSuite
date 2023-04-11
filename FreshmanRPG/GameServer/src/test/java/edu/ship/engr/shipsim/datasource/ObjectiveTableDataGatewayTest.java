package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.criteria.GameLocationDTO;
import edu.ship.engr.shipsim.datatypes.ObjectivesForTest;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.datatypes.QuestsForTest;
import edu.ship.engr.shipsim.model.ObjectiveRecord;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * An abstract class that tests the table data gateways into the Objective table
 *
 * @author merlin
 */
@GameTest("GameServer")
public class ObjectiveTableDataGatewayTest
{

    /**
     * We should be able to receive a list of all quests completed at a location
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void canGetCompleteByLocationObjectives() throws DatabaseException
    {
        GameLocationDTO location =
                (GameLocationDTO) (ObjectivesForTest.QUEST2_OBJECTIVE2.getCompletionCriteria());
        String mapName = location.getMapName();
        Position pos = location.getPosition();
        ObjectiveTableDataGateway gateway = getGateway();
        ArrayList<ObjectivesForTest> objective = new ArrayList<>();
        objective.add(ObjectivesForTest.QUEST2_OBJECTIVE2);
        ArrayList<ObjectiveRecord> objectivesFound =
                gateway.findObjectivesCompletedForMapLocation(mapName, pos);
        assertEquals(objective.get(0).getObjectiveDescription(),
                objectivesFound.get(0).getObjectiveDescription());
    }

    /**
     * We should be able to receive a list of all quests completed at a location regardless
     * of the completion type of the objectives
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void canGetCompleteByLocationObjectivesForBothMovementAndTimed() throws DatabaseException
    {
        GameLocationDTO location =
                (GameLocationDTO) (ObjectivesForTest.QUEST2_OBJECTIVE2.getCompletionCriteria());
        String mapName = location.getMapName();
        Position pos = location.getPosition();
        ObjectiveTableDataGateway gateway = getGateway();
        ArrayList<ObjectivesForTest> objective = new ArrayList<>();
        objective.add(ObjectivesForTest.QUEST2_OBJECTIVE2);
        objective.add(ObjectivesForTest.QUEST2_OBJECTIVE5);
        ArrayList<ObjectiveRecord> objectivesFound =
                gateway.findObjectivesCompletedForMapLocation(mapName, pos);
        assertEquals(objective.get(0).getObjectiveDescription(),
                objectivesFound.get(0).getObjectiveDescription());
    }

    /**
     * Given a quest ID, the TDG should be able to return the next appropriate objective ID.
     *
     * @throws DatabaseException - if record with that quest ID not found
     */
    @Test
    public void canGetNextObjectiveID() throws DatabaseException
    {
        final int questId = QuestsForTest.CHAT_TO_AN_NPC_QUEST.getQuestID();
        final ArrayList<ObjectiveRecord> objectives =
                getGateway().getObjectivesForQuest(questId);
        final int expected = objectives.size() + 1;

        assertEquals(expected, getGateway().getNextObjectiveID(questId));
    }

    /**
     * We should be able to retrieve the specific information about one single
     * objective
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void canGetSingleObjective() throws DatabaseException
    {
        ObjectiveTableDataGateway gateway = getGateway();
        ObjectiveRecord record =
                gateway.getObjective(ObjectivesForTest.QUEST1_OBJECTIVE_1.getQuestID(),
                        ObjectivesForTest.QUEST1_OBJECTIVE_1.getObjectiveID());
        assertEquals(ObjectivesForTest.QUEST1_OBJECTIVE_1.getObjectiveDescription(),
                record.getObjectiveDescription());
        assertEquals(ObjectivesForTest.QUEST1_OBJECTIVE_1.getObjectiveID(),
                record.getObjectiveID());
        assertEquals(ObjectivesForTest.QUEST1_OBJECTIVE_1.getExperiencePointsGained(),
                record.getExperiencePointsGained());
        assertEquals(ObjectivesForTest.QUEST1_OBJECTIVE_1.getQuestID(),
                record.getQuestID());
    }

    /**
     * @return the gateway we should test
     */
    public ObjectiveTableDataGateway getGateway()
    {
        return ObjectiveTableDataGateway.getSingleton();
    }

    /**
     *
     */
    @Test
    public void isASingleton()
    {
        ObjectiveTableDataGateway x = getGateway();
        ObjectiveTableDataGateway y = getGateway();
        assertSame(x, y);
        assertNotNull(x);
    }

    /**
     * We should be able to retrieve the specific information about one single
     * objective
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void nullForMissingObjective() throws DatabaseException
    {
        ObjectiveTableDataGateway gateway = getGateway();
        ObjectiveRecord record = gateway.getObjective(42, 16);
        assertNull(record);
    }

    /**
     * @throws DatabaseException shouldn't
     */
    @Test
    public void retrieveAllObjectivesForQuest() throws DatabaseException
    {
        ObjectiveTableDataGateway gateway = getGateway();
        ArrayList<ObjectiveRecord> records = gateway.getObjectivesForQuest(1);
        assertEquals(3, records.size());
        ObjectiveRecord record = records.get(0);
        // the records could be in either order
        if (record.getObjectiveID() ==
                ObjectivesForTest.QUEST1_OBJECTIVE_1.getObjectiveID())
        {
            assertEquals(ObjectivesForTest.QUEST1_OBJECTIVE_1.getObjectiveDescription(),
                    record.getObjectiveDescription());
            assertEquals(ObjectivesForTest.QUEST1_OBJECTIVE_1.getQuestID(),
                    record.getQuestID());
            assertEquals(ObjectivesForTest.QUEST1_OBJECTIVE_1.getExperiencePointsGained(),
                    record.getExperiencePointsGained());
            assertEquals(ObjectivesForTest.QUEST1_OBJECTIVE_1.getCompletionType(),
                    record.getCompletionType());
            assertEquals(ObjectivesForTest.QUEST1_OBJECTIVE_1.getCompletionCriteria(),
                    record.getCompletionCriteria());
            record = records.get(1);
            assertEquals(ObjectivesForTest.QUEST1_OBJECTIVE2.getObjectiveDescription(),
                    record.getObjectiveDescription());
            assertEquals(ObjectivesForTest.QUEST1_OBJECTIVE2.getQuestID(),
                    record.getQuestID());
            assertEquals(ObjectivesForTest.QUEST1_OBJECTIVE2.getExperiencePointsGained(),
                    record.getExperiencePointsGained());
        }
        else
        {
            assertEquals(ObjectivesForTest.QUEST1_OBJECTIVE2.getObjectiveID(),
                    record.getObjectiveID());
            assertEquals(ObjectivesForTest.QUEST1_OBJECTIVE2.getObjectiveDescription(),
                    record.getObjectiveDescription());
            assertEquals(ObjectivesForTest.QUEST1_OBJECTIVE2.getQuestID(),
                    record.getQuestID());
            assertEquals(ObjectivesForTest.QUEST1_OBJECTIVE2.getExperiencePointsGained(),
                    record.getExperiencePointsGained());
            record = records.get(1);
            assertEquals(ObjectivesForTest.QUEST1_OBJECTIVE_1.getObjectiveDescription(),
                    record.getObjectiveDescription());
            assertEquals(ObjectivesForTest.QUEST1_OBJECTIVE_1.getQuestID(),
                    record.getQuestID());
            assertEquals(ObjectivesForTest.QUEST1_OBJECTIVE_1.getExperiencePointsGained(),
                    record.getExperiencePointsGained());

        }
    }

}
