package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.StateAccumulator;
import edu.ship.engr.shipsim.communication.messages.DisplayTextMessage;
import edu.ship.engr.shipsim.model.reports.InteractableObjectTextReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Stephen Clabaugh, Jacob Knight Tests the functionality of
 * InteractableObjectTextMessagePacker
 */
@GameTest("GameServer")
public class InteractableObjectTextMessagePackerTest
{
    /**
     * Tests that the message packer packs the correct report type
     */
    @Test
    public void testReportsWePack()
    {
        InteractableObjectTextMessagePacker packer = new InteractableObjectTextMessagePacker();
        assertEquals(InteractableObjectTextReport.class, packer.getReportTypesWePack().get(0));
    }

    /**
     * Tests that the player ID is properly packed
     */
    @Test
    public void testPlayerIDIsCorrect()
    {
        int playerID = 1234;
        String text = "Test Message";
        InteractableObjectTextMessagePacker packer = new InteractableObjectTextMessagePacker();
        InteractableObjectTextReport report = new InteractableObjectTextReport(playerID, text);
        StateAccumulator stateAccumulator = new StateAccumulator(null);
        stateAccumulator.setPlayerId(playerID);
        packer.setAccumulator(stateAccumulator);

        DisplayTextMessage message = (DisplayTextMessage) packer.pack(report);

        assertEquals(message.getRelevantPlayerID(), report.getPlayerID());
    }

    /**
     * Tests that the player ID is properly packed
     */
    @Test
    public void testTextIsCorrect()
    {
        int playerID = 1234;
        String text = "Test Message";
        InteractableObjectTextMessagePacker packer = new InteractableObjectTextMessagePacker();
        InteractableObjectTextReport report = new InteractableObjectTextReport(playerID, text);
        StateAccumulator stateAccumulator = new StateAccumulator(null);
        stateAccumulator.setPlayerId(playerID);
        packer.setAccumulator(stateAccumulator);

        DisplayTextMessage message = (DisplayTextMessage) packer.pack(report);

        assertEquals(message.getText(), report.getText());
    }

    /**
     * Tests that the packer doesn't work for a player that isn't connected
     */
    @Test
    public void testIncorrectPlayerPacker()
    {
        int playerID = 1234;
        int incorrectID = 400;
        String text = "Test Message";
        InteractableObjectTextMessagePacker packer = new InteractableObjectTextMessagePacker();
        InteractableObjectTextReport report = new InteractableObjectTextReport(incorrectID, text);
        StateAccumulator stateAccumulator = new StateAccumulator(null);
        stateAccumulator.setPlayerId(playerID);
        packer.setAccumulator(stateAccumulator);

        packer.pack(report);

        assertNull(packer.pack(report));
    }

}
