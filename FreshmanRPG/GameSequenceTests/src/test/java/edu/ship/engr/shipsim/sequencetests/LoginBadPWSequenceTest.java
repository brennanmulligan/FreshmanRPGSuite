package edu.ship.engr.shipsim.sequencetests;


import edu.ship.engr.shipsim.communication.messages.LoginFailedMessage;
import edu.ship.engr.shipsim.communication.messages.LoginMessage;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.*;

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
                            PlayersForTest.MERLIN.getPlayerPassword() + "Z", false), true),
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
                0, sequence));
    }

    /**
     * @see edu.ship.engr.shipsim.model.SequenceTest#resetNecessarySingletons()
     */
    public void resetNecessarySingletons()
    {
        PlayerManager.resetSingleton();
    }

    /**
     * @see edu.ship.engr.shipsim.model.SequenceTest#setUpMachines()
     */
    @Override
    public void setUpMachines()
    {
        OptionsManager.getSingleton().setMapFileTitle(PlayersForTest.MERLIN.getMapName());
    }
}
