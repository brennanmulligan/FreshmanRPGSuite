package model;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import datatypes.PlayersForTest;
import org.junit.Test;

/**
 * 
 * @author Kevin Marek, Andrew Stake
 *
 */
public class CommandItemPurchasedTest
{


	/**
	 * Tests that the command can be created
	 * @throws IOException 
	 */
	@Test
	public void testInitialization() throws IOException
	{
		int id = 1;
		int amount = 100;
		String prizeFile = "randomPrize";
		File f = new File(prizeFile +".pdf");				
		CommandItemPurchased cmd = new CommandItemPurchased(id, amount, prizeFile, prizeFile);
		assertEquals(id, cmd.getPlayerID());
		assertEquals(amount, cmd.getPrice());
		assertEquals(prizeFile, cmd.getFileTitle());
		assertTrue(cmd.execute());
		f.delete();
	}
	
	/**
	 * Testing what is in the file would be very difficult, so we just test that
	 * the file gets created. Hand checking is required if the contents of the
	 * file change
	 * @throws IOException 
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
		f.delete();
	}
}
