package edu.ship.engr.shipsim.sequencetests;

import com.badlogic.gdx.utils.ObjectMap;
import edu.ship.engr.shipsim.communication.messages.TeleportationContinuationMessage;
import edu.ship.engr.shipsim.communication.messages.TeleportationInitiationMessage;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.datatypes.ServersForTest;
import edu.ship.engr.shipsim.model.*;

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
                            PlayersForTest.MERLIN.getPlayerID(), false, "recCenter.tmx",
                            TELEPORT_POSITION), true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                            new TeleportationContinuationMessage("recCenter.tmx",
                                    ServersForTest.REC_CENTER.getHostName(),
                                    ServersForTest.REC_CENTER.getPortNumber(),
                                    PlayersForTest.MERLIN.getPlayerID(), 1111, false), true)
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
                ServerType.THIS_PLAYER_CLIENT, 0, sequence));
    }

    /**
     * @see edu.ship.engr.shipsim.model.SequenceTest#resetNecessarySingletons()
     */
    public void resetNecessarySingletons()
    {
        PlayerManager.resetSingleton();
        ClientPlayerManager.resetSingleton();
        MapManager.resetSingleton();
    }

    /**
     * @see edu.ship.engr.shipsim.model.SequenceTest#setUpMachines()
     */
    @Override
    public void setUpMachines()
    {
        OptionsManager.getSingleton().setMapFileTitle(PlayersForTest.MERLIN.getMapName());
        PlayerManager.resetSingleton();
        PlayerManager.getSingleton()
                .addPlayerSilently(PlayersForTest.MERLIN.getPlayerID());

        // client
        ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.MERLIN);
        PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
        MapManager.getSingleton().changeToNewFile(PlayersForTest.MERLIN.getMapName(),
                "DUMMY");
        buildTeleportPositionsInMap();
    }

    private void buildTeleportPositionsInMap()
    {
        ObjectMap<Position, edu.ship.engr.shipsim.model.TeleportHotSpot> spots = new ObjectMap<>();
        spots.put(TELEPORT_POSITION,
                new TeleportHotSpot("recCenter.tmx", TELEPORT_POSITION));
        MapManager.getSingleton().setTeleportHotspots(spots);

    }
}
