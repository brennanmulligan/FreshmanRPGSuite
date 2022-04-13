package model;

import criteria.InteractableItemActionParameter;
import dataENUM.InteractableItemActionType;
import datasource.DatabaseException;
import datatypes.Position;

/**
 * Command to add an interactable object to the database.
 * @author Jordan Long
 *
 */
public class CommandAddInteractableObject extends Command
{
	private String name;
	private Position position;
	private InteractableItemActionType actionType;
	private InteractableItemActionParameter actionParam;
	private String mapName;


	/**
	 * Construct the command to add an Item
	 * @param name - Name of the player
	 * @param position - Position of the item 
	 * @param actionType - Action type to take when interacted with
	 * @param actionParam - Parameter for the action type
	 * @param mapName - Map name to teleport to if needed
	 */
	public CommandAddInteractableObject(String name, Position position, InteractableItemActionType actionType,
										InteractableItemActionParameter actionParam, String mapName)
	{
		this.name = name;
		this.position = position;
		this.actionType = actionType;
		this.actionParam = actionParam;
		this.mapName = mapName;
	}


	/**
	 * Add the player with the player manager
	 */
	@Override
	boolean execute()
	{
		try
		{
			GameManagerInteractableObjectManager manager = GameManagerInteractableObjectManager.getInstance();
			manager.addObject(this.name, this.mapName, this.position, this.actionType, this.actionParam);
		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
		}
		return true;

	}
}
