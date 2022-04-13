package ui.fx.framework;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ui.fx.IconFont;
import ui.fx.WorkSpace;

/**
 *
 * @author Josh McMillen, Ben Uleau Action Button that will call a Content
 *         View's Add
 */
public class AddButton extends ActionButton
{

	private static AddButton instance;

	private AddButton()
	{
		super("AddButton", IconFont.ADD);

		// Capture click and run Add Function of Active Content View
		this.setOnAction(event ->
		{
			if (WorkSpace.getInstance().targetContent != null)
			{
				WorkSpace.getInstance().targetContent.add();
			}
		});

	}

	/**
	 * @return Singleton instance of Add Button
	 */
	public static ActionButton getInstance()
	{
		if (instance == null)
		{
			instance = new AddButton();
		}
		return instance;
	}

}
