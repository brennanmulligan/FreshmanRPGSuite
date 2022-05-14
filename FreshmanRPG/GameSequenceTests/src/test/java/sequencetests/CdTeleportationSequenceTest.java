package sequencetests;

import communication.messages.SendTerminalTextMessage;
import communication.messages.TeleportationContinuationMessage;
import datasource.DatabaseException;
import datasource.PlayerConnectionRowDataGatewayMock;
import datatypes.PlayersForTest;
import datatypes.ServersForTest;
import model.*;

/**
 * When cd Map1 is entered into the terminal, this sequence is sent
 *
 * @author bl5922
 */
public class CdTeleportationSequenceTest extends SequenceTest
{
    @SuppressWarnings("FieldCanBeLocal")
    private final MessageFlow[] sequence =
            {new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
                    new SendTerminalTextMessage(PlayersForTest.MERLIN.getPlayerID(),
                            "cd " + ServersForTest.FIRST_SERVER.getMapTitle()),
                    true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                            new TeleportationContinuationMessage(
                                    ServersForTest.FIRST_SERVER.getMapName(),
                                    ServersForTest.FIRST_SERVER.getHostName(),
                                    ServersForTest.FIRST_SERVER.getPortNumber(),
                                    PlayersForTest.MERLIN.getPlayerID(), 1111),
                            true)
            };


    /**
     *
     */
    public CdTeleportationSequenceTest()
    {
        serverList.add(ServerType.THIS_PLAYER_CLIENT);
        serverList.add(ServerType.AREA_SERVER);

        interaction = new Interaction(sequence,
                new CommandSendTerminalText("cd Firstserver"),
                PlayersForTest.MERLIN.getPlayerID(),
                ServerType.THIS_PLAYER_CLIENT);
    }

    /**
     * @see model.SequenceTest#resetNecessarySingletons()
     */
    @Override
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
    public void setUpMachines() throws DatabaseException
    {
        OptionsManager.getSingleton().setMapName(PlayersForTest.MERLIN.getMapName());
        PlayerManager.resetSingleton();
        PlayerManager.getSingleton()
                .addPlayerSilently(PlayersForTest.MERLIN.getPlayerID());

        ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.MERLIN);
        PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
        MapManager.getSingleton().changeToNewFile(PlayersForTest.MERLIN.getMapName());
    }

}

