package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.LoginMessage;
import edu.ship.engr.shipsim.model.reports.LoginInitiatedReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author merlin
 */
@GameTest("GameClient")
public class LoginMessagePackerTest
{

    /**
     * Just make sure the right stuff goes into the message when we pack it
     */
    @Test
    public void test()
    {
        LoginMessagePacker packer = new LoginMessagePacker();
        LoginMessage msg = (LoginMessage) packer.pack(new LoginInitiatedReport("harry",
                "elizabeth"));
        assertEquals("harry", msg.getPlayerName());
        assertEquals("elizabeth", msg.getPassword());
    }

}
