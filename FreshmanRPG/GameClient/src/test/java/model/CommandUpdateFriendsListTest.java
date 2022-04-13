package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.channels.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import datatypes.FriendStatusEnum;
import datatypes.PlayersForTest;
import org.junit.Test;

import communication.messages.InitializeThisClientsPlayerMessage;
import dataDTO.ClientPlayerQuestStateDTO;
import dataDTO.FriendDTO;
import datasource.LevelRecord;
import datatypes.QuestStateEnum;

/**
 * @author Kevin Marek, Zack Semanco
 *
 */
public class CommandUpdateFriendsListTest
{

	/**
	 * Test both constructors
	 */
	@Test
	public void testInitialization()
	{
		ArrayList<FriendDTO> friends = new ArrayList<>();
		ArrayList<ClientPlayerQuestStateDTO> list = new ArrayList<>();
		friends.add(new FriendDTO(1,2, FriendStatusEnum.ACCEPTED, "John", "Merlin"));
		
		InitializeThisClientsPlayerMessage msg = new InitializeThisClientsPlayerMessage(list, friends, 0, 100, null);
		CommandUpdateFriendsList friendCmd = new CommandUpdateFriendsList(msg);
		assertEquals(1, friendCmd.getFriendList().get(0).getPlayerID());
		
		friendCmd = new CommandUpdateFriendsList(friends);
		assertEquals(1, friendCmd.getFriendList().get(0).getPlayerID());
	}
	
	/**
	 * Tests the execute of the command and makes sure that the clients friend list is set
	 * @throws AlreadyBoundException shouldn't
	 */
	@Test
	public void testExecutes() throws java.rmi.AlreadyBoundException
	{
		ClientPlayerManager pm = ClientPlayerManager.getSingleton();
		PlayersForTest john = PlayersForTest.JOHN;
		pm.initiateLogin(john.getPlayerName(), john.getPlayerPassword());
		
		try
		{
			pm.finishLogin(john.getPlayerID());
		}
		catch (AlreadyBoundException | NotBoundException e)
		{
			e.printStackTrace();
			fail("Could not create this client's player from login");
		}
		ArrayList<FriendDTO> friends = new ArrayList<>();
		friends.add(new FriendDTO(1,2,FriendStatusEnum.ACCEPTED, "John", "Merlin"));
		
		ArrayList<ClientPlayerQuestStateDTO> quests = new ArrayList<>();
		ClientPlayerQuestStateDTO q = new ClientPlayerQuestStateDTO(42, "title",
				"silly", QuestStateEnum.AVAILABLE, 42, 4, false, null);
		quests.add(q);
		LevelRecord level = new LevelRecord("One", 15, 0, 0);
		int knowledgePoints = 100;

		CommandUpdateFriendsList x = new CommandUpdateFriendsList(
				new InitializeThisClientsPlayerMessage(quests, friends, 0, knowledgePoints, level));
		x.execute();

		ThisClientsPlayer player = ClientPlayerManager.getSingleton().getThisClientsPlayer();
		assertEquals(1, player.getFriendList().get(0).getPlayerID());
		
	}
	
}
