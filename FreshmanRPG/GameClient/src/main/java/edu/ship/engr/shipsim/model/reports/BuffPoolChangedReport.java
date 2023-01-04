package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.view.screen.notification.NotificationType;

/**
 * Class for when buffPool of player has changed.
 *
 * @author Adam Pine & Emmanuel Douge
 */
public class BuffPoolChangedReport implements Report, NotificationTrigger
{
    private int buffPool;

    /**
     * @param buffPool the remaining buffpool value
     */
    public BuffPoolChangedReport(int buffPool)
    {
        this.setBuffPool(buffPool);
    }

    /**
     * @return the remaining buffpool value.
     */
    public int getBuffPool()
    {
        return buffPool;
    }

    private void setBuffPool(int buffPool)
    {
        this.buffPool = buffPool;
    }


    @Override
    public String getNotificationTitle()
    {
        return "Buff pool updated";
    }

    @Override
    public String getNotificationBody()
    {
        return "You have used 1 Bonus Point. You have " + getBuffPool() + " Rec Center Bonus Points Left.";
    }

    @Override
    public NotificationType getNotificationType()
    {
        return NotificationType.ALERT;
    }
}
