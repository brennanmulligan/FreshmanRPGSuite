package DatabaseBuilders;

import datasource.DatabaseException;
import datasource.FriendTableDataGateway;
import datatypes.FriendEnum;
import model.OptionsManager;

import java.sql.SQLException;

public class BuildFriends
{

    /**
     * Create a table of friends
     */
    private static void createFriendTable() throws DatabaseException
    {
        FriendTableDataGateway gateway =
                FriendTableDataGateway.getSingleton();
        FriendTableDataGateway.createTable();
        for (FriendEnum friend : FriendEnum.values())
        {
            gateway.createRow(friend.getId(), friend.getFriendID(), friend.getStatus());
        }
    }

    public static void main(String[] args) throws DatabaseException, SQLException
    {
        OptionsManager.getSingleton().setUsingTestDB(true);
        createFriendTable();
    }
}
