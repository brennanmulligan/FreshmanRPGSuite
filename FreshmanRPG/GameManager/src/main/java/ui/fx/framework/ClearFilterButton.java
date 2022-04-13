package ui.fx.framework;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ui.fx.IconFont;
import ui.fx.WorkSpace;

/**
 *
 * @author Josh McMillen, Ben Uleau Refreshes active view and clears out
 *         FilterField text
 */
public class ClearFilterButton extends ActionButton
{

	private static ClearFilterButton instance;

	private ClearFilterButton()
	{
		super("ClearFilterButton", IconFont.REFRESH);

		// Capture click and reset view and clear FilterField text
		this.setOnAction(event ->
		{
			if (WorkSpace.getInstance().targetContent != null)
			{
				WorkSpace.getInstance().targetContent.refresh();
				FilterField.getInstance().setText(null);
			}
		});

	}

	/**
	 * @return - Singleton Reference to this ClearFilterButton
	 */
	public static ClearFilterButton getInstance()
	{
		if (instance == null)
		{
			instance = new ClearFilterButton();
		}
		return instance;
	}

}
