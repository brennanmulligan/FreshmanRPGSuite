package communication.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import communication.messages.QuestStateChangeMessage;
import datatypes.QuestStateEnum;
import model.ClientModelFacade;
import model.CommandQuestStateChange;

/**
 * @author Ryan
 *
 */
public class QuestStateChangeMessageHandlerTest 
{
	/**
	 * Reset the ModelFacade
	 */
	@Before
	public void setUp()
	{
		ClientModelFacade.resetSingleton();
		ClientModelFacade.getSingleton(true, true);
	}
	
	/**
	 * Test the type of Message that we expect
	 */
	@Test
	public void typeWeHandle()
	{
		QuestStateChangeMessageHandler h = new QuestStateChangeMessageHandler();
		assertEquals(QuestStateChangeMessage.class, h.getMessageTypeWeHandle());
	}
	
	/**
	 * Test that the handler messages handles the messages and creates
	 * a command
	 * @throws InterruptedException shouldn't
	 */
	@Test
	public void testMessageHandling() throws InterruptedException
	{
		QuestStateChangeMessageHandler h = new QuestStateChangeMessageHandler();
		QuestStateChangeMessage msg = new QuestStateChangeMessage(1, 2, "quest 1 title", "Quest 1", QuestStateEnum.AVAILABLE);
		h.process(msg);
		assertTrue(ClientModelFacade.getSingleton().hasCommandsPending());
		CommandQuestStateChange cmd = (CommandQuestStateChange)ClientModelFacade.getSingleton().getNextCommand();
		assertEquals(2, cmd.getQuestID());
		assertEquals("quest 1 title", cmd.getQuestTitle());
		assertEquals("Quest 1", cmd.getQuestDescription());
		assertEquals(QuestStateEnum.AVAILABLE, cmd.getQuestState());
	}

}
