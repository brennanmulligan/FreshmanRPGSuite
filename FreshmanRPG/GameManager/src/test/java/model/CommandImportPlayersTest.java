package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import dataDTO.PlayerDTO;
import datasource.AdventureStateTableDataGatewayMock;
import datasource.DatabaseException;
import datasource.PlayerConnectionRowDataGatewayMock;
import datasource.PlayerLoginRowDataGatewayMock;
import datasource.PlayerRowDataGatewayMock;
import datasource.QuestStateTableDataGatewayMock;
import model.reports.AllPlayersReport;
import model.reports.ErrorReport;

/**
 * Tests that @see model.CommandImportPlayer behaves as expected.
 *
 * @author Abe Loscher and Christopher Boyer
 */
public class CommandImportPlayersTest
{

	private final String filePath = "testdata/testPlayerData.csv";


	/**
	 * Set up the test setting.
	 * @throws DatabaseException shouldn't
	 */
	@Before
	public void setUp() throws DatabaseException
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		new PlayerRowDataGatewayMock().resetData();
		new PlayerLoginRowDataGatewayMock().resetData();
		QuestStateTableDataGatewayMock.getSingleton().resetData();
		AdventureStateTableDataGatewayMock.getSingleton().resetData();
		QuestManager.resetSingleton();
		new PlayerConnectionRowDataGatewayMock(1).resetData();
		PlayerManager.resetSingleton();
	}

	/**
	 * Test that players can be imported.
	 * @throws DatabaseException - shouldn't
	 */
	@Test
	public void testImportPlayersNormal() throws DatabaseException
	{

		File file = new File(filePath);
		CommandImportPlayer command = new CommandImportPlayer(file);
		boolean result = command.execute();
		if (!result)
		{
			fail();
		}

		final int firstPlayerCrew = 1;
		final int firstPlayerMajor = 1;
		final int firstPlayerSectionId = 1;
		final String firstPlayerName = "Mohammed";
		boolean firstPlayerFound = false;

		final int secondPlayerCrew = 2;
		final int secondPlayerMajor = 2;
		final int secondPlayerSectionId = 2;
		final String secondPlayerName = "Truc";
		boolean secondPlayerFound = false;

		for (PlayerDTO player : GameManagerPlayerManager.getInstance().getPlayers())
		{
			if (player.getPlayerName().equals(firstPlayerName))
			{
				firstPlayerFound = true;
				assertEquals(player.getSection(), firstPlayerSectionId);
				assertEquals(player.getMajor().getID(), firstPlayerMajor);
				assertEquals(player.getCrew().getID(), firstPlayerCrew);
			}

			if (player.getPlayerName().equals(secondPlayerName))
			{
				secondPlayerFound = true;
				assertEquals(player.getSection(), secondPlayerSectionId);
				assertEquals(player.getMajor().getID(), secondPlayerMajor);
				assertEquals(player.getCrew().getID(), secondPlayerCrew);
			}
		}

		assertTrue(firstPlayerFound & secondPlayerFound);
	}

	/**
	 * Tests that the appropriate report is sent after importing.
	 * @throws DatabaseException - shouldn't
	 */
	@Test
	public void testReport() throws DatabaseException
	{
		MockQualifiedObserver mqo = new MockQualifiedObserver(AllPlayersReport.class);
		CommandImportPlayer cmd = new CommandImportPlayer(new File(filePath));
		cmd.execute();
		assertNotNull(mqo.getReport());
		assertTrue(mqo.getReport() instanceof AllPlayersReport);
	}

	/**
	 * Test that sending a file missing columns causes an exception.
	 */
	@Test
	public void testSendsInvalidInputReportWhenTheCSVFileIsMissingAColumn()
	{
		MockQualifiedObserver mqo = new MockQualifiedObserver(ErrorReport.class);
		CommandImportPlayer command = new CommandImportPlayer(new File("tests/testdata/badTestPlayerData.csv"));
		command.execute();
		assertNotNull(mqo.getReport());
		assertTrue(mqo.getReport() instanceof ErrorReport);
	}
}
