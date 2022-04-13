package ui.fx.framework;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ui.fx.IconFont;
import ui.fx.WorkSpace;

/**
 *
 * @author Josh McMillen, Ben Uleau Edit Button that will call a Content View's
 *         Edit Button
 */
public class EditButton extends ActionButton
{

	private static EditButton instance;

	private EditButton()
	{
		super("EditButton", IconFont.PENCIL);

		// Capture click and run Edit Function of Active Content View
		this.setOnAction(event ->
		{
			if (WorkSpace.getInstance().targetContent != null)
			{
				WorkSpace.getInstance().targetContent.edit();
			}
		});

	}

	/**
	 * @return Singleton instance of Edit Button
	 */
	public static ActionButton getInstance()
	{
		if (instance == null)
		{
			instance = new EditButton();
		}
		return instance;
	}

}
