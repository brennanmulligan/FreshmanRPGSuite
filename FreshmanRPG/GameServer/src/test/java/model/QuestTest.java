package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import dataENUM.ObjectiveCompletionType;
import datasource.ServerSideTest;
import org.junit.Test;

import criteria.CriteriaStringDTO;
import criteria.GameLocationDTO;
import dataENUM.QuestCompletionActionType;
import datatypes.Position;

/**
 * This class holds the tests for the Quest class
 *
 * @author Scott Lantz, LaVonne Diller
 *
 */
public class QuestTest extends ServerSideTest
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
				new GregorianCalendar(9999, Calendar.MARCH, 21).getTime());

		assertEquals(245, q.getQuestID());
		assertEquals("TITLE!!!!", q.getTitle());
		assertEquals("I am a description", q.getDescription());
		assertEquals("HappyZone", q.getMapName());
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
		QuestRecord q = new QuestRecord(-1, null, null, null, null, null, 42, 45, null, null, null, null);
		ArrayList<ObjectiveRecord> objectives = new ArrayList<>();
		objectives.add(new ObjectiveRecord(5, 42, "Merlin Zone", 4, ObjectiveCompletionType.CHAT,
				new CriteriaStringDTO("Henry")));
		objectives.add(new ObjectiveRecord(5, 420, "Library Quest", 8, ObjectiveCompletionType.MOVEMENT,
				new GameLocationDTO("quad.tmx", new Position(42, 3))));
		Position pos = new Position(22, 20);

		q.setQuestID(44);
		q.setTitle("title");
		q.setDescription("I am set");
		q.setMapName("Map Name");
		q.setPos(pos);
		q.setObjectives(objectives);

		assertEquals(44, q.getQuestID());
		assertEquals("title", q.getTitle());
		assertEquals("I am set", q.getDescription());
		assertEquals("Map Name", q.getMapName());
		assertEquals(pos, q.getPos());
		assertEquals(objectives, q.getObjectives());
	}
}
