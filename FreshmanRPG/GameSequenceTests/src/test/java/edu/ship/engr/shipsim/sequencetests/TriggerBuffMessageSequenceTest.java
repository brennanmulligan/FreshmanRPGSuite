package edu.ship.engr.shipsim.sequencetests;

import edu.ship.engr.shipsim.communication.messages.BuffMessage;
import edu.ship.engr.shipsim.communication.messages.KeyInputMessage;
import edu.ship.engr.shipsim.datatypes.InteractableItemsForTest;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.*;

/**
 * Tests that when a Client presses e the server adds exp points to the player
 *
 * @author ed9737, sk5587, tc9538
 */
public class TriggerBuffMessageSequenceTest extends SequenceTest
{

    /**
     * the sequence of messages to occur
     */
    @SuppressWarnings("FieldCanBeLocal")
    private final MessageFlow[] sequence =
            {new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
                    new KeyInputMessage("e", false), true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                            new BuffMessage(PlayersForTest.JAWN.getPlayerID(), false,
                                    InteractableItemsForTest.BOOK.getExperiencePointPool()),
                            true)

            };

    /**
     * Runs through the message flow
     */
    public TriggerBuffMessageSequenceTest()
    {
        serverList.add(ServerType.THIS_PLAYER_CLIENT);
        serverList.add(ServerType.AREA_SERVER);

        interactions.add(new Interaction(new CommandKeyInputSent("e"),
                PlayersForTest.JAWN.getPlayerID(), ServerType.THIS_PLAYER_CLIENT,
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
     * generic setup
     */
    @Override
    public void setUpMachines()
    {
        MapManager.getSingleton().changeToNewFile(PlayersForTest.JAWN.getMapName(),
                "MORESTUFFF");
        ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.JAWN);

        // set up players through player manager
        PlayerManager pm = PlayerManager.getSingleton();
        pm.addPlayer(PlayersForTest.JAWN.getPlayerID());

        // set player position
        Player playerFromID = pm.getPlayerFromID(PlayersForTest.JAWN.getPlayerID());
        playerFromID.setPosition(InteractableItemsForTest.BOOK.getPosition());

        InteractObjectManager.getSingleton();
    }

}
