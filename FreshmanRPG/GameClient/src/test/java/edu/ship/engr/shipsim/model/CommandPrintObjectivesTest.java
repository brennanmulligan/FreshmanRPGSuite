package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Merlin
 */
@GameTest("GameClient")
public class CommandPrintObjectivesTest
{

    /**
     * Testing what is in the file would be very difficult, so we just test that
     * the file gets created. Hand checking is required if the contents of the
     * file change
     */
    @Test
    public void createsFile()
    {
        PDFObjectiveWriterTest.buildAPlayerWithObjectives();
        File f = new File("test.pdf");
        f.delete();
        CommandPrintObjectives cmd = new CommandPrintObjectives("test.pdf");

        cmd.execute();
        f = new File("test.pdf");
        assertTrue(f.exists());
    }
}
