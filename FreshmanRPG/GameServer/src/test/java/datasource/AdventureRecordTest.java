package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import criteria.CriteriaStringDTO;
import dataENUM.AdventureCompletionType;
import model.AdventureRecord;

/**
 * Tests a simple data transfer object that contains the information about one
 * adventure
 *
 * @author merlin
 *
 */
public class AdventureRecordTest
{
	/**
	 * Just make sure it holds and returns everything
	 */
	@Test
	public void constructAnAdventureRecord()
	{
		AdventureRecord record = new AdventureRecord(1, 1, "Adventure Description 1", 42, AdventureCompletionType.CHAT,
				new CriteriaStringDTO("Lab Instructor"));
		assertEquals(1, record.getAdventureID());
		assertEquals("Adventure Description 1", record.getAdventureDescription());
		assertEquals(1, record.getQuestID());
		assertEquals(42, record.getExperiencePointsGained());
		assertEquals(AdventureCompletionType.CHAT, record.getCompletionType());
		assertEquals(new CriteriaStringDTO("Lab Instructor"), record.getCompletionCriteria());
		assertFalse(record.isRealLifeAdventure());

	}

	/**
	 * Correctly calculates whether it is a real life adventure
	 */
	@Test
	public void detectsRealLife()
	{
		AdventureRecord record = new AdventureRecord(1, 1, "Adventure Description 1", 42,
				AdventureCompletionType.REAL_LIFE, new CriteriaStringDTO("Lab Instructor"));
		assertTrue(record.isRealLifeAdventure());
		record = new AdventureRecord(1, 1, "Adventure Description 1", 42, AdventureCompletionType.CHAT,
				new CriteriaStringDTO("Lab Instructor"));
		assertFalse(record.isRealLifeAdventure());
	}
}
