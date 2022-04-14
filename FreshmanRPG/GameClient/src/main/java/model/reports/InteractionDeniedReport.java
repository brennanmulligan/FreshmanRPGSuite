package model.reports;

import model.QualifiedObservableReport;
import view.screen.notification.NotificationType;

/**
 * The report used when an interaction was denied, for any reason.
 */
public class InteractionDeniedReport implements QualifiedObservableReport, NotificationTrigger
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
