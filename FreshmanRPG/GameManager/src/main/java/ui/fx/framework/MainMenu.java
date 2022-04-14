package ui.fx.framework;

import javafx.scene.layout.VBox;

/**
 * @author Josh McMillen, Ben Uleau Main menu that holds the player, quizbot,
 *         and objective buttons
 */
public class MainMenu extends VBox
{
	private static MainMenu instance;

	/**
	 * Singleton constructor
	 */
	private MainMenu()
	{
		// Set basic parameters
		this.setId("MainMenu");
		// Add the player menu button, objective menu button, and quizbot menu buttons
		// to the main menu
		this.getChildren().addAll(
				PlayerMenuButton.getInstance(),
				ObjectiveMenuButton.getInstance(),
				QuizbotMenuButton.getInstance(),
				InteractableObjectMenuButton.getInstance(),
				MessageBoardMenuButton.getInstance());
	}

	/**
	 * @return singleton instance of main menu
	 */
	public static MainMenu getInstance()
	{
		if (instance == null)
		{
			instance = new MainMenu();
		}
		return instance;
	}

}
