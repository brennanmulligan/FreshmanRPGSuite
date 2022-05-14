package sequencetests;

import communication.messages.KeyInputMessage;
import datatypes.PlayersForTest;
import model.*;

/**
 * A player presses e and sends a message to the server, no message gets sent back
 *
 * @author Elisabeth Ostrow, Stephen Clabaugh
 */
public class ObjectNotInRangeSequenceTest extends SequenceTest
{

    /**
     * the flow of messages to occur
     */
    private final MessageFlow[] sequence =
            {
                    new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
                            new KeyInputMessage("e"), true)

            };

    /**
     * runs through the message flow
     */
    public ObjectNotInRangeSequenceTest()
    {
        serverList.add(ServerType.THIS_PLAYER_CLIENT);
        serverList.add(ServerType.AREA_SERVER);

        interaction = new Interaction(sequence,
                new CommandKeyInputSent("e"),
                PlayersForTest.MERLIN.getPlayerID(),
                ServerType.THIS_PLAYER_CLIENT);
    }

    /**
     * reset data
     */
    @Override
    public void resetNecessarySingletons()
    {
        PlayerManager.resetSingleton();
        InteractObjectManager.resetSingleton();
    }

    /**
     * any setup required
     */
    @Override
    public void setUpMachines()
    {
        MapManager.getSingleton().changeToNewFile(PlayersForTest.MERLIN.getMapName());
        ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.MERLIN);
        PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());

    }

}
