package model.cheatCodeBehaviors;

import model.Player;
import model.PlayerManager;
import model.QualifiedObservableConnector;
import model.reports.InteractableObjectBuffReport;

/**
 * Behavior for cheat code that gives buff
 *
 * @author merlin
 *
 */
public class BuffBehavior implements CheatCodeBehavior
{

	/**
	 * How much buff the player should get if they use this cheat code
	 */
	public static final int BUFF_VALUE = 30;
	static final String text = "Magic Buff";

	/**
	 * @see model.cheatCodeBehaviors.CheatCodeBehavior#giveCheat(int,
	 *      java.lang.String)
	 */
	@Override
	public boolean giveCheat(int playerID, String chatMessage)
	{
		if (chatMessage.equals(text))
		{
			Player player = PlayerManager.getSingleton().getPlayerFromID(playerID);
			int originalBuff = player.getBuffPool();
			player.setBuffPool(Math.max(player.getBuffPool(), BUFF_VALUE));
			int changeInBuff = BUFF_VALUE - originalBuff;
			if (changeInBuff > 0)
			{
				QualifiedObservableConnector.getSingleton()
						.sendReport(new InteractableObjectBuffReport(playerID, changeInBuff));
				return true;
			}
		}
		return false;
	}

}
