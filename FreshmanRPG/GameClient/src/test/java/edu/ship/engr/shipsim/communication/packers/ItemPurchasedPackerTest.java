package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.ItemPurchasedMessage;
import edu.ship.engr.shipsim.model.reports.ItemPurchasedReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@GameTest("GameClient")
public class ItemPurchasedPackerTest
{

    @Test
    public void testPacking()
    {
        int playerID = 1;
        int price = 10;

        ItemPurchasedReport report = new ItemPurchasedReport(playerID, price);
        ItemPurchasedPacker packer = new ItemPurchasedPacker();
        ItemPurchasedMessage msg = (ItemPurchasedMessage) packer.pack(report);

        assertEquals(playerID, msg.getRelevantPlayerID());
        assertEquals(price, msg.getPrice());
    }

    @Test
    public void testGetReportTypesWePack()
    {
        ItemPurchasedPacker packer = new ItemPurchasedPacker();
        assertEquals(ItemPurchasedReport.class, packer.getReportTypesWePack().get(0));

    }
}
