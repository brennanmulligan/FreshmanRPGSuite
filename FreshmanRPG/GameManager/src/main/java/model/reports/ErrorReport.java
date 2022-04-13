package model.reports;

import model.QualifiedObservableReport;

/**
 * This report contains a message about invalid input into anything
 * @author Abe Loscher and Chris Boyer
 *
 */
public class ErrorReport implements QualifiedObservableReport
{

	private String message;

	/**
	 * Constructor for InvalidInputReport
	 * @param message
	 * 		Message detailing what was invalid
	 */
	public ErrorReport(String message)
	{
		this.message = message;
	}

	/**
	 * Getter for message
	 * @return
	 *        report's message
	 */
	public String getMessage()
	{
		return this.message;
	}

}
