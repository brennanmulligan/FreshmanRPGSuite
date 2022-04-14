package ui.fx.framework_test;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import datasource.ObjectiveTableDataGatewayMock;
import datasource.QuestTableDataGatewayMock;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import manager.GameManagerFX;
import model.ObjectiveRecord;
import model.CommandDeleteObjective;
import model.ModelFacade;
import model.OptionsManager;
import model.QuestRecord;
import ui.fx.contentviews.ObjectiveContentView;
import ui.fx.dialogues.AddObjectiveModal;

/**
 * Tests that the GUI can add objectives
 *
 */
public class AddObjectiveTest extends ApplicationTest
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
		ObjectiveTableDataGatewayMock.getSingleton().resetData();
		AddObjectiveModal.getInstance().reset();
	}

	/**
	 *
	 */
	@Test
	public void testAddObjective()
	{
		String objectiveName = "pinkberry";

		clickOn("#ObjectiveMenuButton");
		clickOn("#AddButton");
		clickOn("#ObjectiveButton");
		clickOn("#QuestName");
		System.out.println("Selected Index when adding objective " + lookup("#QuestName").queryComboBox().getSelectionModel().getSelectedIndex());
		push(KeyCode.DOWN);
		push(KeyCode.ENTER);
		clickOn("#Desc").write(objectiveName);
		clickOn("#XPGained");
		push(KeyCode.UP);
		push(KeyCode.ENTER);
		clickOn("#ObjectiveCompletionType");
		push(KeyCode.DOWN);
		push(KeyCode.ENTER);
		clickOn("#ObjectiveParam").write("Hello");
		clickOn("#ModalSaveButton");
		sleep(1000);

		clickOn("#QuestsTable");
		ObjectiveContentView.getInstance().getQuestTable().getSelectionModel().select(0);
		boolean found = false;
		int objectiveId = 0;
		QuestRecord quest = ObjectiveContentView.getInstance().getQuestTable().getSelectionModel().getSelectedItem();
		for (ObjectiveRecord objective : quest.getObjectives())
		{
			if (objective.getObjectiveDescription().equals(objectiveName))
			{
				found = true;
				objectiveId = objective.getObjectiveID();
			}
		}

		if (found)
		{
			CommandDeleteObjective command = new CommandDeleteObjective(quest.getQuestID(), objectiveId);
			ModelFacade.getSingleton().queueCommand(command);
			sleep(1000);
		}
		else
		{
			fail();
		}
	}
}
