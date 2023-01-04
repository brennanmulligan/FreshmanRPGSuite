package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.view.screen.notification.NotificationType;

/**
 * @author Andy, Emmanuel, Adam, and Truc
 */
public class DisplayTextReport implements Report, NotificationTrigger
{
    private String text;

    /**
     * report constructor
     *
     * @param text - text to be displayed
     */
    public DisplayTextReport(String text)
    {
        this.text = text;
    }

    /**
     * @return the text to be displayed
     */
    public String getText()
    {
        return text;
    }

    @Override
    public String getNotificationTitle()
    {
        return "Game Notification";
    }

    @Override
    public String getNotificationBody()
    {
        return text;
    }

    @Override
    public NotificationType getNotificationType()
    {
        return NotificationType.ALERT;
    }
}
