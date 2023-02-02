package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Kevin Marek, Andrew Stake
 */
@GameTest("GameClient")
public class CommandItemPurchasedTest
{


    /**
     * Tests that the command can be created
     */
    @Test
    public void testInitialization() throws IOException
    {
        int id = 1;
        int amount = 100;
        String prizeFile = "randomPrize";
        File f = new File(prizeFile + ".pdf");
        CommandItemPurchased cmd = new CommandItemPurchased(id, amount, prizeFile, prizeFile);
        assertEquals(id, cmd.getPlayerID());
        assertEquals(amount, cmd.getPrice());
        assertEquals(prizeFile, cmd.getFileTitle());
        cmd.execute();
    }

    /**
     * Testing what is in the file would be very difficult, so we just test that
     * the file gets created. Hand checking is required if the contents of the
     * file change
     *
     */
    @Test
    public void createsFile() throws IOException
    {
        ThisClientsPlayer cp = ThisClientsPlayerTest.setUpThisClientsPlayer(PlayersForTest.JOHN);
        File f = new File("test.pdf");
        f.delete();
        CommandItemPurchased cmd = new CommandItemPurchased(PlayersForTest.MERLIN.getPlayerID(), 10, "test.pdf", "test");

        cmd.execute();
        f = new File("test.pdf");
        assertTrue(f.exists());
        assertTrue(f.delete());
    }
}
