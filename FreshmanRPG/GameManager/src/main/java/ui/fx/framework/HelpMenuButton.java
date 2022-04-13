package ui.fx.framework;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import ui.fx.IconFont;

/**
 * @author Josh McMillen, Ben Uleau
 * Button for bringing up the help menu
 */
public class HelpMenuButton extends Button
{

	private static HelpMenuButton instance;

	/**
	 * Singleton constructor
	 */
	private HelpMenuButton()
	{
		//Set basic parameters of the help menu button
		this.setId("HelpMenuButton");
		this.setPrefHeight(56);
		this.setPrefWidth(56);
		this.setTooltip(new Tooltip("Help Menu"));
		this.setText(IconFont.HELP.toString());
		//Capture click event on help menu button
		this.setOnAction(new EventHandler<ActionEvent>()
		{
			/**
			 * Handle click event
			 */
			@Override
			public void handle(ActionEvent event)
			{
				//Log an opening help menu botton to the alert bar
				AlertBar.getInstance().receiveMessage("OPENING HELP MENU");
			}
		});
	}

	/**
	 * @return singleton instance of HelpMenuButton
	 */
	public static HelpMenuButton getInstance()
	{
		if (instance == null)
		{
			instance = new HelpMenuButton();
		}
		return instance;
	}

}
