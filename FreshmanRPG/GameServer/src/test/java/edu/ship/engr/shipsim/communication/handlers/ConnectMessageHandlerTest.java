package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.ConnectionManager;
import edu.ship.engr.shipsim.communication.messages.ConnectMessage;
import edu.ship.engr.shipsim.datasource.LoggerManager;
import edu.ship.engr.shipsim.model.*;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetModelFacade;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * @author merlin
 */
@GameTest("GameServer")
@ResetModelFacade(waitUntilEmpty = true)
@ResetPlayerManager
public class ConnectMessageHandlerTest
{
    /**
     * The incoming message should cause creation of the player in the model and
     * notification of the player's player ID to the state accumulator
     *
     * @throws ModelFacadeException Shouldn't
     */
    @Test
    public void tellsModel() throws ModelFacadeException
    {
        ConnectMessageHandler handler = new ConnectMessageHandler();
        ConnectMessage msg = new ConnectMessage(1, false,PlayerConnection.DEFAULT_PIN);
        handler.process(msg);

        ModelFacadeTestHelper.waitForFacadeToProcess();

        // if this doesn't throw a PlayerNotFoundException, all is well
        PlayerManager.getSingleton().getPlayerFromID(34);
    }

    /**
     * The incoming message should cause creation of the player in the model and
     * notification of the player's playerID to the state accumulator
     *
     * @throws ModelFacadeException Shouldn't
     */
    @Test
    public void tellsStateAccumulatorIfPlayerIDPinIsRecognized()
            throws ModelFacadeException
    {
        ConnectMessageHandler handler = new ConnectMessageHandler();
        ConnectionManager connectionManager = new ConnectionManager();
        handler.setConnectionManager(connectionManager);
        ConnectMessage msg = new ConnectMessage(1, false,PlayerConnection.DEFAULT_PIN);
        handler.process(msg);

        Logger logger = LoggerManager.getSingleton().getLogger();
        logger.info("In tellsStateAccumulatorIfPlayerIDPIN is Recognized: Waiting for model to process");
        ModelFacadeTestHelper.waitForFacadeToProcess();
        logger.info("In tellsStateAccumulatorIfPlayerIDPIN is Recognized: Model has processed");
        logger.info("In tellsStateAccumulatorIfPlayerIDPIN is Recognized: Player Id is " + connectionManager.getPlayerID());

        assertEquals(1, connectionManager.getPlayerID());
    }

    /**
     * Tests that getTypeWeHandle method returns correct type.
     */
    @Test
    public void testTypeWeHandle()
    {
        ConnectMessageHandler h = new ConnectMessageHandler();
        assertSame(ConnectMessage.class, h.getMessageTypeWeHandle());
    }

}
