package communication.handlers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import datatypes.PlayersForTest;
import org.junit.Before;
import org.junit.Test;

import communication.StateAccumulator;
import communication.messages.HighScoreRequestMessage;
import communication.messages.HighScoreResponseMessage;
import communication.messages.Message;
import datasource.DatabaseException;
import model.ModelFacade;
import model.OptionsManager;
import model.PlayerManager;

/**
 * Make sure the right response gets queued back to the player
 *
 * @author Merlin
 *
 */
public class HighScoreRequestMessageHandlerTest
{
	/**
	 * Reset the PlayerManager
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
	 * It should correctly report the type of messages it handles
	 */
	@Test
	public void messageTypeCorrect()
	{
		HighScoreRequestMessageHandler handler = new HighScoreRequestMessageHandler();
		assertEquals(HighScoreRequestMessage.class, handler.getMessageTypeWeHandle());
	}

	/**
	 * Make sure that the appropriate response message gets queued into the
	 * accumulator
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void generatesCorrectResponse() throws DatabaseException
	{
		PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
		HighScoreRequestMessageHandler handler = new HighScoreRequestMessageHandler();
		StateAccumulator accum = new StateAccumulator(null);
		accum.setPlayerId(PlayersForTest.MERLIN.getPlayerID());
		handler.setAccumulator(accum);
		HighScoreResponseMessage msg = new HighScoreResponseMessage(PlayerManager.getSingleton().getTopTenPlayers());

		handler.process(msg);

		// nothing should be queued to the model
		assertEquals(0, ModelFacade.getSingleton().queueSize());

		// make sure we queued the appropriate response
		ArrayList<Message> queue = accum.getPendingMsgs();
		assertEquals(1, queue.size());
		HighScoreResponseMessage response = (HighScoreResponseMessage) queue.get(0);
		assertEquals(10, response.getScoreReports().size());

	}

}
