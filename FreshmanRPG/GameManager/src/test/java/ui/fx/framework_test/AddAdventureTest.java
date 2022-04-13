package ui.fx.framework_test;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import datasource.AdventureTableDataGatewayMock;
import datasource.QuestTableDataGatewayMock;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import manager.GameManagerFX;
import model.AdventureRecord;
import model.CommandDeleteAdventure;
import model.ModelFacade;
import model.OptionsManager;
import model.QuestRecord;
import ui.fx.contentviews.AdventureContentView;
import ui.fx.dialogues.AddAdventureModal;

/**
 * Tests that the GUI can add adventures
 *
 */
public class AddAdventureTest extends ApplicationTest
{

	/**
	 * @see org.testfx.framework.junit.ApplicationTest#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage stage)
	{
		new GameManagerFX(new String[]{"--localhost", "--db=04"}).start(stage);
		OptionsManager.getSingleton().setUsingTestDB(true);
		QuestTableDataGatewayMock.getInstance().resetData();
		AdventureTableDataGatewayMock.getSingleton().resetData();
		AddAdventureModal.getInstance().reset();
	}

	/**
	 *
	 */
	@Test
	public void testAddAdventure()
	{
		String adventureName = "pinkberry";

		clickOn("#AdventureMenuButton");
		clickOn("#AddButton");
		clickOn("#AdventureButton");
		clickOn("#QuestName");
		System.out.println("Selected Index when adding adventure " + lookup("#QuestName").queryComboBox().getSelectionModel().getSelectedIndex());
		push(KeyCode.DOWN);
		push(KeyCode.ENTER);
		clickOn("#Desc").write(adventureName);
		clickOn("#XPGained");
		push(KeyCode.UP);
		push(KeyCode.ENTER);
		clickOn("#AdventureCompletionType");
		push(KeyCode.DOWN);
		push(KeyCode.ENTER);
		clickOn("#AdventureParam").write("Hello");
		clickOn("#ModalSaveButton");
		sleep(1000);

		clickOn("#QuestsTable");
		AdventureContentView.getInstance().getQuestTable().getSelectionModel().select(0);
		boolean found = false;
		int adventureId = 0;
		QuestRecord quest = AdventureContentView.getInstance().getQuestTable().getSelectionModel().getSelectedItem();
		for (AdventureRecord adventure : quest.getAdventures())
		{
			if (adventure.getAdventureDescription().equals(adventureName))
			{
				found = true;
				adventureId = adventure.getAdventureID();
			}
		}

		if (found)
		{
			CommandDeleteAdventure command = new CommandDeleteAdventure(quest.getQuestID(), adventureId);
			ModelFacade.getSingleton().queueCommand(command);
			sleep(1000);
		}
		else
		{
			fail();
		}
	}
}
