package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.criteria.CriteriaStringDTO;
import edu.ship.engr.shipsim.criteria.GameLocationDTO;
import edu.ship.engr.shipsim.dataENUM.ObjectiveCompletionType;
import edu.ship.engr.shipsim.dataENUM.QuestCompletionActionType;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * This class holds the tests for the Quest class
 *
 * @author Scott Lantz, LaVonne Diller
 */
@GameTest("GameServer")
public class QuestTest
{

    /**
     * Test the initialization of Quests and its parameters
     */
    @Test
    public void testInitialize()
    {
        ArrayList<ObjectiveRecord> objectives = new ArrayList<>();
        objectives.add(new ObjectiveRecord(5, 42, "Merlin Zone", 4, ObjectiveCompletionType.CHAT,
                new CriteriaStringDTO("Henry")));
        objectives.add(new ObjectiveRecord(5, 420, "Library Quest", 8, ObjectiveCompletionType.MOVEMENT,
                new GameLocationDTO("quad.tmx", new Position(42, 3))));

        Position pos = new Position(33, 44);

        QuestRecord q = new QuestRecord(245, "TITLE!!!!", "I am a description", "HappyZone", pos, objectives, 42, 13,
                QuestCompletionActionType.NO_ACTION, null, new GregorianCalendar(2015, Calendar.MARCH, 21).getTime(),
                new GregorianCalendar(9999, Calendar.MARCH, 21).getTime(), false);

        assertEquals(245, q.getQuestID());
        assertEquals("TITLE!!!!", q.getTitle());
        assertEquals("I am a description", q.getDescription());
        assertEquals("HappyZone", q.getTriggerMapName());
        assertEquals(pos, q.getPos());
        assertEquals(q.getObjectives(), objectives);
        assertEquals(4, q.getObjectiveXP(42));
        assertEquals(8, q.getObjectiveXP(420));
        assertEquals(42, q.getExperiencePointsGained());
        assertEquals(13, q.getObjectivesForFulfillment());
        ArrayList<ObjectiveRecord> advs = q.getObjectives();
        for (ObjectiveRecord a : advs)
        {
            if (a.getObjectiveID() == 42)
            {
                assertEquals(ObjectiveCompletionType.CHAT, a.getCompletionType());
                assertEquals(new CriteriaStringDTO("Henry"), a.getCompletionCriteria());
            }
            else if (a.getObjectiveID() == 420)
            {
                assertEquals(ObjectiveCompletionType.MOVEMENT, a.getCompletionType());
                assertEquals(new GameLocationDTO("quad.tmx", new Position(42, 3)), a.getCompletionCriteria());
            }
            else
            {
                fail("Unexpected objective with description " + a.getObjectiveDescription());
            }
        }

        assertEquals(q.getStartDate(), new GregorianCalendar(2015, Calendar.MARCH, 21).getTime());
        assertEquals(q.getEndDate(), new GregorianCalendar(9999, Calendar.MARCH, 21).getTime());
    }

    /**
     * Test the setters for setting description and state of quests
     */
    @Test
    public void testSetters()
    {
        QuestRecord q = new QuestRecord(-1, null, null, null, null, null, 42, 45, null, null, null, null, false);
        ArrayList<ObjectiveRecord> objectives = new ArrayList<>();
        objectives.add(new ObjectiveRecord(5, 42, "Merlin Zone", 4, ObjectiveCompletionType.CHAT,
                new CriteriaStringDTO("Henry")));
        objectives.add(new ObjectiveRecord(5, 420, "Library Quest", 8, ObjectiveCompletionType.MOVEMENT,
                new GameLocationDTO("quad.tmx", new Position(42, 3))));
        Position pos = new Position(22, 20);

        q.setQuestID(44);
        q.setTitle("title");
        q.setDescription("I am set");
        q.setTriggerMapName("Map Name");
        q.setPos(pos);
        q.setObjectives(objectives);

        assertEquals(44, q.getQuestID());
        assertEquals("title", q.getTitle());
        assertEquals("I am set", q.getDescription());
        assertEquals("Map Name", q.getTriggerMapName());
        assertEquals(pos, q.getPos());
        assertEquals(objectives, q.getObjectives());
    }
}
