package communication.handlers;

import static org.junit.Assert.*;

import datasource.ServerSideTest;
import org.junit.Before;
import org.junit.Test;

import communication.messages.ItemPurchasedMessage;
import model.ModelFacade;
import model.Player;
import model.PlayerManager;

/**
 * @author Josh Wood
 */
public class ItemPurchasedHandlerTest extends ServerSideTest
{

	/**
	 * Reset the PlayerManager
	 */
	@Before
	public void reset()
	{
		PlayerManager.resetSingleton();
		ModelFacade.resetSingleton();
	}

	/**
	 * Testing getTypeWeHandled method returns the correct type
	 .	 */
	@Test
	public void testTestTypeWeHandle()
	{

		ItemPurchasedHandler j = new ItemPurchasedHandler();
		assertEquals(ItemPurchasedMessage.class, j.getMessageTypeWeHandle());

	}

	/**
	 * checking and seeing if points have been spent by a player or not
	 */
	@Test
	public void doubloonsSpent() throws InterruptedException
	{
		int playerID = 7;
		int price = 2;
		int startingScore = 100;

		PlayerManager.getSingleton().addPlayer(7);
		Player p = PlayerManager.getSingleton().getPlayerFromID(7);
		p.setDoubloons(startingScore);

		assertEquals(startingScore, p.getDoubloons());

		ItemPurchasedMessage msg = new ItemPurchasedMessage(playerID, price);
		ItemPurchasedHandler handler = new ItemPurchasedHandler();

		handler.process(msg);
		int count = 0;
		while (count < 10 && ModelFacade.getSingleton().hasCommandsPending())
		{
			Thread.sleep(100);
			count++;
		}
		assertTrue("ModelFacade didn't process our command", count < 10);
		assertEquals(startingScore - price, p.getQuizScore());


	}
}