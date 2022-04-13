package model.reports;

import java.util.ArrayList;

import dataDTO.InteractableItemDTO;
import dataENUM.InteractableItemActionType;
import model.QualifiedObservableReport;

/**
 * This report contains the list of interactable objects
 * @author Ben Uleau and Abe Loscher
 *
 */
public class ObjectListReport implements QualifiedObservableReport
{
	private ArrayList<InteractableItemDTO> objects;

	/**
	 * Constructor for ObjectListReport
	 * @param objects list of interactable objects
	 */
	public ObjectListReport(ArrayList<InteractableItemDTO> objects)
	{
		this.objects = objects;
	}

	/**
	 * Getter for objects
	 * @return list of interactable objects
	 */
	public ArrayList<InteractableItemDTO> getObjects()
	{
		return objects;
	}

	/**
	 * Getter for message boards
	 * @return list of message boards
	 */
	public ArrayList<InteractableItemDTO> getMessageBoards()
	{
		ArrayList<InteractableItemDTO> justMessages = new ArrayList<>();
		for (InteractableItemDTO o : objects)
		{
			if (o.getActionType() == InteractableItemActionType.BOARD)
			{
				justMessages.add(o);
			}
		}
		return justMessages;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((objects == null) ? 0 : objects.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		ObjectListReport other = (ObjectListReport) obj;
		if (objects == null)
		{
			if (other.objects != null)
			{
				return false;
			}
		}
		else if (!objects.equals(other.objects))
		{
			return false;
		}
		return true;
	}
}
