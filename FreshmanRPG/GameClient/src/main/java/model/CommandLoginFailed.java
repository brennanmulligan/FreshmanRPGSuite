package model;


/**
 * @author Dave, Andy, Matt
 *
 *Creates the CommandLoginFailed
 *
 */
public class CommandLoginFailed extends Command
{

	private static String message = "";


	public CommandLoginFailed()
	{

	}

	public CommandLoginFailed(String message)
	{
		this.message = message;
	}


	/**
	 * @see Command#execute()
	 */
	@Override
	boolean execute()
	{

		ClientPlayerManager.getSingleton().loginFailed(message);

		return true;
	}

}
