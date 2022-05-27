package model.reports;

import static org.junit.Assert.assertEquals;

import datasource.ServerSideTest;
import org.junit.Before;
import org.junit.Test;

import datasource.DatabaseException;
import model.Player;
import model.PlayerManager;
import nl.jqno.equalsverifier.EqualsVerifier;
import datatypes.PlayersForTest;

/**
 * @author Merlin
 *
 */
public class PlayerConnectionReportTest extends ServerSideTest
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
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void creation() throws DatabaseException
	{
		Player john = PlayerManager.getSingleton().addPlayer(1);

		PlayerConnectionReport report = new PlayerConnectionReport(
				john.getPlayerID(),
				john.getPlayerName(),
				john.getAppearanceType(),
				john.getPlayerPosition(),
				john.getCrew(),
				john.getMajor(),
				john.getSection(),
				john.getVanityItems()
		);

		assertEquals(1, report.getPlayerID());
		assertEquals(PlayersForTest.JOHN.getPlayerName(), report.getPlayerName());
		assertEquals(PlayersForTest.JOHN.getAppearanceType(), report.getAppearanceType());
		assertEquals(PlayersForTest.JOHN.getPosition(), report.getPosition());
		assertEquals(PlayersForTest.JOHN.getCrew(), report.getCrew());
		assertEquals(PlayersForTest.JOHN.getMajor(), report.getMajor());
		assertEquals(PlayersForTest.JOHN.getSection(), report.getSection());
	}

	/**
	 * Make sure the equals contract is obeyed
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(PlayerConnectionReport.class).verify();
	}
}
