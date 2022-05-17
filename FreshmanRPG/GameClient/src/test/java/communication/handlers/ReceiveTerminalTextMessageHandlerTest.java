package communication.handlers;

import static org.junit.Assert.assertEquals;

import datatypes.PlayersForTest;
import model.OptionsManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import communication.messages.ReceiveTerminalTextMessage;
import model.ClientModelFacade;
import model.CommandRecieveTerminalResponse;


/**
 * 
 * @author Denny Fleagle 
 * @author Ben Lehman
 *
 */
public class ReceiveTerminalTextMessageHandlerTest
{
	@BeforeClass
	public static void hardReset()
	{
		OptionsManager.getSingleton().setTestMode(true);
	}
	/**
	 * Reset the ModelFacade
	 */
	@Before
	public void reset()
	{
		ClientModelFacade.resetSingleton();
		ClientModelFacade.getSingleton(true, false);
	}

	/**
	 * test the handler process method
	 * @throws InterruptedException interrupted exception
	 */
	@Test
	public void testHandlerProcess() throws InterruptedException
	{
		String testString = "Test : String";
		ReceiveTerminalTextMessage msg = new ReceiveTerminalTextMessage(PlayersForTest.MERLIN.getPlayerID(), testString);
		ReceiveTerminalTextMessageHandler handler = new ReceiveTerminalTextMessageHandler();
		handler.process(msg);
		assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());
		
		CommandRecieveTerminalResponse cmd =(CommandRecieveTerminalResponse) ClientModelFacade.getSingleton().getNextCommand();
		
		assertEquals(PlayersForTest.MERLIN.getPlayerID(), cmd.getPlayerID());
		assertEquals(testString, cmd.getTerminalResult());
	}
	
	/**
	 * Test the type of Message that we expect
	 */
	@Test
	public void typeWeHandle()
	{
		ReceiveTerminalTextMessageHandler h = new ReceiveTerminalTextMessageHandler();
		assertEquals(ReceiveTerminalTextMessage.class, h.getMessageTypeWeHandle());
	}
}
