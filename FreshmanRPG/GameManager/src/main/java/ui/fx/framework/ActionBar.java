package ui.fx.framework;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author Josh McMillen, Ben Uleau
 *
 */
public class ActionBar extends BorderPane
{

	private static ActionBar instance;

	private ActionBar()
	{
		// Set basic parameters of the action bar
		this.setId("ActionBar");
		this.setPrefHeight(48);

		// Add Container for the filter controls
		HBox filterBox = new HBox();
		filterBox.getChildren().addAll(FilterField.getInstance(), FilterButton.getInstance(),
				ClearFilterButton.getInstance());

		// Add container for the Action Buttons
		HBox buttonBox = new HBox();
		buttonBox.getChildren().addAll(PlayerObjectiveStateButton.getSingleton(), ImportButton.getInstance(), AddButton.getInstance(), EditButton.getInstance(), DeleteButton.getInstance());
		PlayerObjectiveStateButton.getSingleton().setVisible(false);
		this.setLeft(filterBox);
		this.setRight(buttonBox);
	}

	/**
	 * @return singleton instance of Action Bar
	 */
	public static ActionBar getInstance()
	{
		if (instance == null)
		{
			instance = new ActionBar();
		}
		return instance;
	}

}
