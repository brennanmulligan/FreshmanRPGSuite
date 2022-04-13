/**
 * 
 */
package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import datatypes.PlayersForTest;
import org.junit.Test;

import communication.messages.InitializeThisClientsPlayerMessage;
import dataDTO.ClientPlayerQuestStateDTO;
import dataDTO.FriendDTO;
import datasource.LevelRecord;
import datatypes.QuestStateEnum;

/**
 * @author nk3668
 *
 */
public class CommandOverwriteExperienceTest
{
	/**
	 * Basic initialization for this command
	 */
	@Test
	public void testInitialization()
	{
		int expPoints = 1000;
		LevelRecord report = new LevelRecord("Weak Kolbold", 100, 10, 7);
		ArrayList<ClientPlayerQuestStateDTO> list = new ArrayList<>();
		ArrayList<FriendDTO> friends = new ArrayList<>();
		InitializeThisClientsPlayerMessage msg = new InitializeThisClientsPlayerMessage(
				list, friends, expPoints, 50, report);
		CommandOverwriteExperience ow = new CommandOverwriteExperience(msg);
		assertEquals(expPoints, ow.getExperiencePoints());
		assertEquals(report, ow.getLevelRecord());
	}

	/**
	 * Test the execute of the command and make sure that this client player
	 * experience is overwritten
	 */
	@Test
	public void testExecutes()
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

		ArrayList<ClientPlayerQuestStateDTO> quests = new ArrayList<>();
		ArrayList<FriendDTO> friends = new ArrayList<>();
		ClientPlayerQuestStateDTO q = new ClientPlayerQuestStateDTO(42, "title",
				"silly", QuestStateEnum.AVAILABLE, 42, 4, false, null);
		quests.add(q);
		LevelRecord level = new LevelRecord("One", 15, 10, 7);
		int expectedExperience = 100;

		CommandOverwriteExperience x = new CommandOverwriteExperience(
				new InitializeThisClientsPlayerMessage(quests, friends, expectedExperience, 50, level));
		x.execute();

		ThisClientsPlayer player = ClientPlayerManager.getSingleton().getThisClientsPlayer();
		assertEquals(expectedExperience, player.getExperiencePoints());
		assertEquals(level, player.getLevelRecord());
	}
}
