package model.reports;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import datasource.DatabaseException;
import model.OptionsManager;
import model.Player;
import model.PlayerManager;
import model.QuestManager;
import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * @author Matthew Croft
 *
 */
public class DoubloonsChangeReportTest
{

	/**
	 * Tests that we can create a DoubloonChangeReport and get its
	 * doubloons and playerID
	 *
	 */
	@Test
	public void testCreateReport()
	{
		Player john = PlayerManager.getSingleton().addPlayer(1);
		DoubloonChangeReport report = new DoubloonChangeReport(john.getPlayerID(),
				john.getDoubloons(), john.getBuffPool());
		assertEquals(john.getDoubloons(), report.getDoubloons());
		assertEquals(john.getBuffPool(), report.getBuffPool());
	}

	/**
	 * Make sure the equals contract is obeyed
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(DoubloonChangeReport.class).verify();
	}
}
