package ui.fx.framework;

import ui.fx.IconFont;
import ui.fx.contentviews.InteractableObjectContentView;

/**
 * Main menu button to switch content view to the interactable object view
 * @author Benjamin Uleau, Mohammed Almaslamani
 *
 */
public class InteractableObjectMenuButton extends MainMenuButton
{
	private static InteractableObjectMenuButton instance;

	private InteractableObjectMenuButton()
	{
		super("InteractableObjectMenuButton", "Interactable object", IconFont.ITEM_BOX, InteractableObjectContentView.getInstance());
	}

	/**
	 * @return Singleton reference to this custom menu button.
	 */
	public static MainMenuButton getInstance()
	{
		if (instance == null)
		{
			instance = new InteractableObjectMenuButton();
		}
		return instance;
	}
}
