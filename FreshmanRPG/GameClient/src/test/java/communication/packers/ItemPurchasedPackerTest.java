package communication.packers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import communication.messages.ItemPurchasedMessage;
import model.reports.ItemPurchasedReport;

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
		
		assertEquals(playerID, msg.getPlayerID());
		assertEquals(price, msg.getPrice());
	}
	
	@Test
	public void testGetReportTypesWePack()
	{
		ItemPurchasedPacker packer = new ItemPurchasedPacker();
		assertEquals(ItemPurchasedReport.class, packer.getReportTypesWePack().get(0));
		
	}
}
