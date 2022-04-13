package datasource;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

import datatypes.PlayerScoreRecord;

/**
 * @author Merlin
 *
 */
public class PlayerScoreRecordTest
{

	/**
	 * It is just a data transfer object. Make sure it stores the stuff it is
	 * supposed to store
	 */
	@Test
	public void test()
	{
		PlayerScoreRecord actual = new PlayerScoreRecord("Me", 1492);
		assertEquals("Me", actual.getPlayerName());
		assertEquals(1492, actual.getExperiencePoints());
	}

	/**
	 * make sure things sort in the correct order
	 */
	@Test
	public void comparable()
	{
		ArrayList<PlayerScoreRecord> data = new ArrayList<>();
		data.add(new PlayerScoreRecord("Second", 14));
		data.add(new PlayerScoreRecord("First", 15));
		Collections.sort(data);
		assertEquals(new PlayerScoreRecord("First", 15), data.get(0));
	}

}
