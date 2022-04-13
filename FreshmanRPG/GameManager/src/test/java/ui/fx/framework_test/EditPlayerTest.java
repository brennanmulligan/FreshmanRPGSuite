package ui.fx.framework_test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import datatypes.Crew;
import datatypes.Major;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import manager.GameManagerFX;
import model.OptionsManager;
import ui.fx.contentviews.PlayerContentView;
import ui.fx.dialogues.EditPlayerModal;

/**
 * Tests that the GUI can be used to edit a player
 */
public class EditPlayerTest extends ApplicationTest
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
	 * @throws InterruptedException shouldn't
	 */
	@Test
	public void testOpenModalWithPlayerInformation() throws InterruptedException
	{
		clickOn("#PlayerMenuButton");

		PlayerContentView.getInstance().getPlayersTable().getSelectionModel().select(0);

		clickOn("#EditButton");

		assertEquals("John", EditPlayerModal.getInstance().getPlayerName());
		assertEquals(1, EditPlayerModal.getInstance().getPlayerId());
		assertEquals("1", EditPlayerModal.getInstance().getPlayerSectionId());
		assertEquals(Crew.NULL_POINTER, EditPlayerModal.getInstance().getPlayerCrew());
		assertEquals(Major.COMPUTER_ENGINEERING, EditPlayerModal.getInstance().getPlayerMajor());
		clickOn("#ModalCancelButton");

	}

	/**
	 *
	 */
	@Test
	public void testModalCanWritePlayerName()
	{
		clickOn("#PlayerMenuButton");
		clickOn("#PlayersTable");
		clickOn("#EditButton");
		String testName = "Name";
		clickOn("#NameField").eraseText(10);
		clickOn("#NameField").write(testName);
		assertEquals(testName, EditPlayerModal.getInstance().getPlayerName());
		clickOn("#ModalCancelButton");
	}

	/**
	 *
	 */
	@Test
	public void testModalCanWriteSectionId()
	{
		clickOn("#PlayerMenuButton");
		clickOn("#PlayersTable");
		clickOn("#EditButton");
		String testSectionId = "1";
		clickOn("#SectionIdField").eraseText(2);
		clickOn("#SectionIdField").write(testSectionId);
		assertEquals(testSectionId, EditPlayerModal.getInstance().getPlayerSectionId());
		clickOn("#ModalCancelButton");
	}

	/**
	 *
	 */
	@Test
	public void testModalCanSetThePlayersMajor()
	{
		clickOn("#PlayerMenuButton");
		clickOn("#PlayersTable");
		PlayerContentView.getInstance().getPlayersTable().getSelectionModel().select(0);
		clickOn("#EditButton");
		clickOn("#Major");
		this.press(KeyCode.DOWN);
		this.press(KeyCode.ENTER);

		assertEquals(Major.ELECTRICAL_ENGINEERING, EditPlayerModal.getInstance().getPlayerMajor());
		clickOn("#ModalCancelButton");

	}

	/**
	 *
	 */
	@Test
	public void testModalCanSetThePlayersCrew()
	{
		clickOn("#PlayerMenuButton");
		clickOn("#PlayersTable");
		PlayerContentView.getInstance().getPlayersTable().getSelectionModel().select(0);
		clickOn("#EditButton");
		clickOn("#Crew");
		this.press(KeyCode.DOWN);
		this.press(KeyCode.ENTER);

		assertEquals(Crew.OUT_OF_BOUNDS, EditPlayerModal.getInstance().getPlayerCrew());
		clickOn("#ModalCancelButton");
	}


	/**
	 *
	 */
	@Test
	public void testModalPopulation()
	{
		clickOn("#PlayerMenuButton");
		clickOn("#PlayersTable");
		clickOn("#EditButton");
		clickOn("#ModalSaveButton");
	}

}
