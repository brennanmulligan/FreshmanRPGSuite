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
 * @author rh5172
 *
 */
public class PlayerAppearanceChangeReportTest
{

	/**
	 * setUp method
	 */
	@Before
	public void setUp()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		QuestManager.resetSingleton();
	}

	/**
	 * Tests that we can create a PlayerAppearanceChangeReport and get its AppearanceType
	 * and playerID
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testCreateReport() throws DatabaseException
	{
		Player john = PlayerManager.getSingleton().addPlayer(1);
		PlayerAppearanceChangeReport report = new PlayerAppearanceChangeReport(john.getPlayerID(), john.getAppearanceType());
		assertEquals(john.getAppearanceType(), report.getAppearanceType());
		assertEquals(john.getPlayerID(), report.getPlayerID());
	}

	/**
	 * Make sure the equals contract is obeyed
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(PlayerAppearanceChangeReport.class).verify();
	}


}
