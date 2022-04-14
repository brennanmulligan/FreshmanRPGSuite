package communication.handlers;

import static org.junit.Assert.assertEquals;

import datatypes.PlayersForTest;
import org.junit.Before;
import org.junit.Test;

import communication.messages.PlayerAppearanceChangeMessage;
import datatypes.Crew;
import datatypes.Major;
import datatypes.Position;
import model.ClientModelFacade;
import model.ClientPlayerManager;
import model.CommandChangePlayerAppearance;

/**
 * Test the PlayerAppearanceMessageHnadler
 *
 */
public class PlayerAppearanceChangeMessageHandlerTest
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
	 * Test the type of Message
	 */
	@Test
	public void typeWeHandle()
	{
		PlayerAppearanceChangeMessageHandler h = new PlayerAppearanceChangeMessageHandler();
		assertEquals(PlayerAppearanceChangeMessage.class, h.getMessageTypeWeHandle());
	}


	/**
	 * Make sure command is called with the correct values
	 * @throws InterruptedException shouldn't
	 */
	/*@Test
	public void test() throws InterruptedException 
	{
		final ClientPlayerManager manager = ClientPlayerManager.getSingleton();
		PlayersForTest andy = PlayersForTest.ANDY;
		manager.initializePlayer(andy.getPlayerID(), andy.getPlayerName(), andy.getBodyID(), andy.getHatID(),
				andy.getPosition(), andy.getCrew(), andy.getMajor(), 3);
		PlayerAppearanceChangeMessage msg = new PlayerAppearanceChangeMessage(PlayersForTest.ANDY.getPlayerID(),
				PlayersForTest.ANDY.getBodyID(), PlayersForTest.ANDY.getHatID());
		PlayerAppearanceChangeMessageHandler handler = new PlayerAppearanceChangeMessageHandler();
		handler.process(msg);
		assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());
		
		CommandChangePlayerAppearance cmd =(CommandChangePlayerAppearance) ClientModelFacade.getSingleton().getNextCommand();
		
		assertEquals(PlayersForTest.ANDY.getPlayerID(), cmd.getPlayerID());
		assertEquals(PlayersForTest.ANDY.getBodyID(), cmd.getBodyID());
		assertEquals(PlayersForTest.ANDY.getHatID(), cmd.getHatID());
	}*/
}
