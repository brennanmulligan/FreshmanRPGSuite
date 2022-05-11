package sequencetests;

import java.io.IOException;

import com.badlogic.gdx.utils.ObjectMap;

import communication.messages.ReceiveTerminalTextMessage;
import communication.messages.SendTerminalTextMessage;
import communication.messages.TeleportationContinuationMessage;
import datasource.DatabaseException;
import datasource.PlayerConnectionRowDataGatewayMock;
import datatypes.PlayersForTest;
import datatypes.Position;
import datatypes.ServersForTest;
import model.ClientModelTestUtilities;
import model.ClientPlayerManager;
import model.Command;
import model.CommandSendTerminalText;
import model.MapManager;
import model.MessageFlow;
import model.OptionsManager;
import model.PlayerManager;
import model.SequenceTest;
import model.ServerType;

/**
 * When cd Quiznasium is entered into the terminal, this sequence is sent
 * @author bl5922
 *
 */
public class CdTeleportationSequenceTest extends SequenceTest
{
    private static final Position TELEPORT_POSITION = new Position(10, 10);

    private MessageFlow[] sequence =
            { new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
                    new SendTerminalTextMessage(PlayersForTest.MERLIN.getPlayerID(),
                            "cd " + ServersForTest.FIRST_SERVER.getMapTitle()),
                    true),
                   new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                            new TeleportationContinuationMessage(ServersForTest.FIRST_SERVER.getMapName(),
                                    ServersForTest.FIRST_SERVER.getHostName(),
                                    ServersForTest.FIRST_SERVER.getPortNumber(),
                            PlayersForTest.MERLIN.getPlayerID(), 1111),
                            true)
            };

    /**
     * @throws IOException
     * 			shouldn't
     */
    public CdTeleportationSequenceTest() throws IOException
    {
        for (MessageFlow mf : sequence)
        {
            messageSequence.add(mf);
        }

        serverList.add(ServerType.THIS_PLAYER_CLIENT);
        serverList.add(ServerType.AREA_SERVER);
    }

    /**
     * @see model.SequenceTest#getInitiatingCommand()
     */
    @Override
    public Command getInitiatingCommand()
    {
        return new CommandSendTerminalText("cd Firstserver");
    }

    /**
     * @see model.SequenceTest#getInitiatingServerType()
     */
    @Override
    public ServerType getInitiatingServerType()
    {
        return ServerType.THIS_PLAYER_CLIENT;
    }

    /**
     * 	 @see model.SequenceTest#getInitiatingPlayerID()
     */
    @Override
    public int getInitiatingPlayerID()
    {
        return PlayersForTest.MERLIN.getPlayerID();
    }

    /**
     * @see model.SequenceTest#setUpMachines()
     */
    @Override
    public void setUpMachines() throws DatabaseException
    {
        OptionsManager.getSingleton().setMapName(PlayersForTest.MERLIN.getMapName());
        PlayerManager.resetSingleton();
        PlayerManager.getSingleton().addPlayerSilently(PlayersForTest.MERLIN.getPlayerID());

        ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.MERLIN);
        PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
        MapManager.getSingleton().changeToNewFile(PlayersForTest.MERLIN.getMapName());
    }


    /**
     * @see model.SequenceTest#resetDataGateways()
     */
    @Override
    public void resetDataGateways()
    {
        try
        {
            PlayerManager.resetSingleton();
            (new PlayerConnectionRowDataGatewayMock(PlayersForTest.MERLIN.getPlayerID())).resetData();
            ClientPlayerManager.resetSingleton();
            MapManager.resetSingleton();
        } catch (DatabaseException e)
        {
            e.printStackTrace();
        }

    }

}
