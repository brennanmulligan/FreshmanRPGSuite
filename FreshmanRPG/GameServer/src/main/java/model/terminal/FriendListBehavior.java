package model.terminal;

import dataDTO.FriendDTO;
import datasource.DatabaseException;
import datasource.FriendTableDataGateway;

import java.util.ArrayList;

/**
 * Class detailing the behavior of the 'friend list' command
 *
 * @author Zachary Semanco, Christian C.
 */
class FriendListBehavior extends FriendBehavior
{
    /**
     * Returns a list of the player's friends (pending and accepted)
     */
    @Override
    protected String execute(int playerID, String[] friends)
    {
        StringBuilder friendString = new StringBuilder();

        ArrayList<FriendDTO> friendDTO = null;

        FriendTableDataGateway gateway = getTheGateway();

        try
        {
            friendDTO = gateway.getAllFriends(playerID);
        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
            return "Error retrieving your friends\n";
        }

        friendString.append("> Friend Name - Status\n");

        /**
         * Builds the string list to return
         */
        for (FriendDTO relationship : friendDTO)
        {
            switch (relationship.getStatus())
            {
                case ACCEPTED:
                    friendString.append("> " + relationship.getFriendName() + " - " + relationship.getStatus().toString() + "\n");
                    break;
                case PENDING:
                    friendString.append("> " + relationship.getFriendName() + " - " + relationship.getStatus().toString() + "\n");
                    break;
                case REQUESTED:
                    friendString.append("> " + relationship.getFriendName() + " - " + relationship.getStatus().toString() + "\n");
                    break;
                default:
                    friendString.append("> " + relationship.getFriendName() + " - " + "INVALID STATUS" + "\n");
            }
        }

        friendString.append("> End of List \n");

        return friendString.toString();
    }
}
