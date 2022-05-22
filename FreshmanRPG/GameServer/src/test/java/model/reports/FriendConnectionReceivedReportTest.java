package model.reports;

import static org.junit.Assert.*;

import datasource.*;
import org.junit.Before;
import org.junit.Test;

import model.OptionsManager;
import nl.jqno.equalsverifier.EqualsVerifier;


/**
 * @author Joshua Wood , Evan Reese
 * 	Making sure that Friend Received Report is working correctly
 *
 */
public class FriendConnectionReceivedReportTest
{
	@Before
	public void setup()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		new FriendDBMock();
	}

	@Test
	public void test() throws DatabaseException
	{

		FriendTableDataGateway gateway =
				(FriendTableDataGateway) TableDataGatewayManager.getSingleton()
						.getTableGateway(
								"Friend");

		int senderID = gateway.accept(2, "John");
		FriendConnectionReceivedReport accepted = new FriendConnectionReceivedReport(senderID, 2);
		assertEquals(accepted.getSenderID(), gateway.accept(2, "John"));
		assertEquals(accepted.getReceiverID(), 2);
	}

	/**
	 * Make sure the equals contract is obeyed
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(FriendConnectionReceivedReport.class).verify();
	}
}