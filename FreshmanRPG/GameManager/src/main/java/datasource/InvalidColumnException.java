package datasource;

/**
 * Represents an exception where there were invalid columns.
 */
public class InvalidColumnException extends Exception
{
	private static final long serialVersionUID = 1L;

	/**
	 * Standard.
	 *
	 * @param message - a message to the user
	 */
	public InvalidColumnException(String message)
	{
		super(message);
	}

	/**
	 * For exception chaining.
	 *
	 * @param message - a message to the user
	 * @param exception - chained
	 */
	public InvalidColumnException(String message, Exception exception)
	{
		super(message, exception);
	}
}
