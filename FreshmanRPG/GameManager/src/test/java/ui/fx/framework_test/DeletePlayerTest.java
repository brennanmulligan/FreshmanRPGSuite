package ui.fx.framework_test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import model.CommandAddPlayerInManager;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import dataDTO.PlayerDTO;
import datatypes.Crew;
import datatypes.Major;
import javafx.stage.Stage;
import manager.GameManagerFX;
import model.CommandAddPlayer;
import model.ModelFacade;
import model.OptionsManager;
import ui.fx.contentviews.PlayerContentView;
import ui.fx.framework.AlertBar;

/**
 * Tests regarding the deletion of players
 */
public class DeletePlayerTest extends ApplicationTest
{

	/**
	 * Starts the GameManager
	 */
	@Override
	public void start(Stage stage)
	{
		new GameManagerFX(new String[]{"--localhost", "--db=05"}).start(stage);
		OptionsManager.getSingleton().setUsingTestDB(true);
	}

	/**
	 * Setup for test class
	 * @throws Exception - if an error occurs
	 */
	@Before
	public void setUp() throws Exception
	{

	}

	/**
	 * Tests that the confirmation box shows up correctly
	 */
	@Test
	public void testDeleteButtonShowsConfirmation()
	{
		clickOn("#PlayerMenuButton");
		PlayerContentView.getInstance().getPlayersTable().getSelectionModel().select(0);
		clickOn("#DeleteButton");
		clickOn("#NoButton");
		assertEquals("DELETE PLAYER", AlertBar.getInstance().getMessage());
	}

	/**
	 * testing deleting player from the db
	 */
	@Test
	public void testDeleteSelectedButton()
	{

		clickOn("#PlayerMenuButton");
		PlayerDTO player = new PlayerDTO(0, "Shaun", "TEST", "TEST", 0, null, null, 0, Crew.OFF_BY_ONE, Major.OTHER, 2, new ArrayList<>());
		CommandAddPlayerInManager command = new CommandAddPlayerInManager
				(
						player.getPlayerName(),
						player.getPlayerPassword(),
						player.getCrew().ordinal(),
						player.getMajor().ordinal(),
						player.getSection()
				);

		ModelFacade.getSingleton().queueCommand(command);

		this.sleep(1000);

		int sizeOfTableBeforeDelete = PlayerContentView.getInstance().getPlayersTable().getItems().size();

		PlayerContentView.getInstance().getPlayersTable().getSelectionModel().select(PlayerContentView.getInstance().getPlayersTable().getItems().size() - 1);
		clickOn("#DeleteButton");
		clickOn("#YesButton");

		this.sleep(3000);

		assertEquals(sizeOfTableBeforeDelete - 1, PlayerContentView.getInstance().getPlayersTable().getItems().size());
	}

}
