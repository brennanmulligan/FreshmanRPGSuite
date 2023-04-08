package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.criteria.QuestCompletionActionParameter;
import edu.ship.engr.shipsim.dataENUM.QuestCompletionActionType;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.datatypes.QuestsForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests required of all player gateways
 *
 * @author Merlin
 */
@GameTest("GameServer")
public class QuestRowDataGatewayTest
{

    protected QuestRowDataGateway gateway;

    /**
     * Makes sure to reset the gateway
     *
     * @throws DatabaseException shouldn't
     */
    @BeforeEach
    public void localSetup() throws DatabaseException
    {
        gateway = this.findGateway(1);
    }

    /**
     * @throws DatabaseException - if it fails
     */
    @Test
    public void addQuest() throws DatabaseException
    {
        QuestsForTest quest = QuestsForTest.ONE_BIG_QUEST;

        int id = createGateway("TEST QUEST A", quest.getQuestDescription(),
                quest.getMapName(), quest.getPosition(), quest.getExperienceGained(),
                quest.getObjectiveForFulfillment(), quest.getCompletionActionType(),
                quest.getCompletionActionParameter(), quest.getStartDate(),
                quest.getEndDate());

        gateway = findGateway(id);
        assertNotNull(gateway);
        assertEquals("TEST QUEST A", gateway.getQuestTitle());
        assertEquals(quest.getQuestDescription(), gateway.getQuestDescription());
        assertEquals(quest.getMapName(), gateway.getTriggerMapName());
        assertEquals(quest.getPosition(), gateway.getTriggerPosition());
        assertEquals(quest.getExperienceGained(), gateway.getExperiencePointsGained());
        assertEquals(quest.getObjectiveForFulfillment(),
                gateway.getObjectivesForFulfillment());
        assertEquals(quest.getCompletionActionType(), gateway.getCompletionActionType());
        assertEquals(quest.getCompletionActionParameter(),
                gateway.getCompletionActionParameter());
        assertEquals(quest.getStartDate(), gateway.getStartDate());
        assertEquals(quest.getEndDate(), gateway.getEndDate());
    }

    /**
     * There are three quests in QuestsForTest that are on the same location. Make
     * sure we get them all
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void canFindQuestsForMapLocation() throws DatabaseException
    {
        QuestsForTest q = QuestsForTest.THE_OTHER_QUEST;
        ArrayList<Integer> questIDs =
                findQuestsForMapLocation(q.getMapName(), q.getPosition());
        List<QuestsForTest> test = new ArrayList<>();
        for (QuestsForTest t : QuestsForTest.values())
        {
            if ((t.getMapName().equals(q.getMapName())) &&
                    (t.getPosition().equals(q.getPosition())))
            {
                test.add(t);
            }
        }
        assertEquals(test.size(), questIDs.size());
        assertTrue(questIDs.contains(q.getQuestID()));
        assertTrue(questIDs.contains(QuestsForTest.ONE_SAME_LOCATION_QUEST.getQuestID()));
    }

    /**
     * test that persist updates the stored information
     *
     * @throws DatabaseException if data is not saved in datasource
     */
    @Test
    public void canUpdateQuests() throws DatabaseException
    {
        QuestsForTest quest = QuestsForTest.ONE_BIG_QUEST;

        gateway = findGateway(quest.getQuestID());

        gateway.setObjectivesForFulfillment(7);
        gateway.setCompletionActionParameter(null);
        gateway.setCompletionActionType(QuestCompletionActionType.NO_ACTION);
        gateway.setEndDate(new GregorianCalendar(9999, Calendar.MARCH, 23).getTime());
        gateway.setExperiencePointsGained(7);
        gateway.setQuestDescription("testDescription");
        gateway.setQuestTitle("f");
        gateway.setStartDate(
                new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime());
        gateway.setTriggerMapName("test.tmx");
        gateway.setTriggerPosition(new Position(2, 32));
        gateway.setEasterEgg(false);

        gateway.persist();

        // set to new gateway to make sure changes have been saved
        QuestRowDataGateway after = findGateway(quest.getQuestID());

        assertEquals(7, after.getObjectivesForFulfillment());
        assertNull(after.getCompletionActionParameter());
        assertEquals(after.getCompletionActionType(),
                QuestCompletionActionType.NO_ACTION);
        assertEquals(after.getEndDate(),
                new GregorianCalendar(9999, Calendar.MARCH, 23).getTime());
        assertEquals(after.getExperiencePointsGained(), 7);
        assertEquals(after.getQuestDescription(), "testDescription");
        assertEquals(after.getQuestTitle(), "f");
        assertEquals(after.getStartDate(),
                new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime());
        assertEquals(after.getTriggerMapName(), "test.tmx");
        assertEquals(after.getTriggerPosition(), new Position(2, 32));
    }

    /**
     * Tests deleting a quest
     */
    @Test
    public void deleteQuest()
    {
        assertThrows(DatabaseException.class, () ->
        {
            int questId = QuestsForTest.EXPLORATION_QUEST.getQuestID();
            QuestRowDataGateway questGateway = findGateway(questId);
            // Should have found the quest and deleted it
            questGateway.remove();
            // Should return false since the quest doesn't exist anymore
            findGateway(questId);
        });
    }

    /**
     * make sure we get the right exception if we try to find a quest who doesn't
     * exist
     */
    @Test
    public void findNotExisting()
    {
        assertThrows(DatabaseException.class, () ->
        {
            gateway = findGateway(11111);
        });
    }

    /**
     * Makes sure we can find an id based on a name
     */
    @Test
    public void findIDFromTitleTest() throws DatabaseException
    {
        assertEquals(1, QuestRowDataGateway.findIDFromTitle("t0"));
    }

    /**
     * Make sure we can retrieve a specific question
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void finder() throws DatabaseException
    {
        QuestsForTest quest = QuestsForTest.ONE_BIG_QUEST;
        gateway = findGateway(QuestsForTest.ONE_BIG_QUEST.getQuestID());
        assertEquals(quest.getQuestID(), gateway.getQuestID());
        assertEquals(quest.getQuestDescription(), gateway.getQuestDescription());
        assertEquals(quest.getMapName(), gateway.getTriggerMapName());
        assertEquals(quest.getPosition(), gateway.getTriggerPosition());
        assertEquals(quest.getObjectiveForFulfillment(),
                gateway.getObjectivesForFulfillment());
        assertEquals(quest.getExperienceGained(), gateway.getExperiencePointsGained());
        assertEquals(quest.getCompletionActionType(), gateway.getCompletionActionType());
        assertEquals(quest.getCompletionActionParameter(),
                gateway.getCompletionActionParameter());
        assertEquals(quest.getQuestTitle(), gateway.getQuestTitle());
        assertEquals(quest.getStartDate(), gateway.getStartDate());
        assertEquals(quest.getEndDate(), gateway.getEndDate());
    }


    /**
     * Use the appropriate gateway to save to the database.
     *
     * @param title                     The quest title.
     * @param description               The quest description.
     * @param mapName                   The map name that the quest is triggered on.
     * @param position                  The position that the quest is triggered at.
     * @param experiencedGained         The amount of experienced gained for completing the quest.
     * @param objectivesForFullfillment The number of objectives that must be completed to finish the
     *                                  quest.
     * @param completionActionType      The quest completion action type.
     * @param completionActionParameter The quest completion action parameter.
     * @param startDate                 The date at which the quest is first available.
     * @param endDate                   The date at which the quest is no longer available.
     * @return The database id of the new quest.
     * @throws DatabaseException If a database error occurred.
     */
    int createGateway(String title, String description, String mapName, Position position,
                      int experiencedGained, int objectivesForFullfillment,
                      QuestCompletionActionType completionActionType,
                      QuestCompletionActionParameter completionActionParameter,
                      Date startDate, Date endDate) throws DatabaseException
    {
        QuestRowDataGateway gateway =
                new QuestRowDataGateway(title, description, mapName, position,
                        experiencedGained, objectivesForFullfillment,
                        completionActionType, completionActionParameter, startDate,
                        endDate, false);

        return gateway.getQuestID();
    }

    /**
     * Use the appropriate gateway to find the quests on a given map location
     *
     * @param mapName  the name of the map
     * @param position the position on the map
     * @return a list of quest IDs for quests that are triggered at the given
     * location
     * @throws DatabaseException shouldn't
     */
    ArrayList<Integer> findQuestsForMapLocation(String mapName, Position position)
            throws DatabaseException
    {
        return QuestRowDataGateway.findQuestsForMapLocation(mapName, position);
    }

    /**
     * Get a gateway for these tests to use
     *
     * @param questID the ID of the quest the gateway should manage
     * @return the gateway
     * @throws DatabaseException if the gateway can't be created
     */
    QuestRowDataGateway findGateway(int questID) throws DatabaseException
    {
        return new QuestRowDataGateway(questID);
    }
}
