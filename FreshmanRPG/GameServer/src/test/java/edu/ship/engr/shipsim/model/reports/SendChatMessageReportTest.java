package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datasource.ServerSideTest;
import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.datatypes.Position;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Dave
 * <p>
 * Make sure that the SendChatMessageReport behaves properly.
 */
public class SendChatMessageReportTest extends ServerSideTest
{
    /**
     * The report should correctly remember the information it was given.
     */
    @Test
    public void testInit()
    {
        String msg = "Test message";
        int senderID = 42;
        Position pos = new Position(0, 1);
        ChatType type = ChatType.Local;

        ChatMessageReceivedReport report = new ChatMessageReceivedReport(senderID, 0, msg, pos, type);

        assertEquals(msg, report.getChatText());
        assertEquals(senderID, report.getSenderID());
        assertEquals(pos, report.getPosition());
        assertEquals(type, report.getType());
    }

    /**
     * Make sure the equals contract is obeyed
     */
    @Test
    public void equalsContract()
    {
        EqualsVerifier.forClass(ChatMessageReceivedReport.class).verify();
    }
}
