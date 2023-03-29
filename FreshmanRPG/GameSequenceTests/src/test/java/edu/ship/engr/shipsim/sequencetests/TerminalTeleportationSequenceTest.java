package edu.ship.engr.shipsim.sequencetests;

import edu.ship.engr.shipsim.communication.messages.SendTerminalTextMessage;
import edu.ship.engr.shipsim.communication.messages.TeleportationContinuationMessage;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.datatypes.ServersForTest;
import edu.ship.engr.shipsim.model.*;

/**
 * When cd Map1 is entered into the terminal, this sequence is sent
 *
 * @author bl5922
 */
public class TerminalTeleportationSequenceTest extends SequenceTest
{
    @SuppressWarnings("FieldCanBeLocal")
    private final MessageFlow[] sequence =
            {new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
                    new SendTerminalTextMessage(PlayersForTest.MERLIN.getPlayerID(), false,
                            "teleport " + ServersForTest.FIRST_SERVER.getMapTitle()),
                    true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                            new TeleportationContinuationMessage(
                                    ServersForTest.FIRST_SERVER.getMapName(),
                                    ServersForTest.FIRST_SERVER.getHostName(),
                                    ServersForTest.FIRST_SERVER.getPortNumber(),
                                    PlayersForTest.MERLIN.getPlayerID(), 1111, false), true)};


    /**
     *
     */
    public TerminalTeleportationSequenceTest()
    {
        serverList.add(ServerType.THIS_PLAYER_CLIENT);
        serverList.add(ServerType.AREA_SERVER);

        interactions.add(new Interaction(new CommandSendTerminalText("teleport " +
                "Firstserver"),
                PlayersForTest.MERLIN.getPlayerID(), ServerType.THIS_PLAYER_CLIENT,
                0, sequence));
    }

    /**
     * @see edu.ship.engr.shipsim.model.SequenceTest#resetNecessarySingletons()
     */
    @Override
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
    public void setUpMachines() throws DatabaseException
    {
        OptionsManager.getSingleton().setMapFileTitle(PlayersForTest.MERLIN.getMapName());
        PlayerManager.resetSingleton();
        PlayerManager.getSingleton()
                .addPlayerSilently(PlayersForTest.MERLIN.getPlayerID());

        ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.MERLIN);
        PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
        MapManager.getSingleton().changeToNewFile(PlayersForTest.MERLIN.getMapName(),
                "STUFF");
    }

}

