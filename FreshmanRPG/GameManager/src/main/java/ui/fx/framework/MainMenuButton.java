package ui.fx.framework;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import ui.fx.IconFont;
import ui.fx.WorkSpace;

/**
 * @author Josh McMillen, Ben Uleau
 * abstract main menu button (player menu button, quizbot menu button, and adventure menu button inherit from this)
 */
public abstract class MainMenuButton extends Button
{


	/**
	 * @param id = style sheet identifier
	 * @param tooltip = popup message on hover
	 * @param icon = button label
	 * @param view = ContentView to switch to on click
	 */
	public MainMenuButton(String id, String tooltip, IconFont icon, ContentView view)
	{
		this.setId(id);
		this.setTooltip(new Tooltip(tooltip));
		this.setText(icon.toString());

		this.setOnAction(event -> WorkSpace.getInstance().setView(view));

		//Set class for css styling (#)
		this.getStyleClass().add("MainMenuButton");
		//Set basic parameters for main menu buttons
		this.setPrefWidth(Double.MAX_VALUE);
		this.setPrefHeight(48);
	}
}
