package communication.handlers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import datasource.ServerSideTest;
import org.junit.Before;
import org.junit.Test;

import communication.StateAccumulator;
import communication.messages.LoginFailedMessage;
import communication.messages.LoginMessage;
import communication.messages.LoginSuccessfulMessage;
import communication.messages.Message;
import model.LoginPlayerManager;
import model.OptionsManager;
import datatypes.PlayersForTest;

/**
 * @author Merlin
 *
 */
public class LoginMessageHandlerTest extends ServerSideTest
{

	/**
	 * reset the singleton before each test
	 */
	@Before
	public void setup()
	{
		OptionsManager.resetSingleton();
		LoginPlayerManager.resetSingleton();
	}

	/**
	 *
	 */
	@Test
	public void tellsTheModel()
	{
		LoginMessageHandler handler = new LoginMessageHandler();
		StateAccumulator accum = new StateAccumulator(null);
		handler.setAccumulator(accum);
		handler.process(
				new LoginMessage(PlayersForTest.MERLIN_OFFLINE.getPlayerName(), PlayersForTest.MERLIN_OFFLINE.getPlayerPassword()));
		assertEquals(1, LoginPlayerManager.getSingleton().getNumberOfPlayers());
	}

	/**
	 * Make sure that the login message handler queues the appropriate response
	 * for successful login
	 */
	@Test
	public void queuesResonse()
	{
		LoginMessageHandler handler = new LoginMessageHandler();
		StateAccumulator accum = new StateAccumulator(null);
		handler.setAccumulator(accum);
		handler.process(
				new LoginMessage(PlayersForTest.MERLIN_OFFLINE.getPlayerName(), PlayersForTest.MERLIN_OFFLINE.getPlayerPassword()));

		ArrayList<Message> x = accum.getPendingMsgs();
		LoginSuccessfulMessage response = (LoginSuccessfulMessage) x.get(0);
		assertEquals(PlayersForTest.MERLIN_OFFLINE.getPlayerID(), response.getPlayerID());
		assertEquals("localhost", response.getHostName());
		assertEquals(1883, response.getPortNumber());
	}

	/**
	 * Make sure that the login message handler queues the appropriate response
	 * for successful login
	 */
	@Test
	public void queuesResonseFailure()
	{
		LoginMessageHandler handler = new LoginMessageHandler();
		StateAccumulator accum = new StateAccumulator(null);
		handler.setAccumulator(accum);
		handler.process(new LoginMessage(PlayersForTest.MERLIN.getPlayerName(),
				PlayersForTest.MERLIN.getPlayerPassword() + 'Z'));

		ArrayList<Message> x = accum.getPendingMsgs();
		assertEquals(LoginFailedMessage.class, x.get(0).getClass());

	}
}
