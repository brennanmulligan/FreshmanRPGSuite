package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

import dataDTO.LevelManagerDTO;
import datasource.DatabaseException;
import datasource.LevelRecord;
import datatypes.LevelsForTest;

/**
 * @author Merlin
 *
 */
public class LevelManagerTest
{
	/**
	 * Reset the necessary singletons
	 */
	@Before
	public void setUp()
	{
		OptionsManager.resetSingleton();
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
	}

	/**
	 * Make sure the LevelManager is a singleton
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void isSingleton() throws DatabaseException
	{
		LevelManagerDTO lm1 = LevelManagerDTO.getSingleton();
		LevelManagerDTO lm2 = LevelManagerDTO.getSingleton();

		assertNotNull(lm1);
		assertNotNull(lm2);

		assertSame(lm1, lm2);

	}

	/**
	 * Test the LevelManager's getLevelForPoints method
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void getsRightRange() throws DatabaseException
	{
		LevelRecord expected = new LevelRecord(LevelsForTest.TWO.getDescription(), LevelsForTest.TWO.getLevelUpPoints(),
				LevelsForTest.TWO.getLevelUpMonth(), LevelsForTest.TWO.getLevelUpDayOfMonth());
		assertEquals(expected, LevelManagerDTO.getSingleton().getLevelForPoints(LevelsForTest.ONE.getLevelUpPoints()));
	}

	/**
	 * Test getting the last level with sending in the level up points for level
	 * three
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void getsLastLevel() throws DatabaseException
	{
		LevelRecord expected = new LevelRecord(LevelsForTest.FOUR.getDescription(),
				LevelsForTest.FOUR.getLevelUpPoints(), LevelsForTest.FOUR.getLevelUpMonth(),
				LevelsForTest.FOUR.getLevelUpDayOfMonth());
		assertEquals(expected, LevelManagerDTO.getSingleton().getLevelForPoints(LevelsForTest.THREE.getLevelUpPoints()));
	}
}
