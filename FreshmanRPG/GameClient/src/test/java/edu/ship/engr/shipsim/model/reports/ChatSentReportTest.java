package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Dave
 * <p>
 * Make sure that the ChatSentReport behaves properly.
 */
@GameTest("GameClient")
public class ChatSentReportTest
{
    /**
     * Make sure that the report properly holds the information it was given.
     */
    @Test
    public void testInit()
    {
        String text = "Hello world";
        int name = 16;
        int receiver = 0;
        Position loc = new Position(1, 1);
        ChatType type = ChatType.Local;

        ChatSentReport report = new ChatSentReport(name, receiver, text, loc, type);

        assertEquals(text, report.getMessage());
        assertEquals(name, report.getSenderID());
        assertEquals(receiver, report.getReceiverID());
        assertEquals(loc, report.getPosition());
        assertEquals(type, report.getType());
    }
}