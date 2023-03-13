package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Denny Fleagle
 * @author Chris Roadcap
 * @author Ben Lehman
 */
@GameTest("GameShared")
public class RecievedTerminalTextMessageTest
{
    /**
     * Test the message gets created correctly
     */
    @Test
    public void testCreation()
    {
        //create string for terminal
        String result = "unknown";

        //Create message
        ReceiveTerminalTextMessage message = new ReceiveTerminalTextMessage(PlayersForTest.ANDY.getPlayerID(), false, result);

        assertEquals(PlayersForTest.ANDY.getPlayerID(), message.getRequestingPlayerID());

        assertEquals(result, message.getResultText());
    }

}
