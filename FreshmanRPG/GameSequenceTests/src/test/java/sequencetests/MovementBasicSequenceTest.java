package sequencetests;

import communication.messages.OtherPlayerMovedMessage;
import communication.messages.PlayerMovedMessage;
import datatypes.PlayersForTest;
import datatypes.Position;
import model.*;

/**
 * Defines the protocol for a successful login sequence
 *
 * @author Merlin
 */
public class MovementBasicSequenceTest extends SequenceTest
{

    @SuppressWarnings("FieldCanBeLocal")
    private final MessageFlow[] sequence =
            {new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
                    new PlayerMovedMessage(PlayersForTest.MATT.getPlayerID(),
                            new Position(PlayersForTest.MATT.getPosition().getRow(),
                                    PlayersForTest.MATT.getPosition().getColumn() + 1)),
                    true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.OTHER_CLIENT,
                            new OtherPlayerMovedMessage(PlayersForTest.MATT.getPlayerID(),
                                    new Position(
                                            PlayersForTest.MATT.getPosition().getRow(),
                                            PlayersForTest.MATT.getPosition()
                                                    .getColumn() + 1)), true)};

    /**
     *
     */
    public MovementBasicSequenceTest()
    {
        serverList.add(ServerType.THIS_PLAYER_CLIENT);
        serverList.add(ServerType.OTHER_CLIENT);
        serverList.add(ServerType.AREA_SERVER);

        interactions.add(new Interaction(
                new CommandClientMovePlayer(PlayersForTest.MATT.getPlayerID(),
                        new Position(PlayersForTest.MATT.getPosition().getRow(),
                                PlayersForTest.MATT.getPosition().getColumn() + 1)),
                PlayersForTest.MATT.getPlayerID(), ServerType.THIS_PLAYER_CLIENT,
                sequence));
    }

    /**
     * @see model.SequenceTest#resetNecessarySingletons()
     */
    public void resetNecessarySingletons()
    {
        PlayerManager.resetSingleton();
    }

    /**
     * @see model.SequenceTest#setUpMachines()
     */
    @Override
    public void setUpMachines()
    {
        MapManager.getSingleton().changeToNewFile(PlayersForTest.MATT.getMapName(),
        "MORESTUFF");
        ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.MATT);
        PlayerManager.getSingleton().addPlayer(PlayersForTest.MATT.getPlayerID());
    }
}
