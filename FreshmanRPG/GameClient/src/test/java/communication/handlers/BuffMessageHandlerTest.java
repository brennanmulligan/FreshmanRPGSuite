package communication.handlers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import communication.messages.BuffMessage;
import model.ClientModelFacade;
import model.CommandDisplayText;

/**
 * @author Emmanuel Douge, Elisabeth Ostrow
 */
public class BuffMessageHandlerTest
{
	/**
	 * Reset the ModelFacade
	 */
	@Before
	public void setup()
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
		BuffMessageHandler display = new BuffMessageHandler();
		assertEquals(BuffMessage.class, display.getMessageTypeWeHandle());

	}

	/**
	 * We add command to the ModelFacade command queue and check they have right
	 * information in the command queue.
	 * 
	 * @throws InterruptedException shouldn't
	 */
	@Test
	public void test() throws InterruptedException
	{
		BuffMessageHandler display = new BuffMessageHandler();
		BuffMessage text = new BuffMessage(1, 10);
		display.process(text);
		assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());

		CommandDisplayText cmd = (CommandDisplayText) ClientModelFacade.getSingleton().getNextCommand();
		assertEquals("You have received 10 Rec Center Bonus Points.", cmd.getText());
		ClientModelFacade.getSingleton().emptyQueue();

	}

}