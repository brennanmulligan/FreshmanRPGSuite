package sequencetests;

import communication.messages.InteractionDeniedMessage;
import communication.messages.KeyInputMessage;
import datatypes.InteractableItemsForTest;
import datatypes.PlayersForTest;
import model.*;

/**
 * Tests that a player cannot receive another buff if it already has one
 *
 * @author Jake Moore
 */
public class NoMultipleBuffSequenceTest extends SequenceTest
{
    private final MessageFlow[] sequence =
            {
                    new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
                            new KeyInputMessage("e"), true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                            new InteractionDeniedMessage(
                                    PlayersForTest.JEFF.getPlayerID()), true)
            };

    /**
     * Runs through the message flow
     */
    public NoMultipleBuffSequenceTest()
    {
        this.serverList.add(ServerType.THIS_PLAYER_CLIENT);
        this.serverList.add(ServerType.AREA_SERVER);
        interaction = new Interaction(sequence,
                new CommandKeyInputSent("e"),
                PlayersForTest.JEFF.getPlayerID(),
                ServerType.THIS_PLAYER_CLIENT);
    }

    /**
     * Sets up machines
     */
    @Override
    public void setUpMachines()
    {
        MapManager.getSingleton().changeToNewFile(PlayersForTest.JEFF.getMapName());
        ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.JEFF);

        // set up players through player manager
        PlayerManager pm = PlayerManager.getSingleton();
        pm.addPlayer(PlayersForTest.JEFF.getPlayerID());

        // set player position
        Player playerFromID = pm.getPlayerFromID(PlayersForTest.JEFF.getPlayerID());
        playerFromID.setPlayerPosition(InteractableItemsForTest.BOOK.getPosition());

        InteractObjectManager.getSingleton();
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
}
