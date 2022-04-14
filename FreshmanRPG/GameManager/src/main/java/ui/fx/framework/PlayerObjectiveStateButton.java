package ui.fx.framework;

import dataDTO.PlayerDTO;
import ui.fx.IconFont;
import ui.fx.contentviews.PlayerContentView;

/**
 * Action Button for pulling up the player objective state.
 * @author Christopher Boyer and Abe Loscher
 */
public class PlayerObjectiveStateButton extends ActionButton
{

	private static PlayerObjectiveStateButton instance;

	/**
	 * Constructor for PlayerObjectiveStateButton
	 */
	private PlayerObjectiveStateButton()
	{
		super("PlayerObjectiveStateButton", IconFont.CIRCLE_CHECK);

		this.setOnAction(event ->
		{
			PlayerDTO dto = PlayerContentView.getInstance().getPlayersTable().getSelectionModel().getSelectedItem();
			if (dto != null)
			{
				PlayerContentView.getInstance().changeObjectiveStatusForPlayer(dto);
			}
		});
	}

	/**
	 * @return the only one of these in the system
	 */
	public static PlayerObjectiveStateButton getSingleton()
	{
		if (instance == null)
		{
			instance = new PlayerObjectiveStateButton();
		}
		return instance;
	}

}
