package ui.fx;

import javafx.scene.layout.BorderPane;

/**
 * @author Josh McMillen, Ben Uleau The user interface is the overall containing
 *         layout
 */
public class UserInterface extends BorderPane
{
	private static UserInterface instance;

	/**
	 * Singleton constructor
	 */
	private UserInterface()
	{
		// Set basic parameters of the UI
		this.setId("UserInterface");
		// Place the title bar at the top of the UI
		this.setTop(TitleBar.getInstance());
		// Place the navigation menu on the left side of the UI
		this.setLeft(NavigationMenu.getInstance());
		// Place the workspace in the center (center & right) of the UI
		this.setCenter(WorkSpace.getInstance());
	}

	/**
	 * @return the singleton instance of the UI
	 */
	public static UserInterface getInstance()
	{
		if (instance == null)
		{
			instance = new UserInterface();
		}
		return instance;
	}
}
