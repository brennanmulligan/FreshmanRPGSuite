package model.reports;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import model.OptionsManager;
import model.Player;
import model.PlayerManager;
import nl.jqno.equalsverifier.EqualsVerifier;
import datatypes.PlayersForTest;

/**
 * Tests the PlayerFinishedInitializingReport
 * @author Brad Olah
 */
public class PlayerFinishedInitializingReportTest
{

	/**
	 * reset the necessary singletons
	 */
	@Before
	public void setUp()
	{
		PlayerManager.resetSingleton();
		OptionsManager.getSingleton().setUsingMocKDataSource(true);

	}

	/**
	 * make sure it gets built correctly
	 */
	@Test
	public void creation()
	{
		Player john = PlayerManager.getSingleton().addPlayer(1);

		PlayerFinishedInitializingReport report = new PlayerFinishedInitializingReport(
				john.getPlayerID(),
				john.getPlayerName(),
				john.getAppearanceType()
		);

		assertEquals(1, report.getPlayerID());
		assertEquals(PlayersForTest.JOHN.getPlayerName(), report.getPlayerName());
		assertEquals(PlayersForTest.JOHN.getAppearanceType(), report.getAppearanceType());
	}

	/**
	 * Make sure the equals contract is obeyed
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(PlayerFinishedInitializingReport.class).verify();
	}
}
