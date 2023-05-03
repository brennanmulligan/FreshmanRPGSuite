package edu.ship.engr.shipsim.sequencetests;

import edu.ship.engr.shipsim.communication.messages.InteractionDeniedMessage;
import edu.ship.engr.shipsim.communication.messages.KeyInputMessage;
import edu.ship.engr.shipsim.datatypes.InteractableItemsForTest;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.*;

/**
 * Tests that a player cannot receive another buff if it already has one
 *
 * @author Jake Moore
 */
public class NoMultipleBuffSequenceTest extends SequenceTest
{
    @SuppressWarnings("FieldCanBeLocal")
    private final MessageFlow[] sequence =
            {new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
                    new KeyInputMessage("e", false), true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                            new InteractionDeniedMessage(
                                    PlayersForTest.JEFF.getPlayerID(), false), true)};

    /**
     * Runs through the message flow
     */
    public NoMultipleBuffSequenceTest()
    {
        this.serverList.add(ServerType.THIS_PLAYER_CLIENT);
        this.serverList.add(ServerType.AREA_SERVER);
        interactions.add(new Interaction(new CommandKeyInputSent("e"),
                PlayersForTest.JEFF.getPlayerID(), ServerType.THIS_PLAYER_CLIENT,
                0, sequence));
    }

    /**
     * Clear used data
     */
    @Override
    public void resetNecessarySingletons()
    {
        PlayerManager.resetSingleton();
        InteractObjectManager.resetSingleton();
    }

    /**
     * Sets up machines
     */
    @Override
    public void setUpMachines()
    {
        MapManager.getSingleton().changeToNewFile(PlayersForTest.JEFF.getMapName(),
                "NOTHING");
        ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.JEFF);

        // set up players through player manager
        PlayerManager pm = PlayerManager.getSingleton();
        pm.addPlayer(PlayersForTest.JEFF.getPlayerID());

        // set player position
        Player playerFromID = pm.getPlayerFromID(PlayersForTest.JEFF.getPlayerID());
        playerFromID.setPosition(InteractableItemsForTest.BOOK.getPosition());

        InteractObjectManager.getSingleton();
    }
}
