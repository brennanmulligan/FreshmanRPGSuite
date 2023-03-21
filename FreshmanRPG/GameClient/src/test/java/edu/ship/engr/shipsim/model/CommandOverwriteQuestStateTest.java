package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.communication.messages.InitializeThisClientsPlayerMessage;
import edu.ship.engr.shipsim.dataDTO.ClientPlayerQuestStateDTO;
import edu.ship.engr.shipsim.dataDTO.FriendDTO;
import edu.ship.engr.shipsim.datasource.LevelRecord;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test the Command to overwrite this client player quest list
 *
 * @author Ryan
 */
@GameTest("GameClient")
public class CommandOverwriteQuestStateTest
{
    /**
     * Test the constructor of CommandOverwriteQuestState
     */
    @Test
    public void constructor()
    {
        ArrayList<ClientPlayerQuestStateDTO> expected = new ArrayList<>();
        ArrayList<FriendDTO> friends = new ArrayList<>();
        ClientPlayerQuestStateDTO q = new ClientPlayerQuestStateDTO(42, "title", "silly", QuestStateEnum.AVAILABLE, 42, 2, true, null, false);
        expected.add(q);
        LevelRecord level = new LevelRecord("One", 15, 10, 7);

        CommandOverwriteQuestState x = new CommandOverwriteQuestState(new InitializeThisClientsPlayerMessage(
                PlayersForTest.MERLIN.getPlayerID(), false, expected, friends, 20, 50, level));
        assertEquals(expected, x.getClientPlayerQuestList());
    }

    /**
     * Test the execute of the command and make sure that this client
     * player quest list is overwritten
     */
    @Test
    public void testExecutes()
    {
        ClientPlayerManager pm = ClientPlayerManager.getSingleton();
        PlayersForTest john = PlayersForTest.JOHN;
        pm.initiateLogin(john.getPlayerName(), john.getPlayerPassword());

        @SuppressWarnings("unused")
        ThisClientsPlayer cp = null;

        try
        {
            cp = pm.finishLogin(john.getPlayerID());
        }
        catch (AlreadyBoundException | NotBoundException e)
        {
            e.printStackTrace();
            fail("Could not create this client's player from login");
        }

        ArrayList<ClientPlayerQuestStateDTO> expected = new ArrayList<>();
        ArrayList<FriendDTO> friends = new ArrayList<>();
        ClientPlayerQuestStateDTO q = new ClientPlayerQuestStateDTO(42, "title", "silly", QuestStateEnum.AVAILABLE, 22, 13, false, null, false);
        expected.add(q);
        LevelRecord level = new LevelRecord("One", 15, 10, 7);

        CommandOverwriteQuestState x = new CommandOverwriteQuestState(new InitializeThisClientsPlayerMessage(
                PlayersForTest.JOHN.getPlayerID(), false, expected, friends, 20, 50, level));
        x.execute();

        ThisClientsPlayer player = ClientPlayerManager.getSingleton().getThisClientsPlayer();
        assertEquals(expected, player.getQuests());
    }

}
