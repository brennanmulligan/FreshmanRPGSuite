package DatabaseBuilders;

import java.sql.SQLException;

import datasource.DatabaseException;
import datasource.FriendTableDataGatewayRDS;
import datasource.TableDataGatewayManager;
import model.OptionsManager;
import datatypes.FriendEnum;

public class BuildFriends
{

	public static void main(String[] args) throws DatabaseException, SQLException
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(false);
		OptionsManager.getSingleton().setUsingTestDB(true);
		createFriendTable();
	}

	/**
	 * Create a table of friends
	 *
	 */
	private static void createFriendTable() throws DatabaseException
	{
		FriendTableDataGatewayRDS gateway =
				(FriendTableDataGatewayRDS) TableDataGatewayManager.getSingleton().getTableGateway("Friend");
		FriendTableDataGatewayRDS.createTable();
		for (FriendEnum friend : FriendEnum.values())
		{
			gateway.createRow(friend.getId(), friend.getFriendID(), friend.getStatus());
		}
	}
}
