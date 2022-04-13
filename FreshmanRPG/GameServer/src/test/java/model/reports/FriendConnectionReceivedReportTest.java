package model.reports;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import datasource.DatabaseException;
import datasource.FriendDBMock;
import datasource.FriendTableDataGateway;
import datasource.FriendTableDataGatewayMock;
import model.OptionsManager;
import nl.jqno.equalsverifier.EqualsVerifier;


/**
 * @author Joshua Wood , Evan Reese
 * 	Making sure that Friend Received Report is working correctly
 *
 */
public class FriendConnectionReceivedReportTest
{

	private FriendTableDataGateway gateway;


	@Before
	public void setup()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		new FriendDBMock();
	}

	/**
	 * @throws DatabaseException
	 */
	@Test
	public void test() throws DatabaseException
	{

		gateway = FriendTableDataGatewayMock.getSingleton();

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