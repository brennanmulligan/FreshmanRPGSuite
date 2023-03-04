package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.communication.messages.InitializeThisClientsPlayerMessage;
import edu.ship.engr.shipsim.dataDTO.ClientPlayerQuestStateDTO;
import edu.ship.engr.shipsim.dataDTO.FriendDTO;
import edu.ship.engr.shipsim.datasource.LevelRecord;
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
 * @author Matthew Croft
 * @author Emily Maust
 */
@GameTest("GameClient")
public class CommandDoubloonsChangedTest
{

    /**
     * Basic initialization for this command
     */
    @Test
    public void testInitialization()
    {
        int doubloons = 100;
        ArrayList<ClientPlayerQuestStateDTO> list = new ArrayList<>();
        ArrayList<FriendDTO> friends = new ArrayList<>();
        InitializeThisClientsPlayerMessage msg = new InitializeThisClientsPlayerMessage(
                PlayersForTest.MERLIN.getPlayerID(), false, list, friends, 0, doubloons, null);
        CommandDoubloonsChanged ow = new CommandDoubloonsChanged(msg);
        assertEquals(doubloons, ow.getDoubloons());
    }

    /**
     * Test the execute of the command and make sure that this client player
     * experience is overwritten
     *
     * @throws java.rmi.AlreadyBoundException h
     */
    @Test
    public void testExecutes() throws java.rmi.AlreadyBoundException
    {
        ClientPlayerManager pm = ClientPlayerManager.getSingleton();
        PlayersForTest john = PlayersForTest.JOHN;
        pm.initiateLogin(john.getPlayerName(), john.getPlayerPassword());
        ArrayList<FriendDTO> friends = new ArrayList<>();

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
        ClientPlayerQuestStateDTO q = new ClientPlayerQuestStateDTO(42, "title",
                "silly", QuestStateEnum.AVAILABLE, 42, 4, false, null);
        quests.add(q);
        LevelRecord level = new LevelRecord("One", 15, 0, 0);
        int doubloons = 100;

        CommandDoubloonsChanged x = new CommandDoubloonsChanged(
                new InitializeThisClientsPlayerMessage(PlayersForTest.JOHN.getPlayerID(), false, quests, friends, 0, doubloons, level));
        x.execute();

        ThisClientsPlayer player = ClientPlayerManager.getSingleton().getThisClientsPlayer();
        assertEquals(doubloons, player.getDoubloons());
    }
}
