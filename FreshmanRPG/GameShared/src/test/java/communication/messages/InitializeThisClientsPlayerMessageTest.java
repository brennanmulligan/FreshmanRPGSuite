package communication.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import dataDTO.ClientPlayerAdventureStateDTO;
import dataDTO.ClientPlayerQuestStateDTO;
import dataDTO.FriendDTO;
import datasource.LevelRecord;
import datatypes.AdventureStateEnum;
import datatypes.QuestStateEnum;
import datatypes.FriendStatusEnum;
import datatypes.PlayersForTest;

/**
 * Tests the InitializeThisClientsPlayerMessage class
 *
 * @author Olivia, LaVonne
 *
 */
public class InitializeThisClientsPlayerMessageTest
{
	/**
	 * Tests that we can create InitializeThisClientsPlayerMessage and get the
	 * values of its fields
	 */
	@Test
	public void testInitialize()
	{
		ClientPlayerAdventureStateDTO adventureOne = new ClientPlayerAdventureStateDTO(1, "Test Adventure 1", 3,
				AdventureStateEnum.HIDDEN, false, true, "Dean", QuestStateEnum.AVAILABLE);
		ClientPlayerAdventureStateDTO adventureTwo = new ClientPlayerAdventureStateDTO(2, "Test Adventure 2", 3,
				AdventureStateEnum.HIDDEN, false, false, null, QuestStateEnum.AVAILABLE);
		ClientPlayerQuestStateDTO q = new ClientPlayerQuestStateDTO(1, "title", "Test Quest 1", QuestStateEnum.AVAILABLE, 42, 13, true,
				null);
		q.addAdventure(adventureOne);
		q.addAdventure(adventureTwo);
		ArrayList<ClientPlayerQuestStateDTO> list = new ArrayList<>();
		list.add(q);
		LevelRecord level = new LevelRecord("One", 15, 10, 7);

		ArrayList<FriendDTO> mockFriends = new ArrayList<>();
		FriendDTO friend1 = new FriendDTO(PlayersForTest.MERLIN.getPlayerID(), PlayersForTest.JOHN.getPlayerID(), FriendStatusEnum.ACCEPTED, PlayersForTest.MERLIN.getPlayerName(), PlayersForTest.JOHN.getPlayerName());
		FriendDTO friend2 = new FriendDTO(PlayersForTest.MERLIN.getPlayerID(), PlayersForTest.NICK.getPlayerID(), FriendStatusEnum.ACCEPTED, PlayersForTest.MERLIN.getPlayerName(), PlayersForTest.NICK.getPlayerName());
		mockFriends.add(friend1);
		mockFriends.add(friend2);

		InitializeThisClientsPlayerMessage message = new InitializeThisClientsPlayerMessage(list, mockFriends, 20, 50, level);

		assertEquals(20, message.getExperiencePts());
		assertEquals(50, message.getKnowledgePoints());
		assertEquals("One", message.getLevel().getDescription());
		assertEquals(1, message.getClientPlayerQuestList().size());
		ClientPlayerQuestStateDTO firstQuest = message.getClientPlayerQuestList().get(0);
		assertEquals(42, firstQuest.getExperiencePointsGained());
		assertEquals(13, firstQuest.getAdventuresToFulfillment());
		assertTrue(firstQuest.isNeedingNotification());
		ClientPlayerAdventureStateDTO firstAdventure = firstQuest.getAdventureList().get(0);
		assertTrue(firstAdventure.isRealLifeAdventure());
		assertEquals("Dean", firstAdventure.getWitnessTitle());
		ArrayList<FriendDTO> friends = message.getFriends();
		assertEquals(2, friends.get(0).getPlayerID());
		assertEquals(3, friends.get(1).getFriendID());
		assertEquals(FriendStatusEnum.ACCEPTED, friends.get(0).getStatus());

	}
}
