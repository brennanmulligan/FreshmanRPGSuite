package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.datatypes.ObjectivesForTest;
import edu.ship.engr.shipsim.datatypes.QuestsForTest;
import edu.ship.engr.shipsim.model.ObjectiveRecord;
import edu.ship.engr.shipsim.model.QuestRecord;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * An abstract class that tests quest table data gateway
 *
 * @author Scott Bucher
 */
@GameTest("GameServer")
public class QuestTableDataGatewayTest
{

    /**
     * @return the gateway we should test
     */
    public QuestTableDataGateway getGatewaySingleton()
    {
        return QuestTableDataGateway.getSingleton();
    }


    /**
     * Ensures that the gateways are able to retrieve all quests.
     *
     * @throws DatabaseException - problem reading Quests table
     */
    @Test
    public void testGetAllQuests() throws DatabaseException
    {
        QuestTableDataGateway gateway = getGatewaySingleton();
        assertQuestListMatchesTestData(gateway.getAllQuests());
    }

    private void assertQuestListMatchesTestData(List<QuestRecord> actual)
    {
        List<QuestsForTest> expected = Arrays.asList(QuestsForTest.values());
        assertEquals(expected.size(), actual.size());

        for (int i = 0; i < expected.size(); i++)
        {
            assertQuestsAreEqual(expected.get(i), actual.get(i));
        }
    }

    private void assertQuestsAreEqual(QuestsForTest expected,
                                      QuestRecord actual)
    {
        assertQuestObjectiveListMatchesTestData(actual);
        assertEquals(expected.getQuestID(), actual.getQuestID());
        assertEquals(expected.getQuestTitle(), actual.getTitle());
        assertEquals(expected.getQuestDescription(), actual.getDescription());
        assertEquals(expected.getMapName(), actual.getTriggerMapName());
        assertEquals(expected.getPosition(), actual.getPos());
        assertEquals(expected.getExperienceGained(),
                actual.getExperiencePointsGained());
        assertEquals(expected.getObjectiveForFulfillment(),
                actual.getObjectivesForFulfillment());
        assertEquals(expected.getCompletionActionType(),
                actual.getCompletionActionType());
        assertEquals(expected.getStartDate(), actual.getStartDate());
        assertEquals(expected.getEndDate(), actual.getEndDate());
    }

    private void assertQuestObjectiveListMatchesTestData(QuestRecord quest)
    {
        List<ObjectiveRecord> actual = new ArrayList<>();
        try
        {
            actual = ObjectiveTableDataGateway.getSingleton()
                    .getObjectivesForQuest(quest.getQuestID());
        }
        catch (DatabaseException ignored)
        {
        }

        // Get all objectives that have the same questID as the quest we are
        // testing
        List<ObjectivesForTest> expected =
                Arrays.asList(Arrays.stream(ObjectivesForTest.values())
                        .filter(objective -> objective.getQuestID() ==
                                quest.getQuestID())
                        .toArray(ObjectivesForTest[]::new));

        assertEquals(expected.size(), actual.size());

        for (int i = 0; i < expected.size(); i++)
        {
            assertAdventuresAreEqual(expected.get(i), actual.get(i));
        }
    }

    private void assertAdventuresAreEqual(ObjectivesForTest expected,
                                          ObjectiveRecord actual)
    {
        assertEquals(expected.getObjectiveID(), actual.getObjectiveID());
        assertEquals(expected.getObjectiveDescription(),
                actual.getObjectiveDescription());
        assertEquals(expected.getQuestID(), actual.getQuestID());
        assertEquals(expected.getExperiencePointsGained(),
                actual.getExperiencePointsGained());
    }

}
