package communication.handlers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import communication.messages.DisplayTextMessage;
import model.ClientModelFacade;
import model.CommandDisplayText;

/**
 * 
 * @author Andy Kim
 *
 */
public class DisplayTextMessageHandlerTest
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
		DisplayTextMessageHandler display = new DisplayTextMessageHandler();
		assertEquals(DisplayTextMessage.class, display.getMessageTypeWeHandle() );		
	}
	
	/**
	 *  We add command to the ModelFacade command queue 
	 *  and check they have right information in the command queue.
	 * @throws InterruptedException shouldn't
	 */
	@Test
	public void test() throws InterruptedException
	{
		DisplayTextMessageHandler display = new DisplayTextMessageHandler();
		DisplayTextMessage text = new DisplayTextMessage(1, "text");
		display.process(text);
		assertEquals(1,ClientModelFacade.getSingleton().getCommandQueueLength());
		
		CommandDisplayText cmd = (CommandDisplayText)ClientModelFacade.getSingleton().getNextCommand();
		assertEquals("text", cmd.getText());
		ClientModelFacade.getSingleton().emptyQueue();
		
	}

}
