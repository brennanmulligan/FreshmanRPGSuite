package communication.handlers;

import static org.junit.Assert.assertEquals;

import datatypes.PlayersForTest;
import org.junit.Before;
import org.junit.Test;

import communication.messages.KnowledgeChangedMessage;
import model.ClientModelFacade;
import model.CommandKnowledgePointsChanged;

/**
 * @author Matthew Croft
 * @author Emily Maust
 *
 */
public class KnowledgeChangedMessageHandlerTest
{
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
		KnowledgeChangedMessageHandler h = new KnowledgeChangedMessageHandler();
		assertEquals(KnowledgeChangedMessage.class, h.getMessageTypeWeHandle());
	}
	
	/**
	 * Testing to see if a command is queued after receiving a message
	 * @throws InterruptedException shouldn't
	 */
	@Test
	public void handleKnowledgePointsChangedMessage() throws InterruptedException
	{	
		int oldScore = PlayersForTest.JOHN.getKnowledgeScore();
		
		KnowledgeChangedMessage msg = new KnowledgeChangedMessage(PlayersForTest.JOHN.getPlayerID(), oldScore + 5, PlayersForTest.JOHN.getBuffPool() - 5);
		KnowledgeChangedMessageHandler h = new KnowledgeChangedMessageHandler();
		h.process(msg);
		assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());
		CommandKnowledgePointsChanged cmd = (CommandKnowledgePointsChanged) ClientModelFacade.getSingleton().getNextCommand();
		assertEquals(PlayersForTest.JOHN.getPlayerID(), cmd.getPlayerID());
		assertEquals(oldScore+5, cmd.getKnowledge());
		ClientModelFacade.getSingleton().emptyQueue();
	}
}
