package model;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

/**
 * @author Merlin
 *
 */
public class CommandPrintAdventuresTest
{

	/**
	 * Testing what is in the file would be very difficult, so we just test that
	 * the file gets created. Hand checking is required if the contents of the
	 * file change
	 */
	@Test
	public void createsFile()
	{
		PDFAdventureWriterTest.buildAPlayerWithAdventures();
		File f = new File("test.pdf");
		f.delete();
		CommandPrintAdventures cmd = new CommandPrintAdventures("test.pdf");

		cmd.execute();
		f = new File("test.pdf");
		assertTrue(f.exists());
	}
}
