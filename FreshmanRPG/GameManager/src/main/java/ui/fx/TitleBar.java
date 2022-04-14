package ui.fx;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import ui.fx.framework.HelpMenuButton;

/**
 * @author Josh McMillen, Ben Uleau This is the title of the game manager
 */
public class TitleBar extends BorderPane
{
	private static TitleBar instance;

	/**
	 * Singleton constructor
	 */
	private TitleBar()
	{
		// Set basic parameters of the title bar
		this.setId("TitleBar");
		this.setPrefHeight(56);
		// Create the title label (title text)
		Label title = new Label("CS1 Objectives Management System");
		title.setId("Title");
		this.setCenter(title);
		this.setRight(HelpMenuButton.getInstance());
	}

	/**
	 * @return the singleton instance of the title menu
	 */
	public static TitleBar getInstance()
	{
		if (instance == null)
		{
			instance = new TitleBar();
		}
		return instance;
	}

}
