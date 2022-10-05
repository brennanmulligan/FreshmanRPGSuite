package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.KeyInputMessage;
import edu.ship.engr.shipsim.model.reports.ClientKeyInputSentReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests functionality for key input packer
 *
 * @author Ian Keefer & TJ Renninger
 */
@GameTest("GameClient")
public class KeyInputPackerTest
{

    /**
     * Tests for creation of a KeyInputPacker and packing messages
     */
    @Test
    public void testInitialization()
    {
        String input = "q";
        ClientKeyInputSentReport inputReport = new ClientKeyInputSentReport(input);

        KeyInputMessagePacker packer = new KeyInputMessagePacker();
        KeyInputMessage msg = (KeyInputMessage) packer.pack(inputReport);

        assertEquals(input, msg.getInput());
    }

}
