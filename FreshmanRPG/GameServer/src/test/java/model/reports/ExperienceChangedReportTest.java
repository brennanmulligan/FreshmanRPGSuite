package model.reports;

import static org.junit.Assert.assertEquals;

import datasource.ServerSideTest;
import org.junit.Before;
import org.junit.Test;

import dataDTO.LevelManagerDTO;
import datasource.LevelRecord;
import model.Player;
import model.PlayerManager;
import model.QuestManager;
import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Tests the ExperienceChangedReport class functionality
 *
 * @author Olivia
 * @author LaVonne
 */
public class ExperienceChangedReportTest extends ServerSideTest
{
	/**
	 * reset the necessary singletons
	 */
	@Before
	public void localSetUp()
	{
		QuestManager.resetSingleton();
	}

	/**
	 * Tests that we can create a ExperienceChangedReport and get its experience
	 * points and playerID
	 */
	@Test
	public void testCreateReport()
	{
		Player john = PlayerManager.getSingleton().addPlayer(1);
		LevelRecord expected = LevelManagerDTO.getSingleton().getLevelForPoints(john.getExperiencePoints());
		ExperienceChangedReport report = new ExperienceChangedReport(john.getPlayerID(), john.getExperiencePoints(),
				expected);
		assertEquals(john.getExperiencePoints(), report.getExperiencePoints());
		assertEquals(expected, report.getRecord());
	}

	/**
	 * Make sure the equals contract is obeyed
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(ExperienceChangedReport.class).verify();
	}
}
