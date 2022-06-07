package communication.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import datatypes.PlayersForTest;
import model.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import communication.messages.OtherPlayerMovedMessage;
import datatypes.Position;

/**
 * @author Andrew
 *
 *         Test to see if MovementMessagHandler properly works.
 */
public class OtherPlayerMovedMessageHandlerTest
{
	@BeforeClass
	public static void hardReset()
	{
		OptionsManager.getSingleton().setTestMode(true);
	}
	/**
	 * reset the singletons and tell the model we are running headless
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
		OtherPlayerMovedMessageHandler h = new OtherPlayerMovedMessageHandler();
		assertEquals(OtherPlayerMovedMessage.class, h.getMessageTypeWeHandle());
	}

	/**
	 * Tests to see if the command is built correctly, and added to the Facade.
	 *
	 * @throws InterruptedException
	 *             shouldn't
	 */
	@Test
	public void engineNotified() throws InterruptedException
	{
		MapManager.getSingleton().changeToNewFile("simple.tmx", "TestEngineNotified in " +
				"Other PlayerMovedMessageHandler" );
		ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.MATT);
		ClientModelTestUtilities.setUpOtherPlayerForTest(PlayersForTest.MERLIN);

		Position p = new Position(1, 1);
		OtherPlayerMovedMessage msg = new OtherPlayerMovedMessage(
				PlayersForTest.MATT.getPlayerID(), p);
		OtherPlayerMovedMessageHandler handler = new OtherPlayerMovedMessageHandler();
		handler.process(msg);
		assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());

		CommandClientMovePlayer cmd = (CommandClientMovePlayer)ClientModelFacade.getSingleton().getNextCommand();
		assertEquals(p, cmd.getPosition());
		assertFalse(cmd.isTeleporting());
		assertEquals(PlayersForTest.MATT.getPlayerID(), cmd.getPlayerID());
		ClientModelFacade.getSingleton().emptyQueue();
	}

}
