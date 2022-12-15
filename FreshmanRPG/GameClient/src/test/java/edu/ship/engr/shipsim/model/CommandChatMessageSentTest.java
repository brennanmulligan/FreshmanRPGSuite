package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datatypes.ChatTextDetails;
import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Matthew Kujawski and Dave Abrams
 * These set of test makes sure that the CommandChatMessageSent works properly.
 */
@GameTest("GameClient")
public class CommandChatMessageSentTest
{

    /**
     * This test makes sure that the command can properly break a String from the UI apart into message content and message type.
     */
    @Test
    public void testInstantiation()
    {
        String localMessage = "This is a local message";
        CommandChatMessageSent ccms = new CommandChatMessageSent(new ChatTextDetails(localMessage, ChatType.Local));
        assertTrue(ccms.getValidity());
        assertEquals(ChatType.Local, ccms.getType());
        assertEquals("This is a local message", ccms.getMessage());

        String zoneMsg = "/z test";
        CommandChatMessageSent ccms2 = new CommandChatMessageSent(new ChatTextDetails(zoneMsg, ChatType.Zone));
        assertEquals(ChatType.Zone, ccms2.getType());
    }


}
