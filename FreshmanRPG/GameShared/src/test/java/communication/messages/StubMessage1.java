package communication.messages;

import java.io.Serializable;

/**
 * Used to log a player into a server
 *
 * @author merlin
 *
 */
public class StubMessage1 implements Message, Serializable
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "This is a stub message (1) - why are you outputting it!";
	}

}
