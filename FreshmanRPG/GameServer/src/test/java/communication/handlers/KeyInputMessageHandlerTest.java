package communication.handlers;

import static org.junit.Assert.assertEquals;

import datasource.ServerSideTest;
import datatypes.PlayersForTest;
import org.junit.Test;

import communication.StateAccumulator;
import communication.messages.KeyInputMessage;
import model.ModelFacade;

/**
 * Tests functionality for a key input message handler
 *
 * @author Ian Keefer & TJ Renninger
 *
 */
public class KeyInputMessageHandlerTest extends ServerSideTest
{

	/**
	 * Tests the creation and process of a key input message handler. Tests to
	 * see the command size has a new command in it.
	 */
	@Test
	public void testProcessKeyInputMessage()
	{
		ModelFacade.resetSingleton();
		StateAccumulator accum = new StateAccumulator(null);
		accum.setPlayerId(PlayersForTest.MERLIN.getPlayerID());
		String input = "q";
		KeyInputMessage message = new KeyInputMessage(input);
		KeyInputMessageHandler handler = new KeyInputMessageHandler();
		handler.setAccumulator(accum);
		handler.process(message);
		assertEquals(1, ModelFacade.getSingleton().queueSize());

	}

}
