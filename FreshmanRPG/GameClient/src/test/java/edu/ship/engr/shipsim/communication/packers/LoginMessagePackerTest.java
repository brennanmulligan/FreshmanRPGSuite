package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.LoginMessage;
import edu.ship.engr.shipsim.model.reports.LoginInitiatedReport;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author merlin
 */
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
