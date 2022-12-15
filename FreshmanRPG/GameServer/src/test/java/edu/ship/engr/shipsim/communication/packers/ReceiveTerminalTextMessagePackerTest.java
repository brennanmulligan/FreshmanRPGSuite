package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.StateAccumulator;
import edu.ship.engr.shipsim.communication.messages.ReceiveTerminalTextMessage;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.reports.ReceiveTerminalTextReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Denny Fleagle
 * @author Chris Roadcap
 */
@GameTest("GameServer")
public class ReceiveTerminalTextMessagePackerTest
{
    /**
     * Test the packing ability
     */
    @Test
    public void test()
    {
        int playerID = PlayersForTest.MERLIN.getPlayerID();

        //Create result text
        String resultText = "unknown atm";


        //Create report and packer using the created list
        ReceiveTerminalTextReport report = new ReceiveTerminalTextReport(playerID, resultText, "");
        ReceiveTerminalTextMessagePacker packer = new ReceiveTerminalTextMessagePacker();

        StateAccumulator stateAccumulator = new StateAccumulator(null);
        stateAccumulator.setPlayerId(playerID);
        packer.setAccumulator(stateAccumulator);

        //Get message from packer's pack() method, and name and map from message
        ReceiveTerminalTextMessage message = (ReceiveTerminalTextMessage) packer.pack(report);
        resultText = message.getResultText();

        //test result text is the same
        assertEquals("unknown atm", resultText);
    }

}
