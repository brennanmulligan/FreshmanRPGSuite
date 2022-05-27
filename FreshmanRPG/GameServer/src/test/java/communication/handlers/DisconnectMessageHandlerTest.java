package communication.handlers;

import datasource.ServerSideTest;
import org.junit.Before;
import org.junit.Test;

import communication.messages.DisconnectMessage;
import model.ModelFacade;
import model.PlayerManager;

import static org.junit.Assert.*;

/**
 *
 * @author merlin
 *
 */
public class DisconnectMessageHandlerTest extends ServerSideTest
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
		DisconnectMessageHandler h = new DisconnectMessageHandler();
		assertEquals(DisconnectMessage.class, h.getMessageTypeWeHandle());
	}

	/**
	 * The incoming message should cause the deletion of the player in the model
	 * and notification of the player's player ID to the state accumulator
	 *
	 * @throws InterruptedException Shouldn't
	 */
	@Test
	public void tellsModel() throws InterruptedException
	{
		PlayerManager pm = PlayerManager.getSingleton();
		pm.addPlayer(1);
		assertNotNull(pm.getPlayerFromID(1));
		DisconnectMessageHandler handler = new DisconnectMessageHandler();
		DisconnectMessage msg = new DisconnectMessage(1);
		handler.process(msg);
		int count = 0;
		while (count < 10 && ModelFacade.getSingleton().hasCommandsPending())
		{
			Thread.sleep(100);
			count++;
		}
		assertTrue("ModelFacade didn't process our command", count < 10);

		// if this doesn't throw a PlayerNotFoundExcetion, all is well
		assertNull(pm.getPlayerFromID(1));
	}

}
