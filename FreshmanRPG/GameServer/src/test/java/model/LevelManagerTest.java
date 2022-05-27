package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import datasource.ServerSideTest;
import org.junit.Test;

import dataDTO.LevelManagerDTO;
import datasource.LevelRecord;
import datatypes.LevelsForTest;

/**
 * @author Merlin
 *
 */
public class LevelManagerTest extends ServerSideTest
{

	/**
	 * Make sure the LevelManager is a singleton
	 */
	@Test
	public void isSingleton()
	{
		LevelManagerDTO lm1 = LevelManagerDTO.getSingleton();
		LevelManagerDTO lm2 = LevelManagerDTO.getSingleton();

		assertNotNull(lm1);
		assertNotNull(lm2);

		assertSame(lm1, lm2);

	}

	/**
	 * Test the LevelManager's getLevelForPoints method
	 */
	@Test
	public void getsRightRange()
	{
		LevelRecord expected = new LevelRecord(LevelsForTest.TWO.getDescription(), LevelsForTest.TWO.getLevelUpPoints(),
				LevelsForTest.TWO.getLevelUpMonth(), LevelsForTest.TWO.getLevelUpDayOfMonth());
		assertEquals(expected, LevelManagerDTO.getSingleton().getLevelForPoints(LevelsForTest.ONE.getLevelUpPoints()));
	}

	/**
	 * Test getting the last level with sending in the level up points for level
	 * three
	 */
	@Test
	public void getsLastLevel()
	{
		LevelRecord expected = new LevelRecord(LevelsForTest.FOUR.getDescription(),
				LevelsForTest.FOUR.getLevelUpPoints(), LevelsForTest.FOUR.getLevelUpMonth(),
				LevelsForTest.FOUR.getLevelUpDayOfMonth());
		assertEquals(expected, LevelManagerDTO.getSingleton().getLevelForPoints(LevelsForTest.THREE.getLevelUpPoints()));
	}
}
