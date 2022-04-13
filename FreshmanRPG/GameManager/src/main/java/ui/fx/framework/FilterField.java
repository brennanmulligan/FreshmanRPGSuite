package ui.fx.framework;

import javafx.scene.control.TextField;

/**
 * @author Josh McMillen, Ben Uleau Field for typing filter
 */
public class FilterField extends TextField
{

	private static FilterField instance;

	/**
	 * Singleton constructor
	 */
	public FilterField()
	{
		// Set basic parameters of filter field
		this.setId("FilterField");
		// The height should be the max value
		this.setPrefHeight(Double.MAX_VALUE);

		/*
		 * This temporarily disables the Filter search box. Will be implemented at a later date.
		 */
		this.setPromptText("Filter: Not Implemented");
		this.setDisable(true);
	}

	/**
	 * @return singleton instance of FilterField
	 */
	public static FilterField getInstance()
	{
		if (instance == null)
		{
			instance = new FilterField();
		}
		return instance;
	}

}
