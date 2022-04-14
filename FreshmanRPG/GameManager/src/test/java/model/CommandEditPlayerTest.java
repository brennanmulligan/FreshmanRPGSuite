package model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import dataDTO.PlayerDTO;
import datasource.DatabaseException;
import datatypes.Crew;
import datatypes.Major;
import model.reports.AllPlayersReport;

/**
 * @author David, Regi
 *
 */
public class CommandEditPlayerTest
{

	/**
	 * @throws Exception
	 *             an exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		OptionsManager.resetSingleton();
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
	}

	/**
	 * @throws DatabaseException
	 *             should not
	 */
	@Test
	public void testEditPlayer() throws DatabaseException
	{
		GameManagerPlayerManager gamePlayerManager = GameManagerPlayerManager.getInstance();
		PlayerDTO player = gamePlayerManager.getPlayers().get(3);

		CommandEditPlayer editCommand = new CommandEditPlayer(player.getPlayerID(), "test_appearance_type", 9000, 1234,
				Crew.OFF_BY_ONE, Major.ELECTRICAL_ENGINEERING, 10, "Regi", "pwa");

		editCommand.execute();
		GameManagerPlayerManager.resetSingleton();
		player = GameManagerPlayerManager.getInstance().getPlayers().get(3);

		assertEquals("Regi", player.getPlayerName());
		assertEquals("test_appearance_type", player.getAppearanceType());
		assertEquals(9000, player.getDoubloons());
		assertEquals(1234, player.getExperiencePoints());
		assertEquals(Crew.OFF_BY_ONE, player.getCrew());
		assertEquals(Major.ELECTRICAL_ENGINEERING, player.getMajor());
		assertEquals(10, player.getSection());
	}

	/**
	 * Test to make sure that a report is being generated and sent to listeners
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testSendsR() throws DatabaseException
	{
		MockQualifiedObserver observer = new MockQualifiedObserver(AllPlayersReport.class);
		GameManagerPlayerManager gamePlayerManager = GameManagerPlayerManager.getInstance();

		PlayerDTO player = gamePlayerManager.getPlayers().get(3);

		CommandEditPlayer editCommand = new CommandEditPlayer(player.getPlayerID(), "test_appearance_type", 9000, 1234,
				Crew.OFF_BY_ONE, Major.ELECTRICAL_ENGINEERING, 10, "Regi", "pwa");

		editCommand.execute();

		GameManagerPlayerManager.resetSingleton();
		ArrayList<PlayerDTO> playerList = GameManagerPlayerManager.getInstance().getPlayers();

		AllPlayersReport report = new AllPlayersReport(playerList);
		assertEquals(report.getPlayerInfo(), ((AllPlayersReport) observer.getReport()).getPlayerInfo());
	}

}
