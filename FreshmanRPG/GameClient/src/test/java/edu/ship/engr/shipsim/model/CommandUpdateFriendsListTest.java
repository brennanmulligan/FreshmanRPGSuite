package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.communication.messages.InitializeThisClientsPlayerMessage;
import edu.ship.engr.shipsim.dataDTO.ClientPlayerQuestStateDTO;
import edu.ship.engr.shipsim.dataDTO.FriendDTO;
import edu.ship.engr.shipsim.datasource.LevelRecord;
import edu.ship.engr.shipsim.datatypes.FriendStatusEnum;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.nio.channels.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Kevin Marek, Zack Semanco
 */
@GameTest("GameClient")
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
        friends.add(new FriendDTO(1, 2, FriendStatusEnum.ACCEPTED, "John", "Merlin"));

        InitializeThisClientsPlayerMessage msg = new InitializeThisClientsPlayerMessage(
                PlayersForTest.JOHN.getPlayerID(), false, list, friends, 0, 100, null);
        CommandUpdateFriendsList friendCmd = new CommandUpdateFriendsList(msg);
        assertEquals(1, friendCmd.getFriendList().get(0).getPlayerID());

        friendCmd = new CommandUpdateFriendsList(friends);
        assertEquals(1, friendCmd.getFriendList().get(0).getPlayerID());
    }

    /**
     * Tests the execute of the command and makes sure that the clients friend list is set
     *
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
        friends.add(new FriendDTO(1, 2, FriendStatusEnum.ACCEPTED, "John", "Merlin"));

        ArrayList<ClientPlayerQuestStateDTO> quests = new ArrayList<>();
        ClientPlayerQuestStateDTO q = new ClientPlayerQuestStateDTO(42, "title",
                "silly", QuestStateEnum.AVAILABLE, 42, 4, false, null);
        quests.add(q);
        LevelRecord level = new LevelRecord("One", 15, 0, 0);
        int doubloons = 100;

        CommandUpdateFriendsList x = new CommandUpdateFriendsList(
                new InitializeThisClientsPlayerMessage(PlayersForTest.JOHN.getPlayerID(), false, quests, friends, 0, doubloons, level));
        x.execute();

        ThisClientsPlayer player = ClientPlayerManager.getSingleton().getThisClientsPlayer();
        assertEquals(1, player.getFriendList().get(0).getPlayerID());

    }

}
