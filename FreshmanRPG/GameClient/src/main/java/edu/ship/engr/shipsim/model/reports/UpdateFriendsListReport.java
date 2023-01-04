package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.dataDTO.FriendDTO;
import edu.ship.engr.shipsim.model.Report;

import java.util.ArrayList;

public class UpdateFriendsListReport implements Report
{

    private ArrayList<FriendDTO> friendList;

    /**
     * Report constructor
     *
     * @param friendList
     */
    public UpdateFriendsListReport(ArrayList<FriendDTO> friendList)
    {
        this.friendList = friendList;
    }

    /**
     * Getter for friendlist
     *
     * @return
     */
    public ArrayList<FriendDTO> getFriendList()
    {
        return friendList;
    }

}
