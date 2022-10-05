package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.StateAccumulator;
import edu.ship.engr.shipsim.communication.messages.LoginFailedMessage;
import edu.ship.engr.shipsim.communication.messages.LoginMessage;
import edu.ship.engr.shipsim.communication.messages.LoginSuccessfulMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.LoginPlayerManager;
import edu.ship.engr.shipsim.model.OptionsManager;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Merlin
 */
@GameTest("LoginServer")
public class LoginMessageHandlerTest
{

    /**
     * reset the singleton before each test
     */
    @BeforeEach
    public void setup()
    {
        OptionsManager.resetSingleton();
        LoginPlayerManager.resetSingleton();
    }

    /**
     *
     */
    @Test
    public void tellsTheModel()
    {
        LoginMessageHandler handler = new LoginMessageHandler();
        StateAccumulator accum = new StateAccumulator(null);
        handler.setAccumulator(accum);
        handler.process(
                new LoginMessage(PlayersForTest.MERLIN_OFFLINE.getPlayerName(), PlayersForTest.MERLIN_OFFLINE.getPlayerPassword()));
        assertEquals(1, LoginPlayerManager.getSingleton().getNumberOfPlayers());
    }

    /**
     * Make sure that the login message handler queues the appropriate response
     * for successful login
     */
    @Test
    public void queuesResonse()
    {
        LoginMessageHandler handler = new LoginMessageHandler();
        StateAccumulator accum = new StateAccumulator(null);
        handler.setAccumulator(accum);
        handler.process(
                new LoginMessage(PlayersForTest.MERLIN_OFFLINE.getPlayerName(), PlayersForTest.MERLIN_OFFLINE.getPlayerPassword()));

        ArrayList<Message> x = accum.getPendingMsgs();
        LoginSuccessfulMessage response = (LoginSuccessfulMessage) x.get(0);
        assertEquals(PlayersForTest.MERLIN_OFFLINE.getPlayerID(), response.getPlayerID());
        assertEquals("localhost", response.getHostName());
        assertEquals(1883, response.getPortNumber());
    }

    /**
     * Make sure that the login message handler queues the appropriate response
     * for successful login
     */
    @Test
    public void queuesResonseFailure()
    {
        LoginMessageHandler handler = new LoginMessageHandler();
        StateAccumulator accum = new StateAccumulator(null);
        handler.setAccumulator(accum);
        handler.process(new LoginMessage(PlayersForTest.MERLIN.getPlayerName(),
                PlayersForTest.MERLIN.getPlayerPassword() + 'Z'));

        ArrayList<Message> x = accum.getPendingMsgs();
        assertEquals(LoginFailedMessage.class, x.get(0).getClass());

    }
}
