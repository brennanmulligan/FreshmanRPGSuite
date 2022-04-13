package datasource;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test for LevelRecord
 *
 * @author Merlin
 *
 */
public class LevelRecordTest
{

	/**
	 * Test the compare to method in Level Record because it implements
	 * Comparable<LevelRecord> Comparable only compares the experience points
	 * required to get past a level
	 */
	@Test
	public void testCompareTo()
	{
		LevelRecord a = new LevelRecord("a", 34, 0, 0);
		LevelRecord b = new LevelRecord("b", 35, 0, 0);
		LevelRecord c = new LevelRecord("b", 35, 0, 0);

		assertTrue(a.compareTo(b) < 0);
		assertTrue(b.compareTo(c) == 0);
		assertTrue(b.compareTo(a) > 0);
	}

}
