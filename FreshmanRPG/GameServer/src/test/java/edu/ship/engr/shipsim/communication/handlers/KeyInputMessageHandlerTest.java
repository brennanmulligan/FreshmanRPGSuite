package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.StateAccumulator;
import edu.ship.engr.shipsim.communication.messages.KeyInputMessage;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.ModelFacade;
import edu.ship.engr.shipsim.model.ModelFacadeException;
import edu.ship.engr.shipsim.model.ModelFacadeTestHelper;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetModelFacade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests functionality for a key input message handler
 *
 * @author Ian Keefer & TJ Renninger
 */
@GameTest("GameServer")
@ResetModelFacade(waitUntilEmpty = true)
public class KeyInputMessageHandlerTest
{

    /**
     * Tests the creation and process of a key input message handler. Tests to
     * see the command size has a new command in it.
     *
     * @throws ModelFacadeException Shouldn't
     */
    @Test
    public void testProcessKeyInputMessage() throws ModelFacadeException
    {
        ModelFacade.resetSingleton();
        StateAccumulator accum = new StateAccumulator(null);
        accum.setPlayerId(PlayersForTest.MERLIN.getPlayerID());
        String input = "q";
        KeyInputMessage message = new KeyInputMessage(input, false);
        KeyInputMessageHandler handler = new KeyInputMessageHandler();
        handler.setAccumulator(accum);
        handler.process(message);
        assertEquals(1, ModelFacade.getSingleton().queueSize());
        ModelFacadeTestHelper.waitForFacadeToProcess();
    }

}
