package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFormattedTextField.AbstractFormatter;


/**
 *	An implemented AbstractFormatter that formats into a date format.
 * @author Brad Olah
 *  Referencing http://stackoverflow.com/a/26794863
 */
public class DateLabelFormatter extends AbstractFormatter
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String datePattern = "MMM dd,yyyy";
	private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

	/**
	 * @see javax.swing.JFormattedTextField.AbstractFormatter#stringToValue(java.lang.String)
	 */
	@Override
	public Object stringToValue(String text) throws ParseException
	{
		return dateFormatter.parseObject(text);
	}

	/**
	 * @see javax.swing.JFormattedTextField.AbstractFormatter#valueToString(java.lang.Object)
	 */
	@Override
	public String valueToString(Object value) throws ParseException
	{
		if (value != null)
		{
			Calendar cal = (Calendar) value;
			return dateFormatter.format(cal.getTime());
		}
		return "";
	}
}