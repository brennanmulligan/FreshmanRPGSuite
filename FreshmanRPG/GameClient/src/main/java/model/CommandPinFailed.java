package model;

/**
 * @author Andy and Matt
 *
 *         Creates the CommandPinFailed
 *
 */
public class CommandPinFailed extends Command
{
	private final String err;

	/**
	 * @param msg
	 *            is the error message
	 */
	public CommandPinFailed(String msg)
	{
		err = msg;
	}

	/**
	 * @see Command#execute()
	 */
	@Override
	boolean execute()
	{
		ClientPlayerManager.getSingleton().pinFailed(err);

		return true;
	}

}
