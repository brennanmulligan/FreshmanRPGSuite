package ui.fx.framework_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import dataDTO.PlayerDTO;
import datasource.DatabaseException;
import datasource.PlayerTableDataGatewayRDS;
import datatypes.Crew;
import datatypes.Major;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import manager.GameManagerFX;
import model.CommandDeletePlayer;
import model.ModelFacade;
import model.OptionsManager;
import ui.fx.dialogues.AddPlayerModal;

/**
 * Tests the GUI aspects of adding players
 *
 */
public class AddPlayerTest extends ApplicationTest
{

	/**
	 * @see org.testfx.framework.junit.ApplicationTest#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage stage)
	{
		new GameManagerFX(new String[]{"--localhost", "--db=04"}).start(stage);
		OptionsManager.getSingleton().setUsingTestDB(true);
	}

	/**
	 *
	 */
	@Test
	public void testModalCanWriteTextInNameBox()
	{
		clickOn("#PlayerMenuButton");
		clickOn("#AddButton");
		String testName = "Name";
		clickOn("#NameField").write(testName);
		assertEquals(testName, AddPlayerModal.getInstance().getPlayerName());
		clickOn("#ModalCancelButton");
	}

	/**
	 *
	 */
	@Test
	public void testModalCanWriteSectionId()
	{
		clickOn("#PlayerMenuButton");
		clickOn("#AddButton");
		String testSectionId = "1";
		clickOn("#SectionIdField").write(testSectionId);
		assertEquals(testSectionId, AddPlayerModal.getInstance().getPlayerSectionId());
		clickOn("#ModalCancelButton");
	}

	/**
	 *
	 */
	@Test
	public void testModalCanWritePassword()
	{
		clickOn("#PlayerMenuButton");
		clickOn("#AddButton");
		String testPassword = "password";
		clickOn("#PasswordField").write(testPassword);
		assertEquals(testPassword, AddPlayerModal.getInstance().getPlayerPassword());
		clickOn("#ModalCancelButton");
	}

	/**
	 *
	 */
	@Test
	public void testModalCanWriteConfirmPassword()
	{
		clickOn("#PlayerMenuButton");
		clickOn("#AddButton");
		String testPassword = "password";
		clickOn("#ConfirmPasswordField").write(testPassword);
		assertEquals(testPassword, AddPlayerModal.getInstance().getPlayerConfirmPassword());
		clickOn("#ModalCancelButton");
	}

	/**
	 *
	 */
	@Test
	public void testModalCanSetThePlayersMajor()
	{
		clickOn("#PlayerMenuButton");
		clickOn("#AddButton");
		clickOn("#Major");
		clickOn("SOFTWARE_ENGINEERING");

		assertEquals(Major.SOFTWARE_ENGINEERING, AddPlayerModal.getInstance().getPlayerMajor());
		clickOn("#ModalCancelButton");

	}

	/**
	 *
	 */
	@Test
	public void testModalCanSetThePlayersCrew()
	{
		clickOn("#PlayerMenuButton");
		clickOn("#AddButton");
		clickOn("#Crew");
		this.press(KeyCode.DOWN);
		this.press(KeyCode.ENTER);

		assertEquals(Crew.values()[1], AddPlayerModal.getInstance().getPlayerCrew());
		clickOn("#ModalCancelButton");
	}

	/**
	 * Test that we can actually get the player added
	 * @throws InterruptedException shouldn't
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testQueueCommand() throws InterruptedException, DatabaseException
	{
		String playerName = "testQueueCommand_Name";
		clickOn("#PlayerMenuButton");
		clickOn("#AddButton");
		clickOn("#NameField").write(playerName);
		clickOn("#SectionIdField").write("1");
		clickOn("#PasswordField").write("Password");
		clickOn("#ConfirmPasswordField").write("Password");
		clickOn("#ModalSaveButton");

		Thread.sleep(2000);

		ArrayList<PlayerDTO> players = PlayerTableDataGatewayRDS.getSingleton().retrieveAllPlayers();
		int id = -1;
		boolean found = false;
		for (PlayerDTO player : players)
		{
			if (player.getPlayerName().equals(playerName))
			{
				id = player.getPlayerID();
				found = true;
			}
		}

		if (!found)
		{
			fail();
		}

		CommandDeletePlayer command = new CommandDeletePlayer(id);
		ModelFacade.getSingleton().queueCommand(command);
		Thread.sleep(2000);
	}
}
