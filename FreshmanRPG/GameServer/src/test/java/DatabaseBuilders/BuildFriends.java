package DatabaseBuilders;

import java.sql.SQLException;

import datasource.DatabaseException;
import datasource.FriendTableDataGatewayRDS;
import model.OptionsManager;
import datatypes.FriendEnum;

public class BuildFriends
{

	/**
	 * @param args
	 * @throws DatabaseException
	 * @throws SQLException
	 */
	public static void main(String[] args) throws DatabaseException, SQLException
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(false);
		OptionsManager.getSingleton().setUsingTestDB(true);
		createFriendTable();
	}

	/**
	 * Create a table of friends
	 *
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	private static void createFriendTable() throws SQLException, DatabaseException
	{
		FriendTableDataGatewayRDS.createTable();
		for (FriendEnum friend : FriendEnum.values())
		{
			FriendTableDataGatewayRDS.getInstance().createRow(friend.getId(), friend.getFriendID(), friend.getStatus());
		}
	}
}
