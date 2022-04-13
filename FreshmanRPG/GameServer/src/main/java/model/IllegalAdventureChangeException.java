package model;

import datatypes.AdventureStateEnum;

/**
 * Throw an exception if you try to change to a non-legal state.
 *
 * @author nk3668
 *
 */
public class IllegalAdventureChangeException extends Exception
{
	/**
	 * ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The message thrown if you try to change to a state with the wrong
	 * parameters
	 *
	 * @param from the state the adventure is currently in
	 * @param to the state we are trying to transition to
	 */
	public IllegalAdventureChangeException(AdventureStateEnum from, AdventureStateEnum to)
	{
		super("You can't change from " + from.toString() + " to " + to.toString());
	}
}
