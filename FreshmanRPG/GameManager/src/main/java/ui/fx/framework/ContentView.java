package ui.fx.framework;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Josh McMillen, Ben Uleau
 * Parent of all Content Views to apply all styling changes across the board
 */
public abstract class ContentView extends BorderPane
{


	/**
	 * @param id = style sheet label
	 * @param label = header label of view
	 */
	public ContentView(String id, String label)
	{
		this.setId(id);
		Label header = new Label(label);
		this.getStyleClass().add("ContentView");
		header.setPrefHeight(32);
		header.setPrefWidth(Double.MAX_VALUE);
		header.setAlignment(Pos.CENTER);
		this.setTop(header);
	}

	/**
	 * Default add of a content view
	 */
	public void add()
	{
		// Add Default Functionality
	}

	/**
	 * Default delete of a content view
	 */
	public void delete()
	{
		// Add Default Functionality
	}

	/**
	 * Default edit of a content view
	 */
	public void edit()
	{
		// Add Default Functionality
	}

	/**
	 * @param filter - filter active view
	 * Default filter of a content view
	 */
	public void filter(String filter)
	{
		// Add Default Functionality
	}

	/**
	 * Default refresh of a content view
	 */
	public void refresh()
	{
		// Add Default Functionality
	}

	/**
	 * Default refresh of a content view
	 */
	public void importFile()
	{
		// Add Default Functionality
	}

}
