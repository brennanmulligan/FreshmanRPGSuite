package ui.fx.framework;

import ui.fx.IconFont;
import ui.fx.contentviews.AdventureContentView;

/**
 *
 * @author Josh McMillen, Ben Uleau
 * Main Menu Button to switch Content View to the Adventure / Quests View
 */
public class AdventureMenuButton extends MainMenuButton
{

	private static AdventureMenuButton instance;

	private AdventureMenuButton()
	{
		super("AdventureMenuButton", "Adventure / Quests", IconFont.MAP, AdventureContentView.getInstance());
	}

	/**
	 * @return Singleton reference to this Custom Menu Button
	 */
	public static MainMenuButton getInstance()
	{
		if (instance == null)
		{
			instance = new AdventureMenuButton();
		}
		return instance;
	}


}
