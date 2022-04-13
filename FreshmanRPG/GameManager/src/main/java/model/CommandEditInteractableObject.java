package model;

import dataDTO.InteractableItemDTO;

/**
 * @author Ben Uleau, Chris Boyer Command to edit an interactable object
 */
public class CommandEditInteractableObject extends Command
{
	private InteractableItemDTO interactableItem;

	/**
	 * @param interactableItem
	 *            - an interactable object
	 */
	public CommandEditInteractableObject(InteractableItemDTO interactableItem)
	{
		this.interactableItem = interactableItem;
	}

	/**
	 * @see model.Command#execute()
	 */
	@Override
	boolean execute()
	{
		GameManagerInteractableObjectManager manager = GameManagerInteractableObjectManager.getInstance();
		return manager.updateItem(interactableItem);
	}
}
