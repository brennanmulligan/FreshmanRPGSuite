package edu.ship.engr.shipsim.sequencetests;

import edu.ship.engr.shipsim.communication.messages.DisplayTextMessage;
import edu.ship.engr.shipsim.communication.messages.KeyInputMessage;
import edu.ship.engr.shipsim.datatypes.InteractableItemsForTest;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.*;

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
                    new KeyInputMessage("e", false), true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                            new DisplayTextMessage(PlayersForTest.JAWN.getPlayerID(), false,
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
                0, sequence));
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
        playerFromID.setPosition(InteractableItemsForTest.BOOKSHELF.getPosition());
        InteractObjectManager.getSingleton();
    }

}
