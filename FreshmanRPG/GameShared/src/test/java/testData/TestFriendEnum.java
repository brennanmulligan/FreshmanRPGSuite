package testData;

import static org.junit.Assert.*;

import datatypes.FriendEnum;
import datatypes.FriendStatusEnum;
import org.junit.Test;


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