package ui.fx.framework;

import dataDTO.PlayerDTO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ui.fx.IconFont;
import ui.fx.contentviews.PlayerContentView;

/**
 * Action Button for pulling up the player adventure state.
 * @author Christopher Boyer and Abe Loscher
 */
public class PlayerAdventureStateButton extends ActionButton
{

	private static PlayerAdventureStateButton instance;

	/**
	 * Constructor for PlayerAdventureStateButton
	 */
	private PlayerAdventureStateButton()
	{
		super("PlayerAdventureStateButton", IconFont.CIRCLE_CHECK);

		this.setOnAction(event ->
		{
			PlayerDTO dto = PlayerContentView.getInstance().getPlayersTable().getSelectionModel().getSelectedItem();
			if (dto != null)
			{
				PlayerContentView.getInstance().changeAdventureStatusForPlayer(dto);
			}
		});
	}

	/**
	 * @return the only one of these in the system
	 */
	public static PlayerAdventureStateButton getSingleton()
	{
		if (instance == null)
		{
			instance = new PlayerAdventureStateButton();
		}
		return instance;
	}

}
