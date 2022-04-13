package ui.fx.framework;

import ui.fx.IconFont;
import ui.fx.contentviews.QuizbotContentView;

/**
 *
 * @author Josh McMillen, Ben Uleau
 * Main Menu Button to switch Content View to the Quizbot View
 */
public class QuizbotMenuButton extends MainMenuButton
{

	private static QuizbotMenuButton instance;

	private QuizbotMenuButton()
	{
		super("QuizbotMenuButton", "Quizbot", IconFont.SUNGLASSES, QuizbotContentView.getInstance());
	}

	/**
	 * @return Singleton reference to this Custom Menu Button
	 */
	public static MainMenuButton getInstance()
	{
		if (instance == null)
		{
			instance = new QuizbotMenuButton();
		}
		return instance;
	}

}
