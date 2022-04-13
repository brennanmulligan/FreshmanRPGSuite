package model.terminal;

import datasource.DatabaseException;
import datasource.FriendTableDataGateway;
import datasource.FriendTableDataGatewayMock;
import datasource.FriendTableDataGatewayRDS;
import model.OptionsManager;

/**
 * Abstract class outlining the behavior of the different friend arguments
 *
 * @author Zachary Semanco, Christian C
 */
abstract class FriendBehavior
{
    public int playerID;
    public String result;

    /**
     * Holds the functionality for the different friend arguments
     *
     * @param playerID
     * @return The result of the command
     */
    protected abstract String execute(int playerID, String[] friends);

    FriendTableDataGateway getTheGateway()
    {
        if (OptionsManager.getSingleton().isUsingMockDataSource())
        {
            return FriendTableDataGatewayMock.getSingleton();
        }
        else
        {
            try
            {
                return FriendTableDataGatewayRDS.getInstance();
            }
            catch (DatabaseException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }
}
