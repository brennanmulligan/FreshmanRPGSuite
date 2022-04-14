package model.reports;

import view.screen.notification.NotificationType;

/**
 * Reports that should trigger notifications should implement this interface.
 * It simply ensures that the report contains the necessary info for displaying
 * a notification.
 */
public interface NotificationTrigger
{
    String getNotificationTitle();
    String getNotificationBody();
    NotificationType getNotificationType();
}
