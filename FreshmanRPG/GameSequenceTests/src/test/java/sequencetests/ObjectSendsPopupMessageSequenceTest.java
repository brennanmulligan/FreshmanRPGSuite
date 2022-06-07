package sequencetests;

import communication.messages.DisplayTextMessage;
import communication.messages.KeyInputMessage;
import datatypes.InteractableItemsForTest;
import datatypes.PlayersForTest;
import model.*;

/**
 * Tests that when a player is near an object of the type that
 * displays text, and the player presses 'e', the server sends back
 * a message to display appropriate text.
 *
 * @author Elisabeth Ostrow, Jacob Knight
 */
public class ObjectSendsPopupMessageSequenceTest extends SequenceTest
{

    /**
     * the sequence of messages
     */
    @SuppressWarnings("FieldCanBeLocal")
    private final MessageFlow[] sequence =
            {new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
                    new KeyInputMessage("e"), true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                            new DisplayTextMessage(PlayersForTest.JAWN.getPlayerID(),
                                    InteractableItemsForTest.BOOKSHELF.getMessage()),
                            true)};

    /**
     * runs through message flow
     */
    public ObjectSendsPopupMessageSequenceTest()
    {
        serverList.add(ServerType.THIS_PLAYER_CLIENT);
        serverList.add(ServerType.AREA_SERVER);

        interactions.add(new Interaction(new CommandKeyInputSent("e"),
                PlayersForTest.JAWN.getPlayerID(), ServerType.THIS_PLAYER_CLIENT,
                sequence));
    }

    /**
     * resets singletons
     */
    @Override
    public void resetNecessarySingletons()
    {
        PlayerManager.resetSingleton();
        InteractObjectManager.resetSingleton();
    }

    /**
     * various setup
     * puts player next to a valid interactable object
     */
    @Override
    public void setUpMachines()
    {
        MapManager.getSingleton().changeToNewFile(PlayersForTest.JAWN.getMapName(),
                "JAWNMAP");
        ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.JAWN);
        // set up players through player manager
        PlayerManager pm = PlayerManager.getSingleton();
        pm.addPlayer(PlayersForTest.JAWN.getPlayerID());
        // set player position
        Player playerFromID = pm.getPlayerFromID(PlayersForTest.JAWN.getPlayerID());
        playerFromID.setPlayerPosition(InteractableItemsForTest.BOOKSHELF.getPosition());
        InteractObjectManager.getSingleton();
    }

}
