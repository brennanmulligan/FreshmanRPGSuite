package communication.handlers;

import static org.junit.Assert.assertEquals;

import datatypes.PlayersForTest;
import org.junit.Before;
import org.junit.Test;

import communication.messages.SendTerminalTextMessage;
import model.ModelFacade;
import model.QualifiedObservableConnector;

/**
 *
 * @author Denny Fleagle
 * @author Ben Lehman
 * @author Austin Smale
 *
 */
public class SendTerminalTextMessageHandlerTest
{
	/**
	 * Reset the ModelFacade
	 */
	@Before
	public void reset()
	{
		ModelFacade.resetSingleton();
		QualifiedObservableConnector.resetSingleton();
	}

	/**
	 * Test the handler processes the messaged
	 * @throws InterruptedException for thread.sleep
	 */
	@Test
	public void testHandlerProcess() throws InterruptedException
	{
		reset();
		SendTerminalTextMessage message = new SendTerminalTextMessage(PlayersForTest.MERLIN.getPlayerID(), "who");
		SendTerminalTextMessageHandler handler = new SendTerminalTextMessageHandler();
		handler.process(message);
		assertEquals(1, ModelFacade.getSingleton().queueSize());

		while (ModelFacade.getSingleton().hasCommandsPending())
		{
			Thread.sleep(100);
		}
		reset();
	}

	/**
	 * Tests that getTypeWeHandle method returns correct type.
	 */
	@Test
	public void testTypeWeHandle()
	{
		SendTerminalTextMessageHandler h = new SendTerminalTextMessageHandler();
		assertEquals(SendTerminalTextMessage.class, h.getMessageTypeWeHandle());
	}

}
