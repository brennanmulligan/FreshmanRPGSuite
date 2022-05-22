package model.terminal;

import datasource.*;
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
     * @return The result of the command
     */
    protected abstract String execute(int playerID, String[] friends);

    FriendTableDataGateway getTheGateway()
    {
        return (FriendTableDataGateway) TableDataGatewayManager.getSingleton().getTableGateway("Friend");

    }
}
