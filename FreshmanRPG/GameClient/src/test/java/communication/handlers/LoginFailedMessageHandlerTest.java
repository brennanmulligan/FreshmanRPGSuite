package communication.handlers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import communication.messages.LoginFailedMessage;
import model.ClientModelFacade;
import model.Command;
import model.CommandLoginFailed;

/**
 * @author Matt and Andy
 *
 */
public class LoginFailedMessageHandlerTest 
{

	
	/**
	 * Reset the ModelFacade
	 */
	@Before
	public void setUp()
	{
		ClientModelFacade.resetSingleton();
		ClientModelFacade.getSingleton(true, false);
	}
	
	/**
	 * Test the type of Message that we expect
	 */
	@Test
	public void typeWeHandle()
	{
		LoginFailedMessageHandler h = new LoginFailedMessageHandler();
		assertEquals(LoginFailedMessage.class, h.getMessageTypeWeHandle());
	}
	
	/**
	 * We should add a command to the ModelFacade command queue
	 * 
	 * @throws InterruptedException
	 * 				shouldn't
	 */
	@Test
	public void test() throws InterruptedException
	{
		LoginFailedMessageHandler handler = new LoginFailedMessageHandler();
		LoginFailedMessage msg = new LoginFailedMessage();
		handler.process(msg);
		assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());
		Command cmd = ClientModelFacade.getSingleton().getNextCommand();
		assertEquals(cmd.getClass(), CommandLoginFailed.class);
		ClientModelFacade.getSingleton().emptyQueue();
	}

}
