package communication.handlers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import communication.ConnectionManager;
import communication.messages.ConnectMessage;
import model.ModelFacade;
import model.OptionsManager;
import model.PlayerConnection;
import model.PlayerManager;

/**
 *
 * @author merlin
 *
 */
public class ConnectMessageHandlerTest
{

	/**
	 * Reset the PlayerManager before each test
	 */
	@Before
	public void reset()
	{
		OptionsManager.resetSingleton();
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
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
		while (ModelFacade.getSingleton().hasCommandsPending())
		{
			Thread.sleep(100);
		}
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
		while (ModelFacade.getSingleton().hasCommandsPending())
		{
			Thread.sleep(100);
		}
		// if this doesn't throw a PlayerNotFoundExcetion, all is well
		PlayerManager.getSingleton().getPlayerFromID(34);
	}

}
