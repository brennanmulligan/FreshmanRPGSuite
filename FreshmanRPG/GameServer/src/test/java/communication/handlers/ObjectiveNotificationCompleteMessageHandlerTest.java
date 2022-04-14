package communication.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
public class ObjectiveNotificationCompleteMessageHandlerTest
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
	 * Test what message type we handle
	 */
	@Test
	public void testMessageWeHandle()
	{
		ObjectiveNotificationCompleteMessageHandler h = new ObjectiveNotificationCompleteMessageHandler();

		assertEquals(ObjectiveNotificationCompleteMessage.class, h.getMessageTypeWeHandle());
	}

	/**
	 * Test that ObjectiveNotificationCompleteMessageHandler creates
	 * CommandObjectiveNotificationComplete
	 *
	 * @throws InterruptedException awef
	 */
	@Test
	public void testProcessCreatesCommandObjectiveNotificationComplete() throws InterruptedException
	{
		PlayerManager.getSingleton().addPlayer(1);
		ObjectiveNotificationCompleteMessage msg = new ObjectiveNotificationCompleteMessage(
				PlayersForTest.JOHN.getPlayerID(), QuestsForTest.ONE_BIG_QUEST.getQuestID(),
				ObjectivesForTest.QUEST1_OBJECTIVE2.getObjectiveID());
		ObjectiveNotificationCompleteMessageHandler handler = new ObjectiveNotificationCompleteMessageHandler();
		handler.process(msg);

		assertTrue(ModelFacade.getSingleton().hasCommandsPending());
		while (ModelFacade.getSingleton().hasCommandsPending())
		{
			Thread.sleep(100);
		}

	}

}
