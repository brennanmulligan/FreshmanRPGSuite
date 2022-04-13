package model;

import datasource.DatabaseException;

/**
 * Command to delete an interactable object
 * @author Mohammed Almaslamani
 *
 */
public class CommandDeleteInteractableObject extends Command
{

	private int objectID;
	private final GameManagerInteractableObjectManager manager = GameManagerInteractableObjectManager.getInstance();

	/**
	 * Constructor to take in interactable object id
	 * @param objectID - id of the interactable object to be deleted.
	 */
	public CommandDeleteInteractableObject(int objectID)
	{
		this.objectID = objectID;
	}


	/**
	 * Executes the command
	 * @return true- success or false-failure 
	 */
	@Override
	boolean execute()
	{
		try
		{
			return manager.removeObjects(this.objectID);
		}
		catch (DatabaseException e)
		{
			return false;
		}
	}

}
