package sequencetests;

import com.badlogic.gdx.utils.ObjectMap;
import communication.messages.TeleportationContinuationMessage;
import communication.messages.TeleportationInitiationMessage;
import datasource.DatabaseException;
import datasource.PlayerConnectionRowDataGatewayMock;
import datatypes.PlayersForTest;
import datatypes.Position;
import datatypes.ServersForTest;
import model.*;

import java.io.IOException;

/**
 * Defines the protocol for a successful login sequence
 *
 * @author Merlin
 */
public class TeleportationMovementSequenceTest extends SequenceTest
{

    private static final Position TELEPORT_POSITION = new Position(47, 16);

    @SuppressWarnings("FieldCanBeLocal")
    private final MessageFlow[] sequence =
            {new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
                    new TeleportationInitiationMessage(
                            PlayersForTest.MERLIN.getPlayerID(), "recCenter.tmx",
                            TELEPORT_POSITION), true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                            new TeleportationContinuationMessage("recCenter.tmx",
                                    ServersForTest.REC_CENTER.getHostName(),
                                    ServersForTest.REC_CENTER.getPortNumber(),
                                    PlayersForTest.MERLIN.getPlayerID(), 1111), true)
                    // from there, the connection sequence is the same as for the login
                    // tests
            };


    public TeleportationMovementSequenceTest()
    {
        serverList.add(ServerType.THIS_PLAYER_CLIENT);
        serverList.add(ServerType.AREA_SERVER);

        interactions.add(new Interaction(
                new CommandClientMovePlayer(PlayersForTest.MERLIN.getPlayerID(),
                        TELEPORT_POSITION), PlayersForTest.MERLIN.getPlayerID(),
                ServerType.THIS_PLAYER_CLIENT, sequence));
    }

    /**
     * @see model.SequenceTest#resetNecessarySingletons()
     */
    public void resetNecessarySingletons()
    {
        try
        {
            PlayerManager.resetSingleton();
            (new PlayerConnectionRowDataGatewayMock(
                    PlayersForTest.MERLIN.getPlayerID())).resetData();
            ClientPlayerManager.resetSingleton();
            MapManager.resetSingleton();
        } catch (DatabaseException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @see model.SequenceTest#setUpMachines()
     */
    @Override
    public void setUpMachines()
    {
        OptionsManager.getSingleton().setMapName(PlayersForTest.MERLIN.getMapName());
        PlayerManager.resetSingleton();
        PlayerManager.getSingleton()
                .addPlayerSilently(PlayersForTest.MERLIN.getPlayerID());

        // client
        ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.MERLIN);
        PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
        MapManager.getSingleton().changeToNewFile(PlayersForTest.MERLIN.getMapName());
        buildTeleportPositionsInMap();
    }

    private void buildTeleportPositionsInMap()
    {
        ObjectMap<Position, TeleportHotSpot> spots = new ObjectMap<>();
        spots.put(TELEPORT_POSITION,
                new TeleportHotSpot("recCenter.tmx", TELEPORT_POSITION));
        MapManager.getSingleton().setTeleportHotspots(spots);

    }
}
