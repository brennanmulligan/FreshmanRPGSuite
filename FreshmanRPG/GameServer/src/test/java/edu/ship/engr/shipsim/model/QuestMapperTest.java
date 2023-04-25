package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataENUM.QuestCompletionActionType;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.ObjectivesForTest;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.datatypes.QuestsForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetQuestManager;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests the QuestMapper class
 *
 * @author Scott Bucher
 */
@GameTest("GameServer")
@ResetQuestManager
public class QuestMapperTest
{

    /**
     * Check to make sure that a newly-created mapper has the correct values.
     *
     * @throws DatabaseException If there is an error creating the mapper.
     */
    @Test
    public void canCreateNew() throws DatabaseException, ParseException
    {
        QuestRecord quest = getQuestWeAreCreating();
        QuestMapper mapper = createMapperForQuest(quest);
        // Since the initial quest record for creation is an id of -1, we need to set the id to the generated one
        quest.setQuestID(mapper.getQuest().getQuestID());
        assertQuestEquals(quest, mapper.getQuest());
    }

    /**
     * Check to make sure that a newly-created mapper actually saves to the
     * database
     *
     * @throws DatabaseException If there is an error when creating the mapper
     */
    @Test
    public void canCreateNewPersist() throws DatabaseException, ParseException
    {
        QuestRecord quest = getQuestWeAreCreating();
        QuestMapper created = createMapperForQuest(quest);
        quest.setQuestID(created.getQuest().getQuestID());
        QuestMapper found =
                findMapperForID(created.getQuest().getQuestID());
        assertQuestEquals(quest, found.getQuest());
    }

    /**
     * Make sure the mapper retrieves all the necessary information for the
     * quest it is finding
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void canFindExisting() throws DatabaseException
    {
        QuestMapper questMapper = getMapper();
        QuestRecord quest = questMapper.getQuest();
        QuestsForTest testQuest = getQuestWeAreTesting();
        assertQuestEquals(testQuest, quest);

        ArrayList<ObjectivesForTest> expectedObjectives = getObjectivesForTheQuestWeAreTesting();
        assertObjectiveListEquals(expectedObjectives, quest.getObjectives());
    }

    /**
     * Make sure that all of the relevant information gets persisted to the data
     * source
     *
     * @throws DatabaseException               shouldn't
     */
    @Test
    public void persists()
            throws DatabaseException, ParseException
    {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        QuestMapper qm = getMapper();
        QuestRecord q = qm.getQuest();
        q.setTitle("New Title");
        q.setDescription("New Description");
        q.setTriggerMapName("New Map Name");
        q.setPosition(new Position(1, 2));
        q.setExperiencePointsGained(420);
        q.setObjectivesForFulfillment(0);
        q.setCompletionActionType(QuestCompletionActionType.NO_ACTION);
        q.setCompletionActionParameter(null);
        q.setStartDate(formatter.parse("01-03-1996"));
        q.setEndDate(formatter.parse("07-15-2014"));
        q.setEasterEgg(false);

        qm.persist();

        QuestMapper qm2 = new QuestMapper(q.getQuestID());
        assertEquals(q, qm2.getQuest());
    }

    /**
     * Make sure the mapper get can all the tests and that those tests have the
     * correct objectives in them
     */
    @Test
    public void getAllQuestsTest()
    {
        ArrayList<QuestRecord> foundQuests = QuestMapper.getAllQuests();
        ArrayList<QuestsForTest> expectedQuests = new ArrayList<>(
                Arrays.asList(QuestsForTest.values()));

        assertEquals(expectedQuests.size(), foundQuests.size());

        ArrayList<ObjectivesForTest> expectedObjectives = new ArrayList<>(
                Arrays.asList(ObjectivesForTest.values()));

        for (QuestRecord foundQuest : foundQuests)
        {
            ArrayList<ObjectivesForTest> expectedObjectivesForQuest = new ArrayList<>();
            for (ObjectivesForTest objective : expectedObjectives)
            {
                if (objective.getQuestID() == foundQuest.getQuestID())
                {
                    expectedObjectivesForQuest.add(objective);
                }
            }

            assertEquals(foundQuest.getObjectives().size(), expectedObjectivesForQuest.size());

            // Check if the values of each objective for this quest are correct
            assertObjectiveListEquals(expectedObjectivesForQuest, foundQuest.getObjectives());
        }

    }

    /**
     * Should be able to coordinate the removal of a quest from the data source
     * layer.
     */
    @Test
    public void removeTest()
    {
        assertThrows(DatabaseException.class, () ->
        {
            QuestsForTest quest = QuestsForTest.DOUBLOON_QUEST;
            QuestMapper mapper = new QuestMapper(quest.getQuestID());

            mapper.remove();

            new QuestMapper(quest.getQuestID());
        });
    }

    protected QuestRecord getQuestWeAreCreating() throws ParseException
    {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        return new QuestRecord(-1, "Test Quest", "Test Quest", "map1.tmx",
                new Position(1, 2), null, 420, 0,
                QuestCompletionActionType.NO_ACTION,
                null, formatter.parse("01-03-1996"),
                formatter.parse("07-15-2014"), true);
    }

    protected QuestMapper findMapperForID(int questId) throws DatabaseException
    {
        return new QuestMapper(questId);
    }

    protected QuestMapper createMapperForQuest(QuestRecord quest)
            throws DatabaseException
    {
        return new QuestMapper(quest.getTitle(),
                quest.getDescription(),
                quest.getTriggerMapName(), quest.getPosition(),
                quest.getObjectives(),
                quest.getObjectivesForFulfillment(),
                quest.getExperiencePointsGained(),
                quest.getCompletionActionType(),
                quest.getCompletionActionParameter(),
                quest.getStartDate(), quest.getEndDate(), quest.isEasterEgg());
    }

    protected void assertQuestEquals(QuestRecord expected, QuestRecord actual)
    {
        assertObjectiveRecordListEquals(expected.getObjectives(),
                actual.getObjectives());
        assertEquals(expected.getQuestID(), actual.getQuestID());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getTriggerMapName(), actual.getTriggerMapName());
        assertEquals(expected.getPosition(), actual.getPosition());
        assertEquals(expected.getExperiencePointsGained(),
                actual.getExperiencePointsGained());
        assertEquals(expected.getObjectivesForFulfillment(),
                actual.getObjectivesForFulfillment());
        assertEquals(expected.getCompletionActionType(),
                actual.getCompletionActionType());
        assertEquals(expected.getCompletionActionParameter(),
                actual.getCompletionActionParameter());
        assertEquals(expected.getStartDate(), actual.getStartDate());
        assertEquals(expected.getEndDate(), actual.getEndDate());
        assertEquals(expected.isEasterEgg(), actual.isEasterEgg());
    }

    protected void assertQuestEquals(QuestsForTest expected, QuestRecord actual)
    {
        assertEquals(expected.getQuestID(), actual.getQuestID());
        assertEquals(expected.getQuestTitle(), actual.getTitle());
        assertEquals(expected.getQuestDescription(), actual.getDescription());
        assertEquals(expected.getMapName(), actual.getTriggerMapName());
        assertEquals(expected.getPosition(), actual.getPosition());
        assertEquals(expected.getExperienceGained(),
                actual.getExperiencePointsGained());
        assertEquals(expected.getObjectiveForFulfillment(),
                actual.getObjectivesForFulfillment());
        assertEquals(expected.getCompletionActionType(),
                actual.getCompletionActionType());
        assertEquals(expected.getCompletionActionParameter(),
                actual.getCompletionActionParameter());
        assertEquals(expected.getStartDate(), actual.getStartDate());
        assertEquals(expected.getEndDate(), actual.getEndDate());
        assertEquals(expected.isEasterEgg(), actual.isEasterEgg());
    }

    protected void assertObjectiveEquals(ObjectivesForTest expected, ObjectiveRecord actual)
    {
        assertEquals(expected.getObjectiveID(), actual.getObjectiveID());
        assertEquals(expected.getQuestID(), actual.getQuestID());
        assertEquals(expected.getExperiencePointsGained(), actual.getExperiencePointsGained());
        assertEquals(expected.getCompletionCriteria().toString(), actual.getCompletionCriteria().toString());
        assertEquals(expected.getCompletionType(), actual.getCompletionType());
        assertEquals(expected.getObjectiveDescription(), actual.getObjectiveDescription());
    }

    protected void assertObjectiveListEquals(ArrayList<ObjectivesForTest> expectedObjectives, ArrayList<ObjectiveRecord> objectives)
    {
        for (ObjectiveRecord objective : objectives)
        {
            boolean found = false;
            for (ObjectivesForTest expectedObjective : expectedObjectives)
            {
                if (objective.getObjectiveID() == expectedObjective.getObjectiveID())
                {
                    found = true;
                    assertObjectiveEquals(expectedObjective, objective);
                }
            }
            if (!found)
            {
                throw new AssertionError("Objective not found");
            }
        }
    }

    protected void assertObjectiveEquals(ObjectiveRecord expected, ObjectiveRecord actual)
    {
        assertEquals(expected.getObjectiveID(), actual.getObjectiveID());
        assertEquals(expected.getQuestID(), actual.getQuestID());
        assertEquals(expected.getExperiencePointsGained(), actual.getExperiencePointsGained());
        assertEquals(expected.getCompletionCriteria().toString(), actual.getCompletionCriteria().toString());
        assertEquals(expected.getCompletionType(), actual.getCompletionType());
        assertEquals(expected.getObjectiveDescription(), actual.getObjectiveDescription());
    }

    protected void assertObjectiveRecordListEquals(ArrayList<ObjectiveRecord> expectedObjectives, ArrayList<ObjectiveRecord> objectives)
    {
        for (ObjectiveRecord objective : objectives)
        {
            boolean found = false;
            for (ObjectiveRecord expectedObjective : expectedObjectives)
            {
                if (objective.getObjectiveID() == expectedObjective.getObjectiveID())
                {
                    found = true;
                    assertObjectiveEquals(expectedObjective, objective);
                }
            }
            if (!found)
            {
                throw new AssertionError("Objective not found");
            }
        }
    }

    /**
     * Quest we are testing
     *
     * @return the quest whose mapper we are testing
     */
    protected QuestsForTest getQuestWeAreTesting()
    {
        return QuestsForTest.ONE_SAME_LOCATION_QUEST;
    }

    protected ArrayList<ObjectivesForTest> getObjectivesForTheQuestWeAreTesting()
    {
        ArrayList<ObjectivesForTest> objectives = new ArrayList<>();
        int questId = getQuestWeAreTesting().getQuestID();

        for (ObjectivesForTest objective : ObjectivesForTest.values())
        {
            if (objective.getQuestID() == questId)
            {
                objectives.add(objective);
            }
        }

        return objectives;
    }

    /**
     * @return the mapper we are testing
     * @throws DatabaseException if we can't create the mapper
     */
    protected QuestMapper getMapper() throws DatabaseException
    {
        return findMapperForID(getQuestWeAreTesting().getQuestID());
    }

}
