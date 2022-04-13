package ui.fx.framework;

import ui.fx.IconFont;
import ui.fx.contentviews.PlayerContentView;

/**
 *
 * @author Josh McMillen, Ben Uleau
 * Main Menu Button to switch Content View to the Player View
 */
public class PlayerMenuButton extends MainMenuButton
{

	private static PlayerMenuButton instance;

	private PlayerMenuButton()
	{
		super("PlayerMenuButton", "Player Menu", IconFont.PERSON, PlayerContentView.getInstance());
	}


	/**
	 * @return Singleton reference to this Custom Menu Button
	 */
	public static MainMenuButton getInstance()
	{
		if (instance == null)
		{
			instance = new PlayerMenuButton();
		}
		return instance;
	}


}
