package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.dataDTO.FriendDTO;
import edu.ship.engr.shipsim.model.QualifiedObservableReport;

import java.util.ArrayList;

/**
 * @author Andrew McCoy
 */
public class CurrentFriendListReport implements QualifiedObservableReport
{


    private ArrayList<FriendDTO> friendList;

    /**
     * Report constructor
     *
     * @param friendList
     */
    public CurrentFriendListReport(ArrayList<FriendDTO> friendList)
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
