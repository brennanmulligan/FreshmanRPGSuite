package edu.ship.engr.shipsim.datatypes;

/**
 * @author Christian C, Andrew M
 * @description -  enum for friends list mock and other hard coded datasources to use for friends list
 */
public enum FriendEnum
{

    JOHNANDMERLIN(1, 2, FriendStatusEnum.PENDING), //JOHN and MERLIN

    JOHNANDNICK(1, 3, FriendStatusEnum.ACCEPTED), // JOHN AND NICK

    MERLINANDNICK(2, 3, FriendStatusEnum.PENDING), // MERLIN and NICK

    NICKANDJOSH(3, 4, FriendStatusEnum.ACCEPTED), //NICK and JOSH

    JOSHANDJOHN(4, 1, FriendStatusEnum.PENDING); //JOSH AND JOHN

    private int id;
    private int friendID;
    private FriendStatusEnum status;


    FriendEnum(int id, int friendID, FriendStatusEnum status)
    {
        this.id = id;
        this.friendID = friendID;
        this.status = status;

    }

    public int getId()
    {
        return id;
    }

    public int getFriendID()
    {
        return friendID;
    }

    public FriendStatusEnum getStatus()
    {
        return status;
    }


}