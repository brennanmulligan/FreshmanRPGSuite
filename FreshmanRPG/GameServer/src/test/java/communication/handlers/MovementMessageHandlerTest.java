package communication.handlers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import communication.messages.PlayerMovedMessage;
import datatypes.Position;
import model.ModelFacade;
import model.OptionsManager;
import model.Player;
import model.PlayerManager;

/**
 *
 * @author merlin
 *
 */
public class MovementMessageHandlerTest
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
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
	}

	/**
	 * Tests that getTypeWeHandle method returns correct type.
	 */
	@Test
	public void testTypeWeHandle()
	{
		MovementMessageHandler h = new MovementMessageHandler();
		assertEquals(PlayerMovedMessage.class, h.getMessageTypeWeHandle());
	}

	/**
	 * Start with a player in a position, send that player through a movement
	 * message and ensure that the player has the new position
	 *
	 * @throws InterruptedException Shouldn't
	 */
	@Test
	public void updatesAPlayerPosition() throws InterruptedException
	{
		int playerID = 1;
		Position startPosition = new Position(0, 0);
		Position newPosition = new Position(1337, 1337);

		PlayerManager.getSingleton().addPlayer(1);
		Player p = PlayerManager.getSingleton().getPlayerFromID(1);
		p.setPlayerPosition(startPosition);

		assertEquals(startPosition, p.getPlayerPosition());

		PlayerMovedMessage msg = new PlayerMovedMessage(playerID, newPosition);
		MovementMessageHandler handler = new MovementMessageHandler();

		handler.process(msg);
		while (ModelFacade.getSingleton().hasCommandsPending())
		{
			Thread.sleep(100);
		}

		assertEquals(newPosition, p.getPlayerPosition());
	}
}
