package ui.fx;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import ui.fx.framework.MainMenu;

/**
 * @author Josh McMillen, Ben Uleau Navigation menu singleton (bar on the left
 *         containing players, quizbot questions, and quests/objectives
 */
public class NavigationMenu extends BorderPane
{

	private static NavigationMenu instance;

	/**
	 * Singleton constructor
	 */
	private NavigationMenu()
	{
		// Set basic parameters of the menu
		this.setId("NavigationMenu");
		this.setPrefWidth(128);

		// Create the menu header label
		Label menuHeader = new Label("MENU");
		menuHeader.setId("MenuHeader");
		// Label should be as wide as the left region
		menuHeader.setPrefWidth(Double.MAX_VALUE);
		menuHeader.setPrefHeight(48);
		// Center text in label
		menuHeader.setAlignment(Pos.CENTER);
		// Insert menu header at top region of navigation menu
		this.setTop(menuHeader);
		// Add main menu to center of the navigation menu
		this.setCenter(MainMenu.getInstance());
	}


	/**
	 * @return the singleton instance of the navigation menu
	 */
	public static synchronized NavigationMenu getInstance()
	{
		if (instance == null)
		{
			instance = new NavigationMenu();
		}
		return instance;
	}

}
