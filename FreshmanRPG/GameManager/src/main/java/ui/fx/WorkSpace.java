package ui.fx;

import javafx.scene.layout.BorderPane;
import ui.fx.contentviews.PlayerContentView;
import ui.fx.framework.ActionBar;
import ui.fx.framework.AlertBar;
import ui.fx.framework.ContentView;
import ui.fx.framework.PlayerAdventureStateButton;

/**
 *
 * @author Josh McMillen, Ben Uleau
 *
 */
public class WorkSpace extends BorderPane
{
	private static WorkSpace instance;
	/**
	 * Used by action buttons to identify what to act upon
	 */
	public ContentView targetContent;

	/**
	 * Singleton constructor
	 */
	private WorkSpace()
	{
		// Set basic parameters of the workspace
		this.setId("WorkSpace");
		// Place the action bar at the top of the workspace
		this.setTop(ActionBar.getInstance());
		// Place the alert bar at the bottom of the workspace
		this.setBottom(AlertBar.getInstance());
	}

	/**
	 * @param contentView
	 *            - Set the target view
	 */
	public void setView(ContentView contentView)
	{
		this.targetContent = contentView;
		this.setCenter(targetContent);
		if (contentView instanceof PlayerContentView)
		{
			PlayerAdventureStateButton.getSingleton().setVisible(true);
		}
		else
		{
			PlayerAdventureStateButton.getSingleton().setVisible(false);
		}
	}

	/**
	 * @return the singleton instance of the workspace
	 */
	public static WorkSpace getInstance()
	{
		if (instance == null)
		{
			instance = new WorkSpace();
		}
		return instance;
	}

}
