package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;

import org.junit.Test;

import criteria.GameLocationDTO;
import datatypes.Position;
import model.AdventureRecord;
import datatypes.AdventuresForTest;
import datatypes.QuestsForTest;

/**
 * An abstract class that tests the table data gateways into the Adventure table
 *
 * @author merlin
 *
 */
public abstract class AdventureTableDataGatewayTest
{

	/**
	 * @return the gateway we should test
	 */
	public abstract AdventureTableDataGateway getGateway();

	/**
	 *
	 */
	@Test
	public void isASingleton()
	{
		AdventureTableDataGateway x = getGateway();
		AdventureTableDataGateway y = getGateway();
		assertSame(x, y);
		assertNotNull(x);
	}

	/**
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void retrieveAllAdventuresForQuest() throws DatabaseException
	{
		AdventureTableDataGateway gateway = getGateway();
		ArrayList<AdventureRecord> records = gateway.getAdventuresForQuest(1);
		assertEquals(3, records.size());
		AdventureRecord record = records.get(0);
		// the records could be in either order
		if (record.getAdventureID() == AdventuresForTest.QUEST1_ADVENTURE_1.getAdventureID())
		{
			assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getAdventureDescription(),
					record.getAdventureDescription());
			assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getQuestID(), record.getQuestID());
			assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getExperiencePointsGained(),
					record.getExperiencePointsGained());
			assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getCompletionType(), record.getCompletionType());
			assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getCompletionCriteria(), record.getCompletionCriteria());
			record = records.get(1);
			assertEquals(AdventuresForTest.QUEST1_ADVENTURE2.getAdventureDescription(),
					record.getAdventureDescription());
			assertEquals(AdventuresForTest.QUEST1_ADVENTURE2.getQuestID(), record.getQuestID());
			assertEquals(AdventuresForTest.QUEST1_ADVENTURE2.getExperiencePointsGained(),
					record.getExperiencePointsGained());
		}
		else
		{
			assertEquals(AdventuresForTest.QUEST1_ADVENTURE2.getAdventureID(), record.getAdventureID());
			assertEquals(AdventuresForTest.QUEST1_ADVENTURE2.getAdventureDescription(),
					record.getAdventureDescription());
			assertEquals(AdventuresForTest.QUEST1_ADVENTURE2.getQuestID(), record.getQuestID());
			assertEquals(AdventuresForTest.QUEST1_ADVENTURE2.getExperiencePointsGained(),
					record.getExperiencePointsGained());
			record = records.get(1);
			assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getAdventureDescription(),
					record.getAdventureDescription());
			assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getQuestID(), record.getQuestID());
			assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getExperiencePointsGained(),
					record.getExperiencePointsGained());

		}
	}

	/**
	 * We should be able to retrieve the specific information about one single
	 * adventure
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void canGetSingleAdventure() throws DatabaseException
	{
		AdventureTableDataGateway gateway = getGateway();
		AdventureRecord record = gateway.getAdventure(AdventuresForTest.QUEST1_ADVENTURE_1.getQuestID(),
				AdventuresForTest.QUEST1_ADVENTURE_1.getAdventureID());
		assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getAdventureDescription(), record.getAdventureDescription());
		assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getAdventureID(), record.getAdventureID());
		assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getExperiencePointsGained(),
				record.getExperiencePointsGained());
		assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getQuestID(), record.getQuestID());
	}

	/**
	 * We should be able to retrieve the specific information about one single
	 * adventure
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void nullForMissingAdventure() throws DatabaseException
	{
		AdventureTableDataGateway gateway = getGateway();
		AdventureRecord record = gateway.getAdventure(42, 16);
		assertNull(record);
	}

	/**
	 * We should be able to receive a list of all quests completed at a location
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void canGetCompleteByLocationAdventures() throws DatabaseException
	{
		GameLocationDTO location = (GameLocationDTO) (AdventuresForTest.QUEST2_ADVENTURE2.getCompletionCriteria());
		String mapName = location.getMapName();
		Position pos = location.getPosition();
		AdventureTableDataGateway gateway = getGateway();
		ArrayList<AdventuresForTest> adventure = new ArrayList<>();
		adventure.add(AdventuresForTest.QUEST2_ADVENTURE2);
		ArrayList<AdventureRecord> adventuresFound = gateway.findAdventuresCompletedForMapLocation(mapName, pos);
		assertEquals(adventure.get(0).getAdventureDescription(), adventuresFound.get(0).getAdventureDescription());
	}

	/**
	 * Given a quest ID, the TDG should be able to return the next appropriate adventure ID.
	 *
	 * @throws DatabaseException - if record with that quest ID not found 
	 */
	@Test
	public void canGetNextAdventureID() throws DatabaseException
	{
		final int questId = QuestsForTest.CHAT_TO_AN_NPC_QUEST.getQuestID();
		final ArrayList<AdventureRecord> adventures = getGateway().getAdventuresForQuest(questId);
		final int expected = adventures.size() + 1;

		assertEquals(expected, getGateway().getNextAdventureID(questId));
	}

}
