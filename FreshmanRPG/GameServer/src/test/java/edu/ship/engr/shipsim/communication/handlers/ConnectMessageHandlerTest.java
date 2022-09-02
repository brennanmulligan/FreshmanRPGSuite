package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.ConnectionManager;
import edu.ship.engr.shipsim.communication.messages.ConnectMessage;
import edu.ship.engr.shipsim.datasource.ServerSideTest;
import edu.ship.engr.shipsim.model.ModelFacade;
import edu.ship.engr.shipsim.model.PlayerConnection;
import edu.ship.engr.shipsim.model.PlayerManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author merlin
 */
public class ConnectMessageHandlerTest extends ServerSideTest
{

    /**
     * Reset the PlayerManager before each test
     */
    @Before
    public void reset()
    {
        PlayerManager.resetSingleton();
        ModelFacade.resetSingleton();
    }

    /**
     * Tests that getTypeWeHandle method returns correct type.
     */
    @Test
    public void testTypeWeHandle()
    {
        ConnectMessageHandler h = new ConnectMessageHandler();
        assertEquals(ConnectMessage.class, h.getMessageTypeWeHandle());
    }

    /**
     * The incoming message should cause creation of the player in the model and
     * notification of the player's playerID to the state accumulator
     *
     * @throws InterruptedException Shouldn't
     */
    @Test
    public void tellsStateAccumulatorIfPlayerIDPinIsRecognized() throws InterruptedException
    {
        ConnectMessageHandler handler = new ConnectMessageHandler();
        ConnectionManager connectionManager = new ConnectionManager();
        handler.setConnectionManager(connectionManager);
        ConnectMessage msg = new ConnectMessage(1, PlayerConnection.DEFAULT_PIN);
        handler.process(msg);
        int count = 0;
        while (count < 20 && ModelFacade.getSingleton().hasCommandsPending())
        {
            Thread.sleep(100);
            count++;
        }
        assertTrue("Model never processed our command", count < 20);
        assertEquals(1, connectionManager.getPlayerID());
    }

    /**
     * The incoming message should cause creation of the player in the model and
     * notification of the player's player ID to the state accumulator
     *
     * @throws InterruptedException Shouldn't
     */
    @Test
    public void tellsModel() throws InterruptedException
    {
        ConnectMessageHandler handler = new ConnectMessageHandler();
        ConnectMessage msg = new ConnectMessage(1, PlayerConnection.DEFAULT_PIN);
        handler.process(msg);
        int count = 0;
        while (count < 10 && ModelFacade.getSingleton().hasCommandsPending())
        {
            Thread.sleep(100);
            count++;
        }
        assertTrue("ModelFacade didn't process our command", count < 10);
        // if this doesn't throw a PlayerNotFoundExcetion, all is well
        PlayerManager.getSingleton().getPlayerFromID(34);
    }

}
