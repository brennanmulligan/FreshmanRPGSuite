package edu.ship.engr.shipsim.model.terminal;

import edu.ship.engr.shipsim.datatypes.FriendStatusEnum;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Test Class for CommandTerminalTextFriend
 *
 * @author Zachary Semanco, Christian C.
 */
@GameTest("GameServer")
public class CommandTerminalTextFriendTest
{

    private final String terminalIdentifier = "friend";

    /**
     * Tests that we get the correct description of the command
     */
    @Test
    public void testGetDescription()
    {
        String actual = TerminalManager.getSingleton().getTerminalCommandObject(terminalIdentifier).getDescription();
        String expected = "Allows the user to add/accept/list friends.";
        assertEquals(expected, actual);
    }

    /**
     * Tests that we get the correct identifier for the command
     */
    @Test
    public void testGetTerminalIdentifier()
    {
        String actual = TerminalManager.getSingleton().getTerminalCommandObject(terminalIdentifier).getTerminalIdentifier();
        assertEquals(terminalIdentifier, actual);
    }

    /**
     * Tests the functionality of the list command
     */
    @Test
    public void testListBehavior()
    {
        String expected = "> Friend Name - Status\n" +
                "> Merlin - PENDING\n" +
                "> Nick - ACCEPTED\n" +
                "> Josh - REQUESTED\n" +
                "> End of List \n";
        FriendListBehavior cmd = new FriendListBehavior();
        int playerID = PlayersForTest.JOHN.getPlayerID();
        assertEquals(expected, cmd.execute(playerID, new String[]{}));
    }

    /**
     * Tests if the user enters an invalid argument that is not in the HashMap
     */
    @Test
    public void testInvalidBehavior()
    {
        CommandTerminalTextFriend cttf = new CommandTerminalTextFriend();
        int playerID = 1;
        String actual = cttf.execute(playerID, new String[]{"invalidARG"});
        String errorMsg = "ERROR: friend argument (invalidARG) does not exist";


        assertEquals(errorMsg, actual);
    }

    /**
     * Test AddBehavior for a single friend
     */
    @Test
    public void testAddBehavior()
    {
        String friendName = PlayersForTest.ANDY.getPlayerName();
        int playerID = PlayersForTest.MATT.getPlayerID();
        FriendAddBehavior cmd = new FriendAddBehavior();
        String expected = "> Friend Name - Status\n" +
                "> " + friendName + " " + FriendStatusEnum.PENDING + "\n";

        assertEquals(expected, cmd.execute(playerID, new String[]{friendName}));
    }

    /**
     * Test AddBehavior for multiple friends
     */
    @Test
    public void testAddMultipleFriends()
    {
        int playerID = PlayersForTest.DATBOI.getPlayerID();
        String[] friendsToAdd = {PlayersForTest.MERLIN.getPlayerName(), PlayersForTest.JOHN.getPlayerName()};
        FriendAddBehavior cmd = new FriendAddBehavior();
        String expected = "> Friend Name - Status\n" +
                "> " + friendsToAdd[0] + " " + FriendStatusEnum.PENDING + "\n" +
                "> " + friendsToAdd[1] + " " + FriendStatusEnum.PENDING + "\n";

        assertEquals(expected, cmd.execute(playerID, friendsToAdd));
    }

    /**
     * Test AddBehavior for an invalid player
     */
    @Test
    public void testFriendNotAdded()
    {
        String friendName = "asdfgfsdfgsdf";
        int playerID = PlayersForTest.MATT.getPlayerID();
        FriendAddBehavior cmd = new FriendAddBehavior();
        String expected = "> Friend Name - Status\n" +
                "> " + friendName + " Error: Couldn't send request\n";

        assertEquals(expected, cmd.execute(playerID, new String[]{friendName}));
    }

    /**
     * Test AcceptBehavior for a single friend
     */
    @Test
    public void testFriendAccept()
    {
        String friendName = PlayersForTest.JOHN.getPlayerName();
        int playerID = PlayersForTest.JOSH.getPlayerID();
        FriendAcceptBehavior cmd = new FriendAcceptBehavior();
        String expected = "> Friend Name - Status\n" +
                "> " + friendName + " " + FriendStatusEnum.ACCEPTED + "\n";

        assertEquals(expected, cmd.execute(playerID, new String[]{friendName}));
    }

    /**
     * Test AcceptBehavior for multiple friends
     */
    @Test
    public void testAcceptMultipleFriends()
    {
        int playerID = PlayersForTest.NICK.getPlayerID();
        String[] friendsToAccept = {PlayersForTest.MERLIN.getPlayerName(), PlayersForTest.JOHN.getPlayerName()};
        FriendAcceptBehavior cmd = new FriendAcceptBehavior();
        String expected = "> Friend Name - Status\n" +
                "> " + friendsToAccept[0] + " " + FriendStatusEnum.ACCEPTED + "\n" +
                "> " + friendsToAccept[1] + " " + FriendStatusEnum.ACCEPTED + "\n";

        assertEquals(expected, cmd.execute(playerID, friendsToAccept));
    }

    /**
     * Test AcceptBehavior for an invalid player
     */
    @Test
    public void testFriendNotAccepted()
    {
        String friendName = "asdfgfsdfgsdf";
        int playerID = PlayersForTest.MATT.getPlayerID();
        FriendAcceptBehavior cmd = new FriendAcceptBehavior();
        String expected = "> Friend Name - Status\n" +
                "> " + friendName + " Error: Couldn't accept request\n";

        assertEquals(expected, cmd.execute(playerID, new String[]{friendName}));
    }

}