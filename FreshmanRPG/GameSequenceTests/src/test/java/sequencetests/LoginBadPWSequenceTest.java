package sequencetests;


import communication.messages.LoginFailedMessage;
import communication.messages.LoginMessage;
import datasource.DatabaseException;
import datasource.PlayerConnectionRowDataGatewayMock;
import datatypes.PlayersForTest;
import model.*;

/**
 * Defines the protocol for a successful login sequence
 *
 * @author Merlin
 */
public class LoginBadPWSequenceTest extends SequenceTest
{

    @SuppressWarnings("FieldCanBeLocal")
    private final MessageFlow[] sequence =
            {new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.LOGIN_SERVER,
                    new LoginMessage(PlayersForTest.MERLIN.getPlayerName(),
                            PlayersForTest.MERLIN.getPlayerPassword() + "Z"), true),
                    new MessageFlow(ServerType.LOGIN_SERVER,
                            ServerType.THIS_PLAYER_CLIENT, new LoginFailedMessage(),
                            true)};


    public LoginBadPWSequenceTest()
    {
        serverList.add(ServerType.THIS_PLAYER_CLIENT);
        serverList.add(ServerType.LOGIN_SERVER);
        interactions.add(new Interaction(
                new CommandLogin(PlayersForTest.MERLIN.getPlayerName(),
                        PlayersForTest.MERLIN.getPlayerPassword() + "Z"),
                PlayersForTest.MERLIN.getPlayerID(), ServerType.THIS_PLAYER_CLIENT,
                sequence));
    }

    /**
     * @see model.SequenceTest#resetNecessarySingletons()
     */
    public void resetNecessarySingletons()
    {
        try
        {
            PlayerManager.resetSingleton();
            (new PlayerConnectionRowDataGatewayMock(2)).resetData();
        }
        catch (DatabaseException e)
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
    }
}
