package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Tests the creation of a pdf file
 *
 * @author Kevin Marek
 */
@GameTest("GameClient")
public class PDFPrizeWriterTest
{

    /**
     * This is really just a driver to generate a file - it doesn't "test"
     * anything. You have to look at test.pdf to see if it was built correctly
     *
     * @throws IOException
     */
    @Test
    public void testCanWritePDF() throws IOException
    {
        ThisClientsPlayer cp = ThisClientsPlayerTest.setUpThisClientsPlayer(PlayersForTest.JOHN);
        PDFPrizeWriter writer = new PDFPrizeWriter();
        writer.createPDFOfPurchasedPrize("fakeItem.pdf", 100, "fakeItem");
    }
}
