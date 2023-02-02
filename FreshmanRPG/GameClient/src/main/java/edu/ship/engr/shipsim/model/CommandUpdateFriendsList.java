package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.communication.messages.InitializeThisClientsPlayerMessage;
import edu.ship.engr.shipsim.dataDTO.FriendDTO;

import java.util.ArrayList;

/**
 * Command to change the content of a client's friend list
 *
 * @author Kevin Marek, Zack Semanco
 */
public class CommandUpdateFriendsList extends Command
{

    ArrayList<FriendDTO> friendList;

    /**
     * Constructor using the initialize this clients player message
     *
     * @param message the initialize this clients player message
     */
    public CommandUpdateFriendsList(InitializeThisClientsPlayerMessage message)
    {
        this.friendList = message.getFriends();
    }

    /**
     * Constructor taking an arraylist of the player's friend list
     *
     * @param friendList the list of current friends
     */
    public CommandUpdateFriendsList(ArrayList<FriendDTO> friendList)
    {
        this.friendList = friendList;
    }


    /**
     * @return This commands friends list
     */
    public ArrayList<FriendDTO> getFriendList()
    {
        return friendList;
    }

    @Override
    void execute()
    {
        ThisClientsPlayer thisClientsPlayer = ClientPlayerManager.getSingleton().getThisClientsPlayer();
        thisClientsPlayer.sendCurrentFriendListReport(friendList);
    }

}
