package communication.messages;

import java.io.Serializable;
import java.util.ArrayList;

import dataDTO.DoubloonPrizeDTO;


/**
 *
 * @author Andrew M, Christian C
 *
 * This is the message class for the doubloon prize message
 *
 */
public class DoubloonPrizeMessage implements Message, Serializable
{
	private static final long serialVersionUID = 1L;

	ArrayList<DoubloonPrizeDTO> dtos;
	int playerID;

	/**
	 * Constructor for message 
	 * @param playerID
	 * @param dtos
	 */
	public DoubloonPrizeMessage(int playerID, ArrayList<DoubloonPrizeDTO> dtos)
	{
		this.playerID = playerID;
		this.dtos = dtos;

	}

	/**
	 * Returns the playerID
	 * @return playerID
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @return the list of DTOs 
	 */
	public ArrayList<DoubloonPrizeDTO> getDtos()
	{
		return dtos;
	}

	/**
	 * getter for name
	 * @param index - position of the prize in the list
	 * @return prize name 
	 */
	public String getName(int index)
	{
		return dtos.get(index).getName();
	}

	/**
	 * getter for price
	 * @param index - position of the prize in the list
	 * @return cost of prize
	 */
	public int getPrice(int index)
	{
		return dtos.get(index).getCost();
	}

	/**
	 * getter for description
	 * @param index - position of the prize in the list 
	 * @return description of the prize 
	 */
	public String getDescription(int index)
	{
		return dtos.get(index).getDescription();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dtos == null) ? 0 : dtos.hashCode());
		result = prime * result + playerID;
		return result;
	}

	/**
	 * This is NOT the default generated equals method.  They need to be equal even if
	 * the array of dtos is in a different order, so we replaced dtos.equals(other.dtos) with
	 * containsAll
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
		DoubloonPrizeMessage other = (DoubloonPrizeMessage) obj;
		if (dtos == null)
		{
			if (other.dtos != null)
			{
				return false;
			}
		}
		else if (!dtos.containsAll(other.dtos) || !other.dtos.containsAll(dtos))
		{
			return false;
		}
		if (playerID != other.playerID)
		{
			return false;
		}
		return true;
	}

}
