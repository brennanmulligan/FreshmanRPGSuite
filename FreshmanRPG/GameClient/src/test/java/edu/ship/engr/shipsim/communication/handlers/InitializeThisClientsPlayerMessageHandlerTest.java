package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.InitializeThisClientsPlayerMessage;
import edu.ship.engr.shipsim.dataDTO.ClientPlayerObjectiveStateDTO;
import edu.ship.engr.shipsim.dataDTO.ClientPlayerQuestStateDTO;
import edu.ship.engr.shipsim.dataDTO.FriendDTO;
import edu.ship.engr.shipsim.datasource.LevelRecord;
import edu.ship.engr.shipsim.datatypes.FriendStatusEnum;
import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import edu.ship.engr.shipsim.model.*;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetClientModelFacade;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Frank Schmidt
 */
@GameTest("GameClient")
@ResetClientModelFacade(shouldClearQueue = true)
public class InitializeThisClientsPlayerMessageHandlerTest
{
    /**
     * Test the type of Message that we expect
     */
    @Test
    public void typeWeHandle()
    {
        InitializeThisClientsPlayerMessageHandler h = new InitializeThisClientsPlayerMessageHandler();
        assertEquals(InitializeThisClientsPlayerMessage.class, h.getMessageTypeWeHandle());
    }

    /**
     * We should add a command to the ModelFacade command queue
     *
     * @throws InterruptedException  shouldn't
     */
    @Test
    public void test() throws InterruptedException
    {
        ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.JOHN);
        InitializeThisClientsPlayerMessageHandler handler = new InitializeThisClientsPlayerMessageHandler();
        ArrayList<ClientPlayerQuestStateDTO> qList = new ArrayList<>();
        ClientPlayerQuestStateDTO q = new ClientPlayerQuestStateDTO(3, "questtitle", "stupid quest",
                QuestStateEnum.TRIGGERED, 42, 133, true, null);
        q.addObjective(new ClientPlayerObjectiveStateDTO(3, "stupid objective", 5,
                ObjectiveStateEnum.TRIGGERED, false, true, "My big toe", QuestStateEnum.AVAILABLE));
        qList.add(q);

        ArrayList<FriendDTO> mockFriends = new ArrayList<>();
        FriendDTO friend1 = new FriendDTO(PlayersForTest.MERLIN.getPlayerID(), PlayersForTest.JOHN.getPlayerID(), FriendStatusEnum.ACCEPTED, PlayersForTest.MERLIN.getPlayerName(), PlayersForTest.JOHN.getPlayerName());
        FriendDTO friend2 = new FriendDTO(PlayersForTest.MERLIN.getPlayerID(), PlayersForTest.NICK.getPlayerID(), FriendStatusEnum.ACCEPTED, PlayersForTest.MERLIN.getPlayerName(), PlayersForTest.NICK.getPlayerName());
        mockFriends.add(friend1);
        mockFriends.add(friend2);

        LevelRecord level = new LevelRecord("One", 45, 10, 7);
        InitializeThisClientsPlayerMessage msg = new InitializeThisClientsPlayerMessage(
                qList, mockFriends, 20, 50, level);
        handler.process(msg);

        ClientModelFacade facade = ClientModelFacade.getSingleton();
        int commandQueueLength = facade.getCommandQueueLength();
        assertEquals(4, facade.getCommandQueueLength());
        for (int i = 0; i < commandQueueLength; i++)
        {
            Command cmd = facade.getNextCommand();
            if (cmd.getClass() == CommandOverwriteQuestState.class)
            {
                CommandOverwriteQuestState thisCmd = (CommandOverwriteQuestState) (cmd);
                assertEquals(qList, thisCmd.getClientPlayerQuestList());
            }
            else if (cmd.getClass() == CommandOverwriteExperience.class)
            {
                CommandOverwriteExperience thisCmd = (CommandOverwriteExperience) (cmd);
                assertEquals(20, thisCmd.getExperiencePoints());
                assertEquals(level, thisCmd.getLevelRecord());
            }
            else if (cmd.getClass() == CommandDoubloonsChanged.class)
            {
                CommandDoubloonsChanged thisCmd = (CommandDoubloonsChanged) (cmd);
                assertEquals(50, thisCmd.getDoubloons());
            }
            else if (cmd.getClass() == CommandUpdateFriendsList.class)
            {
                CommandUpdateFriendsList thisCmd = (CommandUpdateFriendsList) (cmd);
                assertEquals(2, thisCmd.getFriendList().size());
            }
            else
            {
                fail("Unexpected command in facade queue " + cmd);
            }
        }
    }

}
