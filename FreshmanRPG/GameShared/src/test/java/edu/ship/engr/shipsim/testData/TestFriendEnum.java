package edu.ship.engr.shipsim.testData;

import edu.ship.engr.shipsim.datatypes.FriendEnum;
import edu.ship.engr.shipsim.datatypes.FriendStatusEnum;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author - Andrew M,  Christian C
 **/
@GameTest("GameShared")
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