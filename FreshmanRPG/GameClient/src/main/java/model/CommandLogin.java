package model;


/**
 * @author Merlin
 *
 */
public class CommandLogin extends Command
{

	private String name;
	private String password;

	/**
	 * @param name
	 *            the player's name
	 * @param password
	 *            the password
	 */
	public CommandLogin(String name, String password)
	{
		this.name = name;
		this.password = password;
	}

	/**
	 * @see Command#execute()
	 */
	@Override
	boolean execute()
	{
		ClientPlayerManager p = ClientPlayerManager.getSingleton();
		p.initiateLogin(name, password);
		return true;
	}

}
