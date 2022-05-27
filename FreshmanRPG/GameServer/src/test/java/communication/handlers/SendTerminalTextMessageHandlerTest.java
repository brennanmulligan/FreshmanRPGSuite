package communication.handlers;

import static org.junit.Assert.assertEquals;

import datasource.ServerSideTest;
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
public class SendTerminalTextMessageHandlerTest extends ServerSideTest
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
	 * Tests that getTypeWeHandle method returns correct type.
	 */
	@Test
	public void testTypeWeHandle()
	{
		SendTerminalTextMessageHandler h = new SendTerminalTextMessageHandler();
		assertEquals(SendTerminalTextMessage.class, h.getMessageTypeWeHandle());
	}

}
