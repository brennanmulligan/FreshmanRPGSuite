package edu.ship.engr.shipsim.DatabaseBuilders;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.FriendTableDataGateway;
import edu.ship.engr.shipsim.datatypes.FriendEnum;
import edu.ship.engr.shipsim.model.OptionsManager;

import java.sql.SQLException;

public class BuildTestFriends
{
    public static void main(String[] args) throws DatabaseException, SQLException
    {
        OptionsManager.getSingleton().setUsingTestDB(true);
        createFriendTable();
    }

    /**
     * Create a table of friends
     */
    private static void createFriendTable() throws DatabaseException
    {
        System.out.println("Building the Friend Table");
        FriendTableDataGateway.createTable();
        FriendTableDataGateway gateway = FriendTableDataGateway.getSingleton();

        ProgressBar bar = new ProgressBar(FriendEnum.values().length);
        for (FriendEnum friend : FriendEnum.values())
        {
            gateway.createRow(friend.getId(), friend.getFriendID(), friend.getStatus());

            bar.update();
        }
    }
}
