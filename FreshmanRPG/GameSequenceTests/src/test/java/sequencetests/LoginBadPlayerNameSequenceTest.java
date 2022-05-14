package sequencetests;


import communication.messages.LoginFailedMessage;
import communication.messages.LoginMessage;
import datasource.DatabaseException;
import datasource.PlayerConnectionRowDataGatewayMock;
import datatypes.PlayersForTest;
import model.*;

import java.io.IOException;

/**
 * Defines the protocol for a successful login sequence
 *
 * @author Merlin
 */
public class LoginBadPlayerNameSequenceTest extends SequenceTest
{

    private final MessageFlow[] sequence =
            {
                    new MessageFlow(ServerType.THIS_PLAYER_CLIENT,
                            ServerType.LOGIN_SERVER,
                            new LoginMessage(PlayersForTest.MERLIN.getPlayerName() + "Z",
                                    PlayersForTest.MERLIN.getPlayerPassword()), true),
                    new MessageFlow(ServerType.LOGIN_SERVER,
                            ServerType.THIS_PLAYER_CLIENT,
                            new LoginFailedMessage(), true)
            };

    /**
     * @throws IOException shouldn't
     */
    public LoginBadPlayerNameSequenceTest()
    {
        serverList.add(ServerType.THIS_PLAYER_CLIENT);
        serverList.add(ServerType.LOGIN_SERVER);
        interaction = new Interaction(sequence,
                new CommandLogin(PlayersForTest.MERLIN.getPlayerName() + "Z",
                        PlayersForTest.MERLIN.getPlayerPassword()),
                PlayersForTest.MERLIN.getPlayerID(),
                ServerType.THIS_PLAYER_CLIENT);
    }

    /**
     * @see model.SequenceTest#setUpMachines()
     */
    @Override
    public void setUpMachines()
    {
        OptionsManager.getSingleton().setMapName(PlayersForTest.MERLIN.getMapName());
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
        } catch (DatabaseException e)
        {
            e.printStackTrace();
        }
    }
}
