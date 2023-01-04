package edu.ship.engr.shipsim.model.cheatCodeBehaviors;

import edu.ship.engr.shipsim.model.Player;
import edu.ship.engr.shipsim.model.PlayerManager;
import edu.ship.engr.shipsim.model.ReportObserverConnector;
import edu.ship.engr.shipsim.model.reports.InteractableObjectBuffReport;

/**
 * Behavior for cheat code that gives buff
 *
 * @author merlin
 */
public class BuffBehavior implements CheatCodeBehavior
{

    /**
     * How much buff the player should get if they use this cheat code
     */
    public static final int BUFF_VALUE = 30;
    static final String text = "Magic Buff";

    /**
     * @see CheatCodeBehavior#giveCheat(int,
     * java.lang.String)
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
                ReportObserverConnector.getSingleton()
                        .sendReport(new InteractableObjectBuffReport(playerID, changeInBuff));
                return true;
            }
        }
        return false;
    }

}
