package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Test;

import dataDTO.FriendDTO;
import datatypes.FriendStatusEnum;


/**
 *
 * @author Christian C, Andrew M
 *
 */
public abstract class FriendTableDataGatewayTest
{

	// gateway instance
	private FriendTableDataGateway gateway;
	//friendDBMock instance
	private final FriendDBMock friendDatabase = FriendDBMock.getSingleton();

	@After
	public void tearDown() throws DatabaseException, SQLException
	{
		if (gateway != null)
		{
			gateway.resetTableGateway();
		}
		if (friendDatabase != null)
		{
			friendDatabase.resetData();
		}
	}

	abstract FriendTableDataGateway getGatewaySingleton() throws DatabaseException;

	@Test
	public void isSingleton() throws DatabaseException
	{
		FriendTableDataGateway obj1 = getGatewaySingleton();
		FriendTableDataGateway obj2 = getGatewaySingleton();
		assertSame(obj1, obj2);
		assertNotNull(obj2);
	}

	/**
	 * Test get all friends
	 *
	 */
	@Test
	public void testGetAllFriends() throws DatabaseException
	{
		int id = 1;
		FriendTableDataGateway gateway = getGatewaySingleton();
		ArrayList<FriendDTO> friendListFromGateway = gateway.getAllFriends(id);

		assertEquals(friendListFromGateway.get(0).getPlayerID(), 1);
		assertEquals(friendListFromGateway.get(0).getFriendID(), 2);
		assertEquals(friendListFromGateway.get(1).getPlayerID(), 1);
		assertEquals(friendListFromGateway.get(1).getFriendID(), 3);

		assertEquals(friendListFromGateway.get(0).getPlayerName(), "John"); // John
		assertEquals(friendListFromGateway.get(0).getFriendName(), "Merlin"); //Merlin
		assertEquals(friendListFromGateway.get(0).getStatus(), FriendStatusEnum.PENDING); //Pending

		assertEquals(friendListFromGateway.get(1).getPlayerName(), "John"); // John
		assertEquals(friendListFromGateway.get(1).getFriendName(), "Nick"); //Nick
		assertEquals(friendListFromGateway.get(1).getStatus(), FriendStatusEnum.ACCEPTED); //Pending
	}

	/**
	 * test adding a friend to the FriendDBMock
	 */
	@Test
	public void testAddFriend() throws DatabaseException
	{
		FriendTableDataGateway gateway = getGatewaySingleton();
		ArrayList<FriendDTO> friendListFromGateway = gateway.getAllFriends(1);
		assertEquals(3, friendListFromGateway.size());
		friendDatabase.resetData();
		gateway.add(1, "Frank", FriendStatusEnum.PENDING);

		friendListFromGateway = gateway.getAllFriends(1);
		assertEquals(friendListFromGateway.size(), 4);


		assertEquals(friendListFromGateway.get(0).getPlayerID(), 1);
		assertEquals(friendListFromGateway.get(0).getFriendID(), 2);
		assertEquals(friendListFromGateway.get(1).getPlayerID(), 1);
		assertEquals(friendListFromGateway.get(1).getFriendID(), 3);

		assertEquals(friendListFromGateway.get(0).getPlayerName(), "John"); // John
		assertEquals(friendListFromGateway.get(0).getFriendName(), "Merlin"); //Merlin
		assertEquals(friendListFromGateway.get(0).getStatus(), FriendStatusEnum.PENDING); //Pending

		assertEquals(friendListFromGateway.get(1).getPlayerName(), "John"); // John
		assertEquals(friendListFromGateway.get(1).getFriendName(), "Nick"); //Nick
		assertEquals(friendListFromGateway.get(1).getStatus(), FriendStatusEnum.ACCEPTED); //Pending

		assertEquals(friendListFromGateway.get(2).getPlayerName(), "John"); // John
		assertEquals(friendListFromGateway.get(2).getFriendName(), "Frank"); //Nick
		assertEquals(friendListFromGateway.get(2).getFriendID(), 7); //Nick
		assertEquals(friendListFromGateway.get(2).getStatus(), FriendStatusEnum.PENDING); //Pending

//				gateway.add(7, "Merlin", FriendStatusEnum.PENDING);

		friendListFromGateway = gateway.getAllFriends(7);
		assertEquals(friendListFromGateway.get(0).getPlayerName(), "Frank"); // Frank
		assertEquals(friendListFromGateway.get(0).getFriendName(), "John"); //John
		assertEquals(friendListFromGateway.get(0).getStatus(), FriendStatusEnum.REQUESTED); //Requested
	}

	/**
	 * do the same test as add friend but frank accepts and we test that
	 */
	@Test
	public void testAcceptFriend() throws DatabaseException
	{
		FriendTableDataGateway gateway = getGatewaySingleton();
		ArrayList<FriendDTO> friendListFromGateway = gateway.getAllFriends(1);
		assertEquals(friendListFromGateway.size(), 3);
		gateway.resetTableGateway();
		gateway.add(1, "Frank", FriendStatusEnum.PENDING); //john adds frank

		friendListFromGateway = gateway.getAllFriends(1);
		assertEquals(friendListFromGateway.size(), 4);


		//TODO change order
		assertEquals(friendListFromGateway.get(0).getPlayerID(), 1);
		assertEquals(friendListFromGateway.get(0).getFriendID(), 2);
		assertEquals(friendListFromGateway.get(1).getPlayerID(), 1);
		assertEquals(friendListFromGateway.get(1).getFriendID(), 3);

		assertEquals(friendListFromGateway.get(0).getPlayerName(), "John"); // John
		assertEquals(friendListFromGateway.get(0).getFriendName(), "Merlin"); //Merlin
		assertEquals(friendListFromGateway.get(0).getStatus(), FriendStatusEnum.PENDING); //Pending

		assertEquals(friendListFromGateway.get(1).getPlayerName(), "John"); // John
		assertEquals(friendListFromGateway.get(1).getFriendName(), "Nick"); //Nick
		assertEquals(friendListFromGateway.get(1).getStatus(), FriendStatusEnum.ACCEPTED); //Pending

		assertEquals(friendListFromGateway.get(2).getPlayerName(), "John"); // John
		assertEquals(friendListFromGateway.get(2).getFriendName(), "Frank"); //Frank
		assertEquals(friendListFromGateway.get(2).getFriendID(), 7); //Frank
		assertEquals(friendListFromGateway.get(2).getStatus(), FriendStatusEnum.PENDING); //Pending

		//Frank to accept friend request from john
		gateway.accept(7, "John");
		friendListFromGateway = gateway.getAllFriends(1);
		assertEquals(friendListFromGateway.get(2).getStatus(), FriendStatusEnum.ACCEPTED); //Pending
		friendListFromGateway = gateway.getAllFriends(7);
		//these are for testing what we got in our logic
//				System.out.println("Player ID: " + friendListFromGateway.get(0).getPlayerName());
//				System.out.println("Friend ID: " + friendListFromGateway.get(0).getFriendName());
		assertEquals(friendListFromGateway.get(0).getPlayerName(), "Frank"); // Frank
		assertEquals(friendListFromGateway.get(0).getFriendName(), "John"); //John
		assertEquals(friendListFromGateway.get(0).getStatus(), FriendStatusEnum.ACCEPTED); //Pending
	}

	@Test(expected = Exception.class)
	public void testAddException() throws DatabaseException
	{
		FriendTableDataGateway gateway = getGatewaySingleton();
		ArrayList<FriendDTO> friendListFromGateway = gateway.getAllFriends(1);
		assertEquals(friendListFromGateway.size(), 3);
		gateway.add(1, "fffffffffffffffffffffff", FriendStatusEnum.PENDING); //add a friend that doesn't exist
	}

	@Test(expected = Exception.class)
	public void testAcceptException() throws DatabaseException
	{
		FriendTableDataGateway gateway = getGatewaySingleton();
		ArrayList<FriendDTO> friendListFromGateway = gateway.getAllFriends(1);
		assertEquals(friendListFromGateway.size(), 3);
		gateway.accept(1, "fffffffffff");

	}

	@Test
	public void testFriendCounter() throws DatabaseException
	{
		FriendTableDataGateway gateway = getGatewaySingleton();
		assertEquals(gateway.getFriendCounter(1), 1);
		//test if someone has no friends that it returns 0 not null
		assertEquals(gateway.getFriendCounter(8), 0);
		//make sure someone with all pending relationships still has zero friends
		//this based off the enum which when creating the DBTable is used to make the base table
		assertEquals(gateway.getFriendCounter(2), 0);
		//test to see if I accept a request does the count increase if i check again
		//uncomment below when gateway is refactored
		gateway.accept(2, "John");
		assertEquals(1, gateway.getFriendCounter(2));
		assertEquals(2, gateway.getFriendCounter(1));
	}
}
