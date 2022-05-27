package communication.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import datasource.ServerSideTest;
import datatypes.PlayersForTest;
import org.junit.Before;
import org.junit.Test;

import communication.messages.ObjectiveNotificationCompleteMessage;
import model.ModelFacade;
import model.OptionsManager;
import model.PlayerManager;
import datatypes.ObjectivesForTest;
import datatypes.QuestsForTest;

/**
 * Test for ObjectiveNotificationCompleteMessageHandler
 *
 * @author Ryan
 *
 */
public class ObjectiveNotificationCompleteMessageHandlerTest extends ServerSideTest
{

	/**
	 * Reset the PlayerManager
	 */
	@Before
	public void reset()
	{
		PlayerManager.resetSingleton();
		ModelFacade.resetSingleton();
		OptionsManager.resetSingleton();
		OptionsManager.getSingleton().setTestMode(true);
	}

	/**
	 * Test what message type we handle
	 */
	@Test
	public void testMessageWeHandle()
	{
		ObjectiveNotificationCompleteMessageHandler h = new ObjectiveNotificationCompleteMessageHandler();

		assertEquals(ObjectiveNotificationCompleteMessage.class, h.getMessageTypeWeHandle());
	}
}
