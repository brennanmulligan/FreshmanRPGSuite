package communication.handlers;

import static org.junit.Assert.assertEquals;

import datatypes.PlayersForTest;
import model.OptionsManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import communication.messages.DoubloonsChangedMessage;
import model.ClientModelFacade;
import model.CommandDoubloonsChanged;

/**
 * @author Matthew Croft
 * @author Emily Maust
 *
 */
public class DoubloonsChangedMessageHandlerTest
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
		ClientModelFacade.getSingleton(true, true);
	}
	
	/**
	 * Tests that getTypeWeHandle method returns correct type.
	 */
	@Test
	public void testTypeWeHandle()
	{
		DoubloonsChangedMessageHandler h = new DoubloonsChangedMessageHandler();
		assertEquals(DoubloonsChangedMessage.class, h.getMessageTypeWeHandle());
	}
	
	/**
	 * Testing to see if a command is queued after receiving a message
	 * @throws InterruptedException shouldn't
	 */
	@Test
	public void handleDoubloonsChangedMessage() throws InterruptedException
	{	
		int oldScore = PlayersForTest.JOHN.getDoubloons();
		
		DoubloonsChangedMessage msg = new DoubloonsChangedMessage(PlayersForTest.JOHN.getPlayerID(), oldScore + 5, PlayersForTest.JOHN.getBuffPool() - 5);
		DoubloonsChangedMessageHandler h = new DoubloonsChangedMessageHandler();
		h.process(msg);
		assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());
		CommandDoubloonsChanged cmd = (CommandDoubloonsChanged) ClientModelFacade.getSingleton().getNextCommand();
		assertEquals(PlayersForTest.JOHN.getPlayerID(), cmd.getPlayerID());
		assertEquals(oldScore+5, cmd.getDoubloons());
		ClientModelFacade.getSingleton().emptyQueue();
	}
}
