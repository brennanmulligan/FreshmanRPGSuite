package edu.ship.engr.shipsim.communication.messages;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TerminalTextExitMessageTest
{

    @Test
    public void basic()
    {
        TerminalTextExitMessage msg = new TerminalTextExitMessage(42);
        assertEquals(42, msg.getPlayerID());

    }

}
