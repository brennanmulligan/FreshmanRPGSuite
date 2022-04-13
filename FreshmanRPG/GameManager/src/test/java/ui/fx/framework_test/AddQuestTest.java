package ui.fx.framework_test;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import manager.GameManagerFX;
import model.CommandDeleteQuest;
import model.ModelFacade;
import model.OptionsManager;
import model.QuestRecord;
import ui.fx.contentviews.AdventureContentView;

/**
 * Test that quests can be added.
 * @author Chris Boyer and Abe WhatverHisLastNameIs
 */
public class AddQuestTest extends ApplicationTest
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
	public void testAddQuest()
	{
		String questTitle = "A totally Different Name2";
		clickOn("#AdventureMenuButton");
		clickOn("#AddButton");
		clickOn("#QuestButton");
		clickOn("#QuestTitle").write(questTitle);
		clickOn("#Desc").write("description");
		clickOn("#MapOptions");
		push(KeyCode.DOWN);
		push(KeyCode.ENTER);
		clickOn("#QuestCompletionActionType");
		push(KeyCode.DOWN);
		push(KeyCode.DOWN);
		push(KeyCode.ENTER);
		clickOn("#ModalSaveButton");

		sleep(1000);
		boolean found = false;
		int questId = 0;
		for (QuestRecord quest : AdventureContentView.getInstance().getQuestTable().getItems())
		{
			if (quest.getTitle().equals(questTitle))
			{
				found = true;
				questId = quest.getQuestID();
			}
		}

		if (found)
		{
			CommandDeleteQuest command = new CommandDeleteQuest(questId);
			ModelFacade.getSingleton().queueCommand(command);
			sleep(1000);
		}
		else
		{
			fail();
		}

	}
}
