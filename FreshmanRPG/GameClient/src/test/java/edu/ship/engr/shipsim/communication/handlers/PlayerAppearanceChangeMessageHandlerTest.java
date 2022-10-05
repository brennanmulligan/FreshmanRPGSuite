package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.PlayerAppearanceChangeMessage;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetClientModelFacade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test the PlayerAppearanceMessageHnadler
 */
@GameTest("GameClient")
@ResetClientModelFacade
public class PlayerAppearanceChangeMessageHandlerTest
{
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
		ModelFacadeTestHelper.waitForCommandComplete(10,
		    "PlayerAppearanceChangeMessageHandlerTest");
		CommandChangePlayerAppearance cmd =(CommandChangePlayerAppearance) ClientModelFacade.getSingleton().getNextCommand();
		
		assertEquals(PlayersForTest.ANDY.getPlayerID(), cmd.getPlayerID());
		assertEquals(PlayersForTest.ANDY.getBodyID(), cmd.getBodyID());
		assertEquals(PlayersForTest.ANDY.getHatID(), cmd.getHatID());
	}*/
}
