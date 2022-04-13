package ui.fx.framework_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import dataDTO.PlayerDTO;
import datasource.DatabaseException;
import datasource.PlayerTableDataGatewayRDS;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import manager.GameManagerFX;
import model.Command;
import model.CommandDeletePlayer;
import model.ModelFacade;
import model.OptionsManager;
import ui.fx.contentviews.PlayerContentView;

/**
 * Test file for @see ui.fx.contentview.PlayerContentView import functionality.
 * @author Christopher Boyer and Abe Loscher
 */
public class ImportPlayerTest extends ApplicationTest
{

	/**
	 * Start the game manager.
	 */
	@Override
	public void start(Stage stage)
	{
		new GameManagerFX(new String[]{"--localhost", "--db=04"}).start(stage);
		OptionsManager.getSingleton().setUsingTestDB(true);
	}

	/**
	 * Setup for test class.
	 * @throws Exception Exception
	 */
	@Before
	public void setUp() throws Exception
	{

	}

	/**
	 * Test that hitting the import button opens it and then you can escape out of it.
	 */
	@Test
	public void testImportButtonOpensFileChooser()
	{
		clickOn("#PlayerMenuButton");
		clickOn("#ImportButton");
		assertTrue(PlayerContentView.getInstance().getContainer().isActive());
		push(KeyCode.ESCAPE);
		assertFalse(PlayerContentView.getInstance().getContainer().isActive());
	}


	/**
	 * TODO implement when all the functionality is there.
	 * @throws DatabaseException - shouldn't
	 */
	@Test
	public void testImportButtonImportsSelectedFile() throws DatabaseException
	{
		ArrayList<PlayerDTO> players = PlayerTableDataGatewayRDS.getSingleton().retrieveAllPlayers();
		int numPlayersBefore = players.size();
		clickOn("#PlayerMenuButton");
		clickOn("#ImportButton");
		PlayerContentView.getInstance().getContainer().setSelectedFile(new File("tests/testdata/testPlayerData.csv"));
		push(KeyCode.ESCAPE);
		clickOn("#YesButton");
		sleep(2000);
		players = PlayerTableDataGatewayRDS.getSingleton().retrieveAllPlayers();
		int numPlayersAfter = players.size();
		final int addedPlayers = 2;
		assertEquals(numPlayersBefore + addedPlayers, numPlayersAfter);


		for (PlayerDTO player : players)
		{
			if (player.getPlayerName().equals("Truc"))
			{
				Command command = new CommandDeletePlayer(player.getPlayerID());
				ModelFacade.getSingleton().queueCommand(command);
			}

			if (player.getPlayerName().equals("Mohammed"))
			{
				Command command = new CommandDeletePlayer(player.getPlayerId());
				ModelFacade.getSingleton().queueCommand(command);
			}
		}

		sleep(500);
	}

}
