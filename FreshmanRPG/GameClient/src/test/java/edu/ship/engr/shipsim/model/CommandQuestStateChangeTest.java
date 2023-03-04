package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.communication.messages.QuestStateChangeMessage;
import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Ryan
 */
@GameTest("GameClient")
public class CommandQuestStateChangeTest
{

    /**
     * Test the constructor of CommandOverwriteQuestState
     */
    @Test
    public void testConstructor()
    {
        CommandQuestStateChange x = new CommandQuestStateChange(
                new QuestStateChangeMessage(1, false, 2, "title", "Silly Quest",
                        QuestStateEnum.COMPLETED));
        assertEquals(2, x.getQuestID());
        assertEquals("title", x.getQuestTitle());
        assertEquals("Silly Quest", x.getQuestDescription());
        assertEquals(QuestStateEnum.COMPLETED, x.getQuestState());
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
//	@Test
//	public void testChangingQuest() throws AlreadyBoundException, NotBoundException
//	{
//		int playerID = 1;
//		int questID = 1;
//		ClientPlayerQuestStateDTO q = new ClientPlayerQuestStateDTO(questID, "title",
//				"silly quest", QuestStateEnum.TRIGGERED, 3, 0, true, null);
//
//		Position pos = new Position(1, 2);
//		ClientPlayerManager pm = ClientPlayerManager.getSingleton();
//		pm.initializePlayer(playerID, "Player 1", "body_type", "hat_type", pos, Crew.NULL_POINTER, Major.COMPUTER_ENGINEERING, 1);
//
//		pm.initiateLogin("john", "pw");
//		pm.finishLogin(playerID);
//
//		ClientPlayerManager.getSingleton().getThisClientsPlayer().addQuest(q);
//
//		CommandQuestStateChange x = new CommandQuestStateChange(
//				new QuestStateChangeMessage(playerID, questID, "title", "silly quest",
//						QuestStateEnum.COMPLETED));
//		x.execute();
//
//		for (ClientPlayerQuestStateDTO quest : ClientPlayerManager.getSingleton()
//				.getThisClientsPlayer().getQuests())
//		{
//			if (quest.getQuestID() == questID)
//			{
//				assertEquals(QuestStateEnum.COMPLETED, quest.getQuestState());
//			}
//		}
//	}

}
