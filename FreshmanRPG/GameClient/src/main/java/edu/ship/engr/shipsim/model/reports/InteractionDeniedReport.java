package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.view.screen.notification.NotificationType;

/**
 * The report used when an interaction was denied, for any reason.
 */
public class InteractionDeniedReport implements Report, NotificationTrigger
{
    private String popupMessage = "You cannot use this item.";

    /**
     * @return the message that should be displayed
     */
    public String getPopupMessage()
    {
        return popupMessage;
    }

    @Override
    public String getNotificationTitle()
    {
        return "Interaction denied";
    }

    @Override
    public String getNotificationBody()
    {
        return "Interact Denied: " + getPopupMessage();
    }

    @Override
    public NotificationType getNotificationType()
    {
        return NotificationType.ALERT;
    }
}
