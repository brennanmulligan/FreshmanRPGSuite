package communication.handlers;

import static org.junit.Assert.assertEquals;

import communication.messages.ChatMessageToServer;
import datasource.ServerSideTest;
import org.junit.Before;
import org.junit.Test;

import datatypes.ChatType;
import datatypes.Position;
import model.ModelFacade;
import model.QualifiedObservableConnector;

/**
 * Testing the ChatMessageHandler
 *
 * @author Josh
 */

public class ChatMessageToServerHandlerTest extends ServerSideTest
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
		ChatMessageToServerHandler h = new ChatMessageToServerHandler();
		assertEquals(ChatMessageToServer.class, h.getMessageTypeWeHandle());
	}

}
