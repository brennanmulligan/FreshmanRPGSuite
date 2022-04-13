package communication.messages;

import java.io.Serializable;
import java.util.ArrayList;

import dataDTO.ClientPlayerQuestStateDTO;
import dataDTO.FriendDTO;
import datasource.LevelRecord;

/**
 * @author Merlin
 * @author Olivia
 * @author LaVonne
 */
public class InitializeThisClientsPlayerMessage implements Message, Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<ClientPlayerQuestStateDTO> clientPlayerQuestList;
	private ArrayList<FriendDTO> friends;
	private int experiencePts;
	private LevelRecord level;
	private int knowledgePoints;


	/**
	 * @param clientPlayerQuestList players quest list
	 * @param experiencePts player's experience points
	 * @param level LevelRecord
	 * @param knowledgePoints for this player
	 * @param playerID of this player
	 */
	public InitializeThisClientsPlayerMessage(ArrayList<ClientPlayerQuestStateDTO> clientPlayerQuestList, ArrayList<FriendDTO> friends, int experiencePts,
											  int knowledgePoints, LevelRecord level)
	{
		this.friends = friends;
		this.clientPlayerQuestList = clientPlayerQuestList;
		this.experiencePts = experiencePts;
		this.knowledgePoints = knowledgePoints;
		this.level = level;
	}

	/**
	 * Return current players quest List
	 *
	 * @return quest list
	 */
	public ArrayList<ClientPlayerQuestStateDTO> getClientPlayerQuestList()
	{
		return clientPlayerQuestList;
	}


	/**
	 * Return current players friend list
	 * @return friend list
	 */
	public ArrayList<FriendDTO> getFriends()
	{
		return friends;
	}

	/**
	 * Get experience points of this client's player
	 *
	 * @return experience pts
	 */
	public int getExperiencePts()
	{
		return experiencePts;
	}

	/**
	 * Get level of this client's player
	 *
	 * @return level
	 */
	public LevelRecord getLevel()
	{
		return level;
	}

	/**
	 * @return the knowledgePoints of this player
	 */
	public int getKnowledgePoints()
	{
		return knowledgePoints;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientPlayerQuestList == null) ? 0 : clientPlayerQuestList.hashCode());
		result = prime * result + experiencePts;
		result = prime * result + ((friends == null) ? 0 : friends.hashCode());
		result = prime * result + knowledgePoints;
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(Object)
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
		if (!(obj instanceof InitializeThisClientsPlayerMessage))
		{
			return false;
		}
		InitializeThisClientsPlayerMessage other = (InitializeThisClientsPlayerMessage) obj;
		if (clientPlayerQuestList == null)
		{
			if (other.clientPlayerQuestList != null)
			{
				return false;
			}
		}
		else if (!clientPlayerQuestList.equals(other.clientPlayerQuestList))
		{
			return false;
		}
		if (experiencePts != other.experiencePts)
		{
			return false;
		}
		if (friends == null)
		{
			if (other.friends != null)
			{
				return false;
			}
		}
		else if (!friends.equals(other.friends))
		{
			return false;
		}
		if (knowledgePoints != other.knowledgePoints)
		{
			return false;
		}
		if (level == null)
		{
			if (other.level != null)
			{
				return false;
			}
		}
		else if (!level.equals(other.level))
		{
			return false;
		}
		return true;
	}
}
