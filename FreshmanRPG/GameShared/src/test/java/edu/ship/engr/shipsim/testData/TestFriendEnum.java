package edu.ship.engr.shipsim.testData;

import edu.ship.engr.shipsim.datatypes.FriendEnum;
import edu.ship.engr.shipsim.datatypes.FriendStatusEnum;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * @author - Andrew M,  Christian C
 **/
public class TestFriendEnum
{

    @Test
    public void test()
    {

        assertEquals(1, FriendEnum.JOHNANDMERLIN.getId());
        assertEquals(FriendStatusEnum.PENDING, FriendEnum.JOHNANDMERLIN.getStatus());
        assertEquals(2, FriendEnum.JOHNANDMERLIN.getFriendID());


    }
}