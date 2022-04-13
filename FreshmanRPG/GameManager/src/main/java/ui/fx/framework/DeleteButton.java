package ui.fx.framework;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ui.fx.IconFont;
import ui.fx.WorkSpace;

/**
 *
 * @author Josh McMillen, Ben Uleau Action Button that will call a Content
 *         View's Delete Function
 */
public class DeleteButton extends ActionButton
{

	private static DeleteButton instance;

	private DeleteButton()
	{
		super("DeleteButton", IconFont.TRASH);

		// Capture click and run Delete Function of Active Content View
		this.setOnAction(event ->
		{
			if (WorkSpace.getInstance().targetContent != null)
			{
				WorkSpace.getInstance().targetContent.delete();
			}
		});

	}

	/**
	 * @return Singleton instance of Delete Button
	 */
	public static ActionButton getInstance()
	{
		if (instance == null)
		{
			instance = new DeleteButton();
		}
		return instance;
	}

}
