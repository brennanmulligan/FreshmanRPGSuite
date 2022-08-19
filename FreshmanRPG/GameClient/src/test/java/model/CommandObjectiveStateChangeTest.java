package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import org.junit.Test;

import communication.messages.ObjectiveStateChangeMessage;
import dataDTO.ClientPlayerObjectiveStateDTO;
import dataDTO.ClientPlayerQuestStateDTO;
import datatypes.ObjectiveStateEnum;
import datatypes.Crew;
import datatypes.Major;
import datatypes.Position;
import datatypes.QuestStateEnum;

/**
 * @author sl6469
 *
 */

public class CommandObjectiveStateChangeTest
{

	/**
	 * test the setup of the constructor
	 */
	@Test
	public void testInitialization()
	{
		CommandObjectiveStateChange x = new CommandObjectiveStateChange(
				new ObjectiveStateChangeMessage(1, 2, 5, "Silly Objective",
						ObjectiveStateEnum.COMPLETED, true, "Lex Luther"));
		assertEquals(2, x.getQuestID());
		assertEquals(5, x.getObjectiveID());
		assertEquals("Silly Objective", x.getObjectiveDescription());
		assertEquals(ObjectiveStateEnum.COMPLETED, x.getObjectiveState());
		assertTrue(x.isRealLifeObjective());
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
	public void testChangingObjectives() throws AlreadyBoundException, NotBoundException
	{

		int playerID = 1;
		int questID = 1;
		int objectiveID = 1;

		ClientPlayerQuestStateDTO q = new ClientPlayerQuestStateDTO(questID, "questtitle", "silly quest",
				QuestStateEnum.TRIGGERED, 3, 0, false, null);

		ClientPlayerObjectiveStateDTO a1 = new ClientPlayerObjectiveStateDTO(objectiveID, "objective 1",
				3, ObjectiveStateEnum.TRIGGERED, false, true, "Comrade", QuestStateEnum.AVAILABLE);
		q.getObjectiveList().add(a1);

		Position pos = new Position(1, 2);
		ClientPlayerManager pm = ClientPlayerManager.getSingleton();
		pm.initializePlayer(playerID, "Player 1", pm.getThisClientsPlayer().getVanities(), pos, Crew.FORTY_PERCENT, Major.COMPUTER_ENGINEERING, 1);

		pm.initiateLogin("john", "pw");
		pm.finishLogin(playerID);

		ClientPlayerManager.getSingleton().getThisClientsPlayer().addQuest(q);

		CommandObjectiveStateChange x = new CommandObjectiveStateChange(
				new ObjectiveStateChangeMessage(playerID, questID, objectiveID,
						"objective 1", ObjectiveStateEnum.COMPLETED, true, "Comrade"));
		x.execute();

		for (ClientPlayerQuestStateDTO quest : ClientPlayerManager.getSingleton()
				.getThisClientsPlayer().getQuests())
		{
			if (quest.getQuestID() == questID)
			{
				for (ClientPlayerObjectiveStateDTO objective : quest.getObjectiveList())
				{
					if (objective.getObjectiveID() == objectiveID)
					{
						assertEquals(ObjectiveStateEnum.COMPLETED,
								objective.getObjectiveState());
					}
				}
			}
		}
	}

}
