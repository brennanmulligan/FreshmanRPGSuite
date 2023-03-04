package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@GameTest("GameShared")
public class TerminalTextExitMessageTest
{

    @Test
    public void basic()
    {
        TerminalTextExitMessage msg = new TerminalTextExitMessage(42, false);
        assertEquals(42, msg.getRelevantPlayerID());

    }

}
