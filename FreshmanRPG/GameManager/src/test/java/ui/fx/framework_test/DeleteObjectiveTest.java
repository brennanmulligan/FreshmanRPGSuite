package ui.fx.framework_test;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import dataENUM.QuestCompletionActionType;
import datasource.DatabaseException;
import datatypes.Position;
import javafx.stage.Stage;
import manager.GameManagerFX;
import model.CommandAddQuest;
import model.ModelFacade;
import model.OptionsManager;
import ui.fx.contentviews.ObjectiveContentView;
import ui.fx.framework.AlertBar;


/**
 * Test deleting an objective
 * @author Benjamin Uleau, Mohammed Almaslamani
 *
 */
public class DeleteObjectiveTest extends ApplicationTest
{

	/**
	 * Starts the game manager
	 */
	@Override
	public void start(Stage stage)
	{
		new GameManagerFX(new String[]{"--localhost", "--db=04"}).start(stage);
		OptionsManager.getSingleton().setUsingTestDB(true);
	}


	/**
	 * Setup for test class
	 * @throws Exception - exception 
	 */
	@Before
	public void setUp() throws Exception
	{

	}

	/**
	 * Testing the confirmation message shows up correctly
	 */
	@Test
	public void testDeleteButtonShowsConfirmation()
	{
		clickOn("#ObjectiveMenuButton");
		clickOn("#ClearFilterButton");
		ObjectiveContentView.getInstance().getQuestTable().getSelectionModel().select(0);
		ObjectiveContentView.getInstance().getObjectiveTable().getSelectionModel().select(0);
		clickOn("#DeleteButton");
		clickOn("#NoButton");
		assertEquals("DELETE OBJECTIVE", AlertBar.getInstance().getMessage());
	}

	/**
	 * testing deleting objective from the db
	 * @throws DatabaseException shouldn't 
	 */
	@Test
	public void testDeleteSelectedButton() throws DatabaseException
	{

		clickOn("#ObjectiveMenuButton");
		CommandAddQuest command = new CommandAddQuest("testTitle100", "description", "asdf", new Position(0, 0), 0, 0, QuestCompletionActionType.NO_ACTION, null, new Date(), new Date());
		ModelFacade.getSingleton().queueCommand(command);
		sleep(1000);
		this.sleep(1000);

		int sizeOfTableBeforeDelete = ObjectiveContentView.getInstance().getQuestTable().getItems().size();

		ObjectiveContentView.getInstance().getQuestTable().getSelectionModel().select(ObjectiveContentView.getInstance().getQuestTable().getItems().size() - 1);
		clickOn("#DeleteButton");
		clickOn("#YesButton");

		this.sleep(3000);

		assertEquals(sizeOfTableBeforeDelete - 1, ObjectiveContentView.getInstance().getQuestTable().getItems().size());
	}

}
