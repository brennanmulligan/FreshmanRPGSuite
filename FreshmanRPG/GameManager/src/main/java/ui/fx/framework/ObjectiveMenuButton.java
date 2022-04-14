package ui.fx.framework;

import ui.fx.IconFont;
import ui.fx.contentviews.ObjectiveContentView;

/**
 *
 * @author Josh McMillen, Ben Uleau
 * Main Menu Button to switch Content View to the Objective / Quests View
 */
public class ObjectiveMenuButton extends MainMenuButton
{

	private static ObjectiveMenuButton instance;

	private ObjectiveMenuButton()
	{
		super("ObjectiveMenuButton", "Objective / Quests", IconFont.MAP, ObjectiveContentView.getInstance());
	}

	/**
	 * @return Singleton reference to this Custom Menu Button
	 */
	public static MainMenuButton getInstance()
	{
		if (instance == null)
		{
			instance = new ObjectiveMenuButton();
		}
		return instance;
	}


}
