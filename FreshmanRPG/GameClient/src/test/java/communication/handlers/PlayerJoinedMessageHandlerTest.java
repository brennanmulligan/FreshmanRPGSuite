package communication.handlers;

import static org.junit.Assert.assertEquals;

import datatypes.PlayersForTest;
import org.junit.Before;
import org.junit.Test;

import communication.messages.PlayerJoinedMessage;
import model.ClientModelFacade;
import model.CommandInitializePlayer;

/**
 * Make sure the message is handled properly
 * 
 * @author Merlin
 * 
 */
public class PlayerJoinedMessageHandlerTest
{

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
	 * Test the type of Message that we expect
	 */
	@Test
	public void typeWeHandle()
	{
		PlayerJoinedMessageHandler h = new PlayerJoinedMessageHandler();
		assertEquals(PlayerJoinedMessage.class, h.getMessageTypeWeHandle());
	}
	
	/**
	 * We should add the player to the player manager
	 * 
	 * @throws InterruptedException
	 *             shouldn't
	 */
	@Test
	public void test() throws InterruptedException
	{
		PlayerJoinedMessage msg = new PlayerJoinedMessage(2, PlayersForTest.MERLIN.name(),
				PlayersForTest.MERLIN.getAppearanceType(), PlayersForTest.MERLIN.getPosition(), PlayersForTest.MERLIN.getCrew(),
				PlayersForTest.MERLIN.getMajor(), PlayersForTest.MERLIN.getSection());
		PlayerJoinedMessageHandler handler = new PlayerJoinedMessageHandler();
		handler.process(msg);
		assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());
		
		CommandInitializePlayer cmd =(CommandInitializePlayer) ClientModelFacade.getSingleton().getNextCommand();
		
		assertEquals(PlayersForTest.MERLIN.getPlayerID(), cmd.getPlayerID());
		assertEquals(PlayersForTest.MERLIN.getPlayerName().toUpperCase(), cmd.getPlayerName());
		assertEquals(PlayersForTest.MERLIN.getAppearanceType(), cmd.getAppearanceType());
		assertEquals(PlayersForTest.MERLIN.getPosition(), cmd.getPosition());
		assertEquals(PlayersForTest.MERLIN.getCrew(), cmd.getCrew());
		assertEquals(PlayersForTest.MERLIN.getMajor(), cmd.getMajor());
		assertEquals(PlayersForTest.MERLIN.getSection(), cmd.getSection());
		
		
	}

}
