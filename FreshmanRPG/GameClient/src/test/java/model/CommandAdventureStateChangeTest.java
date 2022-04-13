package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import org.junit.Test;

import communication.messages.AdventureStateChangeMessage;
import dataDTO.ClientPlayerAdventureStateDTO;
import dataDTO.ClientPlayerQuestStateDTO;
import datatypes.AdventureStateEnum;
import datatypes.Crew;
import datatypes.Major;
import datatypes.Position;
import datatypes.QuestStateEnum;

/**
 * @author sl6469
 *
 */

public class CommandAdventureStateChangeTest
{

	/**
	 * test the setup of the constructor
	 */
	@Test
	public void testInitialization()
	{
		CommandAdventureStateChange x = new CommandAdventureStateChange(
				new AdventureStateChangeMessage(1, 2, 5, "Silly Adventure",
						AdventureStateEnum.COMPLETED, true, "Lex Luther"));
		assertEquals(2, x.getQuestID());
		assertEquals(5, x.getAdventureID());
		assertEquals("Silly Adventure", x.getAdventureDescription());
		assertEquals(AdventureStateEnum.COMPLETED, x.getAdventureState());
		assertTrue(x.isRealLifeAdventure());
		assertEquals("Lex Luther", x.getWitnessTitle());
	}

	/**
	 * Test that when we execute the CommandQuestStateChange ThisClientsPlayer
	 * ClientPlayerQuest's state changes
	 * 
	 * @throws AlreadyBoundException
	 *             shouldn't
	 * @throws NotBoundException
	 *             shouldn't
	 */
	@Test
	public void testChangingAdventure() throws AlreadyBoundException, NotBoundException
	{

		int playerID = 1;
		int questID = 1;
		int adventureID = 1;

		ClientPlayerQuestStateDTO q = new ClientPlayerQuestStateDTO(questID, "questtitle", "silly quest",
				QuestStateEnum.TRIGGERED, 3, 0, false, null);

		ClientPlayerAdventureStateDTO a1 = new ClientPlayerAdventureStateDTO(adventureID, "adventure 1",
				3, AdventureStateEnum.TRIGGERED, false, true, "Comrade", QuestStateEnum.AVAILABLE);
		q.getAdventureList().add(a1);

		Position pos = new Position(1, 2);
		ClientPlayerManager pm = ClientPlayerManager.getSingleton();
		pm.initializePlayer(playerID, "Player 1", "Player 1 Type", pos, Crew.NULL_POINTER, Major.COMPUTER_ENGINEERING, 1);

		pm.initiateLogin("john", "pw");
		pm.finishLogin(playerID);

		ClientPlayerManager.getSingleton().getThisClientsPlayer().addQuest(q);

		CommandAdventureStateChange x = new CommandAdventureStateChange(
				new AdventureStateChangeMessage(playerID, questID, adventureID,
						"adventure 1", AdventureStateEnum.COMPLETED, true, "Comrade"));
		x.execute();

		for (ClientPlayerQuestStateDTO quest : ClientPlayerManager.getSingleton()
				.getThisClientsPlayer().getQuests())
		{
			if (quest.getQuestID() == questID)
			{
				for (ClientPlayerAdventureStateDTO adventure : quest.getAdventureList())
				{
					if (adventure.getAdventureID() == adventureID)
					{
						assertEquals(AdventureStateEnum.COMPLETED,
								adventure.getAdventureState());
					}
				}
			}
		}
	}

}
