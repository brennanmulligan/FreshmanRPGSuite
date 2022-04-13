package model.reports;

import model.QualifiedObservableReport;

/**
 *
 * @author Andy, Emmanuel, Adam, and Truc
 *
 */
public class DisplayTextReport implements QualifiedObservableReport
{
	private String text;

	/**
	 * report constructor
	 * @param text - text to be displayed
	 */
	public DisplayTextReport(String text)
	{
		this.text = text;
	}

	/**
	 *
	 * @return the text to be displayed
	 */
	public String getText()
	{
		return text;
	}
}
