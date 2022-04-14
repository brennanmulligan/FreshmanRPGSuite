package ui.fx.framework_test;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import datasource.DatabaseException;
import datasource.DatabaseManager;
import javafx.stage.Stage;
import manager.GameManagerFX;
import model.OptionsManager;
import ui.fx.contentviews.ObjectiveContentView;
import ui.fx.framework.AlertBar;

/**
 * Test deleting a quest
 *
 * @author Benjamin Uleau, Mohammed Almaslamani
 *
 */
public class DeleteQuestTest extends ApplicationTest
{
	/**
	 * Starts the game manager
	 */
	@Override
	public void start(Stage stage)
	{
		try
		{
			DatabaseManager.getSingleton().setTesting();
		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
		}
		OptionsManager.getSingleton().setUsingTestDB(true);
		OptionsManager.getSingleton().setUsingMocKDataSource(false);
		new GameManagerFX(new String[]
				{"--localhost", "--db=04"}).start(stage);

	}

	/**
	 * Setup for test class
	 *
	 * @throws Exception
	 *             - exception
	 */
	@Before
	public void setUp() throws Exception
	{
		DatabaseManager.getSingleton().setTesting();
	}

	/**
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws SQLException
	 *             shouldn't
	 *
	 */
	@After
	public void tearDown() throws DatabaseException, SQLException
	{

		DatabaseManager.getSingleton().rollBack();

	}

	/**
	 * Testing the confirmation message shows up correctly
	 */
	@Test
	public void testDeleteButtonShowsConfirmation()
	{
		clickOn("#ObjectiveMenuButton");
		ObjectiveContentView.getInstance().getQuestTable().getSelectionModel().select(0);
		clickOn("#DeleteButton");
		clickOn("#NoButton");
		assertEquals("DELETE QUEST", AlertBar.getInstance().getMessage());
	}

	/**
	 * testing deleting quest from the db
	 */
	@Test
	public void testDeleteSelectedButton()
	{
		clickOn("#ObjectiveMenuButton");

		int sizeOfTableBeforeDelete = ObjectiveContentView.getInstance().getQuestTable().getItems().size();

		ObjectiveContentView.getInstance().getQuestTable().getSelectionModel()
				.select(ObjectiveContentView.getInstance().getQuestTable().getItems().size() - 1);
		System.out.println(ObjectiveContentView.getInstance().getQuestTable().getSelectionModel().getSelectedItem()
				.getDescription());
		clickOn("#DeleteButton");
		clickOn("#YesButton");

		this.sleep(2000);

		assertEquals(sizeOfTableBeforeDelete - 1, ObjectiveContentView.getInstance().getQuestTable().getItems().size());
	}
}
