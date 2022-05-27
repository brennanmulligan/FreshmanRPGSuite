package model.reports;

import static org.junit.Assert.assertEquals;

import datasource.ServerSideTest;
import org.junit.Before;
import org.junit.Test;

import model.Player;
import model.PlayerManager;
import nl.jqno.equalsverifier.EqualsVerifier;
import datatypes.PlayersForTest;

/**
 * Tests the PlayerFinishedInitializingReport
 * @author Brad Olah
 */
public class PlayerFinishedInitializingReportTest extends ServerSideTest
{

	/**
	 * reset the necessary singletons
	 */
	@Before
	public void localSetUp()
	{
		PlayerManager.resetSingleton();
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
