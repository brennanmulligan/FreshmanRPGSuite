package edu.ship.engr.shipsim.model.terminal;

import edu.ship.engr.shipsim.dataDTO.FriendDTO;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.FriendTableDataGateway;

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

        ArrayList<FriendDTO> friendDTO;

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

        /*
         * Builds the string list to return
         */
        for (FriendDTO relationship : friendDTO)
        {
            switch (relationship.getStatus())
            {
                case ACCEPTED:
                case REQUESTED:
                case PENDING:
                    friendString.append("> ").append(relationship.getFriendName())
                            .append(" - ").append(relationship.getStatus().toString())
                            .append("\n");
                    break;
                default:
                    friendString.append("> ").append(relationship.getFriendName())
                            .append(" - ").append("INVALID STATUS").append("\n");
            }
        }

        friendString.append("> End of List \n");

        return friendString.toString();
    }
}
