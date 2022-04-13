package ui.fx.framework;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ui.fx.IconFont;
import ui.fx.WorkSpace;

/**
 * @author Joshua McMillen, Mohammed Almaslamani
 *
 */
public class ImportButton extends ActionButton
{

	private static ImportButton instance;

	/**
	 *
	 */
	public ImportButton()
	{
		super("ImportButton", IconFont.IMPORT);

		// Capture click and run Add Function of Active Content View
		this.setOnAction(event ->
		{
			if (WorkSpace.getInstance().targetContent != null)
			{
				WorkSpace.getInstance().targetContent.importFile();
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
			instance = new ImportButton();
		}
		return instance;
	}

}
