package model;

import datasource.DatabaseException;

/**
 * Command that refreshes the list of interactable objects
 *
 * @author Ben Uleau and Abe Loscher
 *
 */
public class CommandGetAllInteractableObjects extends Command
{
	/**
	 * @see model.Command#execute()
	 */
	@Override
	boolean execute()
	{
		try
		{
			GameManagerInteractableObjectManager.getInstance().refreshObjectList();
		}
		catch (DatabaseException e)
		{
			return false;
		}
		return true;
	}

}
