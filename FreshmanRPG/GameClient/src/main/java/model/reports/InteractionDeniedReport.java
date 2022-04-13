package model.reports;

import model.QualifiedObservableReport;

/**
 * The report used when an interaction was denied, for any reason.
 */
public class InteractionDeniedReport implements QualifiedObservableReport
{
	private String popupMessage = "You cannot use this item.";

	/**
	 * @return the message that should be displayed
	 */
	public String getPopupMessage()
	{
		return popupMessage;
	}
}
