package ui.fx.framework_test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import manager.GameManagerFX;
import model.OptionsManager;
import ui.fx.IconFont;
import ui.fx.WorkSpace;
import ui.fx.contentviews.InteractableObjectContentView;
import ui.fx.contentviews.PlayerContentView;
import ui.fx.contentviews.QuizbotContentView;
import ui.fx.framework.AddButton;
import ui.fx.framework.AdventureMenuButton;
import ui.fx.framework.AlertBar;
import ui.fx.framework.ClearFilterButton;
import ui.fx.framework.DeleteButton;
import ui.fx.framework.EditButton;
import ui.fx.framework.FilterButton;
import ui.fx.framework.HelpMenuButton;
import ui.fx.framework.ImportButton;
import ui.fx.framework.InteractableObjectMenuButton;
import ui.fx.framework.PlayerAdventureStateButton;
import ui.fx.framework.PlayerMenuButton;
import ui.fx.framework.QuizbotMenuButton;

/**
 * @author Benjamin Uleau, Josh McMillen
 *
 */
public class FrameworkTest extends ApplicationTest
{
	/**
	 * Start the scene
	 */
	@Override
	public void start(Stage stage)
	{
		new GameManagerFX(new String[]
				{"--localhost", "--db=04"}).start(stage);
		OptionsManager.getSingleton().setUsingTestDB(true);
	}

	/**
	 * Verify the action buttons have the correct text
	 */
	@Test
	public void testActionButtonTexts()
	{
		assertEquals(IconFont.ADD.toString(), AddButton.getInstance().getText());
		assertEquals(IconFont.PENCIL.toString(), EditButton.getInstance().getText());
		assertEquals(IconFont.TRASH.toString(), DeleteButton.getInstance().getText());
		assertEquals(IconFont.IMPORT.toString(), ImportButton.getInstance().getText());
		assertEquals(IconFont.MAP.toString(), AdventureMenuButton.getInstance().getText());
		assertEquals(IconFont.REFRESH.toString(), ClearFilterButton.getInstance().getText());
		assertEquals(IconFont.MAGNIFYING_GLASS.toString(), FilterButton.getInstance().getText());
		assertEquals(IconFont.HELP.toString(), HelpMenuButton.getInstance().getText());
		assertEquals(IconFont.PERSON.toString(), PlayerMenuButton.getInstance().getText());
		assertEquals(IconFont.SUNGLASSES.toString(), QuizbotMenuButton.getInstance().getText());
		assertEquals(IconFont.ITEM_BOX.toString(), InteractableObjectMenuButton.getInstance().getText());
		assertEquals(IconFont.CIRCLE_CHECK.toString(), PlayerAdventureStateButton.getSingleton().getText());
	}

	/**
	 * Test the action buttons from the player content view
	 */
	@Test
	public void testActionButtonsPlayerContext()
	{

		clickOn("#PlayerMenuButton");
		assertEquals("PlayerContentView", WorkSpace.getInstance().getCenter().getClass().getSimpleName());
		clickOn("#PlayersTable");
		assertEquals("ID", PlayerContentView.getInstance().getPlayersTable().getColumns().get(0).getText());
		assertEquals("NAME", PlayerContentView.getInstance().getPlayersTable().getColumns().get(1).getText());
		assertEquals("CREW", PlayerContentView.getInstance().getPlayersTable().getColumns().get(2).getText());
		assertEquals("MAJOR", PlayerContentView.getInstance().getPlayersTable().getColumns().get(3).getText());
		assertEquals("SECTION", PlayerContentView.getInstance().getPlayersTable().getColumns().get(4).getText());

		clickOn("#PlayersTable");
		assertEquals("John", PlayerContentView.getInstance().getPlayersTable().getItems().get(0).getPlayerName());

		clickOn("#AddButton");
		assertEquals("ADD PLAYER", AlertBar.getInstance().getMessage());
		clickOn("#ModalCancelButton");

		clickOn("#EditButton");
		assertEquals("EDIT PLAYER", AlertBar.getInstance().getMessage());
		clickOn("#ModalCancelButton");

		clickOn("#DeleteButton");
		assertEquals("DELETE PLAYER", AlertBar.getInstance().getMessage());
		clickOn("#NoButton");

		clickOn("#ImportButton");
		assertEquals("IMPORT PLAYERS", AlertBar.getInstance().getMessage());
		push(KeyCode.ESCAPE);

	}

	/**
	 * Test the action buttons from the adventure content view
	 */
	@Test
	public void testActionButtonsAdventureContext()
	{
		clickOn("#AdventureMenuButton");
		assertEquals("AdventureContentView", WorkSpace.getInstance().getCenter().getClass().getSimpleName());

		clickOn("#AddButton");
		clickOn("#AdventureButton");
		clickOn("#ModalCancelButton");
		assertEquals("ADD ADVENTURE OR QUEST", AlertBar.getInstance().getMessage());

		clickOn("#EditButton");
		clickOn("#ModalCancelButton");
		assertEquals("EDIT QUEST", AlertBar.getInstance().getMessage());

		clickOn("#DeleteButton");
		assertEquals("DELETE QUEST", AlertBar.getInstance().getMessage());
		clickOn("#NoButton");

		clickOn("#ImportButton");
		assertEquals("IMPORT ADVENTURES AND/OR QUESTS", AlertBar.getInstance().getMessage());
		push(KeyCode.ESCAPE);
	}

	/**
	 * Test the action buttons from the quizbot content view
	 *
	 * @throws InterruptedException
	 *             - if thread was interupted
	 */
	@Test
	public void testActionButtonsQuizbotContext() throws InterruptedException
	{
		clickOn("#QuizbotMenuButton");
		assertEquals("QuizbotContentView", WorkSpace.getInstance().getCenter().getClass().getSimpleName());
		clickOn("#QuestionsTable");
		assertEquals("ID", QuizbotContentView.getInstance().getQuestionsTable().getColumns().get(0).getText());
		assertEquals("QUESTION", QuizbotContentView.getInstance().getQuestionsTable().getColumns().get(1).getText());
		assertEquals("ANSWER", QuizbotContentView.getInstance().getQuestionsTable().getColumns().get(2).getText());
		assertEquals("START", QuizbotContentView.getInstance().getQuestionsTable().getColumns().get(3).getText());
		assertEquals("END", QuizbotContentView.getInstance().getQuestionsTable().getColumns().get(4).getText());

		clickOn("#AddButton");
		assertEquals("ADD QUIZ", AlertBar.getInstance().getMessage());
		clickOn("#ModalCancelButton");

		QuizbotContentView.getInstance().getQuestionsTable().getSelectionModel().select(0);
		clickOn("#EditButton");
		assertEquals("EDIT QUIZ", AlertBar.getInstance().getMessage());
		clickOn("#ModalCancelButton");

		clickOn("#DeleteButton");
		push(KeyCode.ESCAPE);
		assertEquals("QUESTION DELETION CANCELLED", AlertBar.getInstance().getMessage());

		clickOn("#ImportButton");
		assertEquals("IMPORT QUIZBOT QUESTIONS", AlertBar.getInstance().getMessage());
		push(KeyCode.ESCAPE);
	}

	/**
	 *
	 */
	@Test
	public void testInteractableObjectButtons()
	{
		clickOn("#InteractableObjectMenuButton");
		assertEquals("InteractableObjectContentView", WorkSpace.getInstance().getCenter().getClass().getSimpleName());
		System.out.println(InteractableObjectContentView.getInstance().getInteractableObjectTable().getSelectionModel()
				.getSelectedIndex());

		clickOn("#InteractableObjectsTable");
		InteractableObjectContentView.getInstance().getInteractableObjectTable().getSelectionModel().select(2);
		System.out.println(InteractableObjectContentView.getInstance().getInteractableObjectTable().getSelectionModel()
				.getSelectedIndex());

		// TABLE VIEW STUFF HERE
		clickOn("#AddButton");
		assertEquals("ADD INTERACTABLE OBJECT", AlertBar.getInstance().getMessage());
		clickOn("#ModalCancelButton");

		clickOn("#EditButton");
		assertEquals("EDIT INTERACTABLE OBJECT", AlertBar.getInstance().getMessage());
		clickOn("#ModalCancelButton");

		clickOn("#DeleteButton");
		assertEquals("DELETE INTERACTABLE OBJECT", AlertBar.getInstance().getMessage());
		clickOn("#NoButton");
		assertEquals("DELETE ITEM CANCELLED", AlertBar.getInstance().getMessage());

		clickOn("#ImportButton");
		assertEquals("IMPORT INTERACTABLE OBJECTS", AlertBar.getInstance().getMessage());
		push(KeyCode.ESCAPE);

	}

	/**
	 * Test the help button (this is independent from any content view)
	 */
	@Test
	public void testHelpMenuButton()
	{
		clickOn("#HelpMenuButton");
		assertEquals("OPENING HELP MENU", AlertBar.getInstance().getMessage());
	}

	/**
	 * Test the filter buttons and field from the player content view
	 */
	@Test
	public void testFilterPlayerContext()
	{
		clickOn("#PlayerMenuButton");

		clickOn("#FilterField").write("This is a Test");

		clickOn("#FilterButton");

		clickOn("#ClearFilterButton");
		assertEquals("REFRESH PLAYERS VIEW", AlertBar.getInstance().getMessage());

	}

	/**
	 * Test the filter buttons and field from the adventure content view
	 */
	@Test
	public void testFilterAdventureContext()
	{
		clickOn("#AdventureMenuButton");

		clickOn("#FilterField").write("This is a Test");

		clickOn("#FilterButton");

		clickOn("#ClearFilterButton");
		assertEquals("REFRESH ADVENTURES AND QUESTS VIEW", AlertBar.getInstance().getMessage());

	}

	/**
	 * Test the filter buttons and field from the quizbot content view
	 */
	@Test
	public void testFilterQuizbotContext()
	{
		clickOn("#QuizbotMenuButton");

		clickOn("#FilterField").write("This is a Test");

		clickOn("#FilterButton");


		clickOn("#ClearFilterButton");
		assertEquals("REFRESH QUIZBOT VIEW", AlertBar.getInstance().getMessage());

	}
}
