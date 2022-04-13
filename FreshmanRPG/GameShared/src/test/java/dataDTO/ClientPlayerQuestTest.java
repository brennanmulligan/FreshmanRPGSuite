package dataDTO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import datatypes.AdventureStateEnum;
import datatypes.QuestStateEnum;

/**
 * Test the
 *
 * @author nk3668
 *
 */
public class ClientPlayerQuestTest
{

	/**
	 * Tests that a clients player will contain state, id, and a description.
	 */
	@Test
	public void testClientPlayerQuestInitialization()
	{
		Date expireDate = new Date();
		ClientPlayerQuestStateDTO q = new ClientPlayerQuestStateDTO(1, "title", "Test Quest 1", QuestStateEnum.AVAILABLE, 42, 133, true,
				expireDate);
		assertEquals(1, q.getQuestID());
		assertEquals("title", q.getQuestTitle());
		assertEquals("Test Quest 1", q.getQuestDescription());
		assertEquals(QuestStateEnum.AVAILABLE, q.getQuestState());
		assertEquals(42, q.getExperiencePointsGained());
		assertEquals(133, q.getAdventuresToFulfillment());
		assertEquals(expireDate, q.getExpireDate());
	}

	/**
	 * Create a new ClientPlayerQuest with two ClientPlayerAdventures
	 *
	 * @return the ClientPlayerQuest
	 */
	public static ClientPlayerQuestStateDTO createOneQuestWithTwoAdventures()
	{
		ClientPlayerAdventureStateDTO adventureOne = new ClientPlayerAdventureStateDTO(1, "Test Adventure 1", 4,
				AdventureStateEnum.HIDDEN, false, true, "Henry", QuestStateEnum.AVAILABLE);
		ClientPlayerAdventureStateDTO adventureTwo = new ClientPlayerAdventureStateDTO(2, "Test Adventure 2", 4,
				AdventureStateEnum.HIDDEN, false, false, null, QuestStateEnum.AVAILABLE);
		ClientPlayerQuestStateDTO q = new ClientPlayerQuestStateDTO(1, "title", "Test Quest 1", QuestStateEnum.AVAILABLE, 1, 2, true,
				null);
		q.addAdventure(adventureOne);
		q.addAdventure(adventureTwo);
		assertEquals(2, q.getAdventureList().size());
		ClientPlayerAdventureStateDTO a = q.getAdventureList().get(0);
		assertTrue(a.isRealLifeAdventure());
		assertEquals("Henry", a.getWitnessTitle());
		a = q.getAdventureList().get(1);
		assertFalse(a.isRealLifeAdventure());
		assertNull(a.getWitnessTitle());
		return q;
	}

	/**
	 * Create a new ClientPlayerQuest with two ClientPlayerAdventures
	 *
	 * @return the ClientPlayerQuest
	 */
	public static ClientPlayerQuestStateDTO createOneQuestWithTwoAdventuresNeedingNotification()
	{
		ClientPlayerAdventureStateDTO adventureOne = new ClientPlayerAdventureStateDTO(1, "Test Adventure 1", 4,
				AdventureStateEnum.COMPLETED, true, true, "CEO:", QuestStateEnum.AVAILABLE);
		ClientPlayerAdventureStateDTO adventureTwo = new ClientPlayerAdventureStateDTO(2, "Test Adventure 2", 2,
				AdventureStateEnum.COMPLETED, true, false, null, QuestStateEnum.AVAILABLE);
		ClientPlayerQuestStateDTO q = new ClientPlayerQuestStateDTO(1, "title", "Test Quest 1", QuestStateEnum.COMPLETED, 1, 2, true,
				null);
		q.addAdventure(adventureOne);
		q.addAdventure(adventureTwo);
		assertEquals(2, q.getAdventureList().size());
		return q;
	}

	/**
	 * Tests the ability to add an adventure to the quest
	 */
	@Test
	public void testAddingAdventures()
	{
		ClientPlayerAdventureStateDTO adventureOne = new ClientPlayerAdventureStateDTO(1, "Test Adventure 1", 10,
				AdventureStateEnum.HIDDEN, false, false, null, QuestStateEnum.AVAILABLE);
		assertEquals(1, adventureOne.getAdventureID());
		ClientPlayerAdventureStateDTO adventureTwo = new ClientPlayerAdventureStateDTO(2, "Test Adventure 2", 7,
				AdventureStateEnum.HIDDEN, false, true, "Liz", QuestStateEnum.AVAILABLE);
		assertEquals(2, adventureTwo.getAdventureID());
		ClientPlayerQuestStateDTO q = new ClientPlayerQuestStateDTO(1, "title", "Test Quest 1", QuestStateEnum.AVAILABLE, 1, 2, true,
				null);
		q.addAdventure(adventureOne);
		q.addAdventure(adventureTwo);
		assertEquals(2, q.getAdventureList().size());
		assertEquals(1, q.getAdventureList().get(0).getAdventureID());
		assertEquals(2, q.getAdventureList().get(1).getAdventureID());
	}
}
