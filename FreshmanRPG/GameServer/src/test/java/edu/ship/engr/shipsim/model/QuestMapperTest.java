package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataENUM.QuestCompletionActionType;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.datatypes.QuestsForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetQuestManager;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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
        // Since the initial quest record for creation is an id of -1, we need to set the id to the generated one
        quest.setQuestID(created.getQuest().getQuestID());
        QuestMapper found =
                findMapperForID(created.getQuest().getQuestID());
        assertQuestEquals(quest, found.getQuest());
    }

    /**
     * make sure the mapper retrieves all of the necessary information for the
     * quest it is finding Added Section to the test
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void canFindExisting() throws DatabaseException
    {
        QuestMapper qm = getMapper();
        QuestRecord q = qm.getQuest();
        QuestsForTest testQuest = getQuestWeAreTesting();
        assertQuestEquals(testQuest, q);
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
        q.setMapName("New Map Name");
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
                quest.getMapName(), quest.getPosition(),
                quest.getObjectivesForFulfillment(),
                quest.getExperiencePointsGained(),
                quest.getCompletionActionType(),
                quest.getCompletionActionParameter(),
                quest.getStartDate(), quest.getEndDate(), quest.isEasterEgg());
    }

    protected void assertQuestEquals(QuestRecord expected, QuestRecord actual)
    {
        assertEquals(expected.getQuestID(), actual.getQuestID());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getMapName(), actual.getMapName());
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
        assertEquals(expected.getMapName(), actual.getMapName());
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

    /**
     * Quest we are testing
     *
     * @return the quest whose mapper we are testing
     */
    protected QuestsForTest getQuestWeAreTesting()
    {
        return QuestsForTest.EASTER_EGG_QUEST;
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
