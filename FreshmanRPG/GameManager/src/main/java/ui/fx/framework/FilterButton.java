package ui.fx.framework;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ui.fx.IconFont;
import ui.fx.WorkSpace;

/**
 * @author Josh McMillen, Ben Uleau
 * Button filter content
 */
public class FilterButton extends ActionButton
{

	private static FilterButton instance;

	/**
	 * Singleton constructor
	 */
	private FilterButton()
	{
		super("FilterButton", IconFont.MAGNIFYING_GLASS);

		/*
		 * Handle the action
		 */
		this.setOnAction(event ->
		{
			//If target content isn't null
			if (WorkSpace.getInstance().targetContent != null)
			{
				//And the filter field's text isn't empty
				if (FilterField.getInstance().getText() != null && FilterField.getInstance().getText().length() > 0)
				{
					//Filter target content by text
					WorkSpace.getInstance().targetContent.filter(FilterField.getInstance().getText());
				}
			}
		});

	}

	/**
	 * @return singleton instance of FilterButton
	 */
	public static ActionButton getInstance()
	{
		if (instance == null)
		{
			instance = new FilterButton();
		}
		return instance;
	}

}
