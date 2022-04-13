package communication.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import communication.messages.DisconnectMessage;
import model.ModelFacade;
import model.OptionsManager;
import model.PlayerManager;

/**
 *
 * @author merlin
 *
 */
public class DisconnectMessageHandlerTest
{

	/**
	 * Reset the PlayerManager before each test
	 */
	@Before
	public void reset()
	{
		PlayerManager.resetSingleton();
		ModelFacade.resetSingleton();
		OptionsManager.resetSingleton();
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
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
		while (ModelFacade.getSingleton().hasCommandsPending())
		{
			Thread.sleep(100);
		}
		// if this doesn't throw a PlayerNotFoundExcetion, all is well
		assertNull(pm.getPlayerFromID(1));
	}

}
