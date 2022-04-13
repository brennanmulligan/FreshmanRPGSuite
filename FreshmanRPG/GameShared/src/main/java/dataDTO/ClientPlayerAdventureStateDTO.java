package dataDTO;

import java.io.Serializable;

import datatypes.AdventureStateEnum;
import datatypes.QuestStateEnum;

/**
 * Stores the adventure for the GameClient which encapsulates the AdventureState
 * and Adventure on the game server
 *
 * @author Nathaniel
 *
 */
public class ClientPlayerAdventureStateDTO implements Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private int adventureID, adventureXP;

	private String adventureDescription;
	private AdventureStateEnum adventureState;
	private boolean needingNotification;
	private boolean realLifeAdventure;
	private String witnessTitle;
	private QuestStateEnum questState;

	/**
	 * Basic constructor for ClientPlayerAdventure
	 *
	 * @param adventureID unique identifier for this adventure
	 * @param adventureDescription description of the adventure
	 * @param adventureXP xp reward for adventure
	 * @param adventureState current state of this adventure using the
	 *            AdventureStateList enum
	 * @param needingNotification true if the player needs to be told about the
	 *            state of this adventure
	 * @param realLifeAdventure true if the player completes this adventure
	 *            outside of the game
	 * @param witnessTitle if this is a real life adventure, the title of the
	 *            person who can witness completion
	 * @param qs the state of the quest that the adventure belongs to
	 */
	public ClientPlayerAdventureStateDTO(int adventureID, String adventureDescription, int adventureXP,
										 AdventureStateEnum adventureState, boolean needingNotification, boolean realLifeAdventure,
										 String witnessTitle, QuestStateEnum qs)
	{
		this.adventureID = adventureID;
		this.adventureXP = adventureXP;
		this.adventureDescription = adventureDescription;
		this.adventureState = adventureState;
		this.needingNotification = needingNotification;
		this.realLifeAdventure = realLifeAdventure;
		this.witnessTitle = witnessTitle;
		this.questState = qs;
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
		ClientPlayerAdventureStateDTO other = (ClientPlayerAdventureStateDTO) obj;
		if (adventureDescription == null)
		{
			if (other.adventureDescription != null)
			{
				return false;
			}
		}
		else if (!adventureDescription.equals(other.adventureDescription))
		{
			return false;
		}
		if (adventureID != other.adventureID)
		{
			return false;
		}
		if (adventureState != other.adventureState)
		{
			return false;
		}
		if (adventureXP != other.adventureXP)
		{
			return false;
		}
		if (needingNotification != other.needingNotification)
		{
			return false;
		}
		return true;
	}

	/**
	 * Retrieve the adventure's description
	 *
	 * @return adventureDescription ; The description of the adventure
	 */
	public String getAdventureDescription()
	{
		return adventureDescription;
	}

	/**
	 * Retrieve the adventure's ID
	 *
	 * @return adventureID ; the adventure's unique identifier
	 */
	public int getAdventureID()
	{
		return adventureID;
	}

	/**
	 * Retrieve the adventure's state
	 *
	 * @return adventureState ; The current state of the adventure. Uses the
	 *         enum AdventureStateList
	 */
	public AdventureStateEnum getAdventureState()
	{
		return adventureState;
	}

	/**
	 * @return the adventureXP
	 */
	public int getAdventureXP()
	{
		return adventureXP;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adventureDescription == null) ? 0 : adventureDescription.hashCode());
		result = prime * result + adventureID;
		result = prime * result + ((adventureState == null) ? 0 : adventureState.hashCode());
		result = prime * result + adventureXP;
		result = prime * result + (needingNotification ? 1231 : 1237);
		return result;
	}

	/**
	 * @return true if we should notify the player about this adventure's state
	 */
	public boolean isNeedingNotification()
	{
		return needingNotification;
	}

	/**
	 * @param adventureDescription the adventureDescription to set
	 */
	public void setAdventureDescription(String adventureDescription)
	{
		this.adventureDescription = adventureDescription;
	}

	/**
	 * @param adventureID the adventureID to set
	 */
	public void setAdventureID(int adventureID)
	{
		this.adventureID = adventureID;
	}

	/**
	 * @param adventureState the adventureState to set
	 */
	public void setAdventureState(AdventureStateEnum adventureState)
	{
		this.adventureState = adventureState;
	}

	/**
	 * @param adventureXP the adventureXP to set
	 */
	public void setAdventureXP(int adventureXP)
	{
		this.adventureXP = adventureXP;
	}

	/**
	 * @return true if this adventure must be completed in real life
	 */
	public boolean isRealLifeAdventure()
	{
		return realLifeAdventure;
	}

	/**
	 * @return if this is a real life adventure, the title of the person who can
	 *         witness completion
	 */
	public String getWitnessTitle()
	{
		return witnessTitle;
	}

	/**
	 * @return the state of the quest that the adventure belongs to
	 */
	public QuestStateEnum getQuestState()
	{
		return questState;
	}

}
