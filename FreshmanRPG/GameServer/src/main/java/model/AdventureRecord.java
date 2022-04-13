package model;

import criteria.AdventureCompletionCriteria;
import dataENUM.AdventureCompletionType;

/**
 * Data Transfer Object for the Adventure Data Gateway to deliver records.
 *
 * @author merlin
 *
 */
public final class AdventureRecord
{
	private int adventureID;
	private String adventureDescription;
	private int questID;
	private int experiencePointsGained;
	private AdventureCompletionCriteria completionCriteria;
	private AdventureCompletionType completionType;

	/**
	 * Create it
	 *
	 * @param questID the unique ID of the quest that contains the adventure
	 * @param adventureID the adventure's unique ID
	 * @param adventureDescription the adventure's description
	 * @param experiencePointsGained the number of points earned by completing
	 *            this adventure
	 * @param completionType the type of action the player must do to complete
	 *            this adventure
	 * @param completionCriteria the criteria for satisfying this adventure
	 */
	public AdventureRecord(int questID, int adventureID, String adventureDescription, int experiencePointsGained,
						   AdventureCompletionType completionType, AdventureCompletionCriteria completionCriteria)
	{
		this.adventureID = adventureID;
		this.adventureDescription = adventureDescription;
		this.questID = questID;
		this.experiencePointsGained = experiencePointsGained;
		this.completionType = completionType;
		this.completionCriteria = completionCriteria;
	}

	/**
	 * @return the type of action the player must do to complete this adventure
	 */
	public AdventureCompletionType getCompletionType()
	{
		return completionType;
	}

	/**
	 * retrieve the adventure's ID
	 *
	 * @return adventureID the adventure's unique ID
	 */
	public int getAdventureID()
	{
		return adventureID;
	}

	/**
	 * Added For Support of JavaFX TableView
	 * @return adventureID;
	 */
	public int getAdventureId()
	{
		return this.getAdventureID();
	}


	/**
	 * retrieve the adventures description
	 *
	 * @return adventureDescription
	 */
	public String getAdventureDescription()
	{
		return adventureDescription;
	}

	/**
	 * retrieve the quest's ID
	 *
	 * @return questID the unique ID for the adventure's quest
	 */
	public int getQuestID()
	{
		return questID;
	}

	/**
	 * Added For Support of JavaFX TableView
	 * @return questID;
	 */
	public int getQuestId()
	{
		return this.getQuestID();
	}

	/**
	 * Sets adventure Id
	 * @param adventureID id
	 */
	public void setAdventureID(int adventureID)
	{
		this.adventureID = adventureID;
	}

	/**
	 * Sets adventure description
	 * @param adventureDescription description
	 */
	public void setAdventureDescription(String adventureDescription)
	{
		this.adventureDescription = adventureDescription;
	}

	/**
	 * Sets questID
	 * @param questID id
	 */
	public void setQuestID(int questID)
	{
		this.questID = questID;
	}

	/**
	 * Sets the experiencePointsGained variable
	 * @param experiencePointsGained xp gained
	 */
	public void setExperiencePointsGained(int experiencePointsGained)
	{
		this.experiencePointsGained = experiencePointsGained;
	}

	/**
	 * Sets the completion criteria
	 * @param completionCriteria criteria
	 */
	public void setCompletionCriteria(AdventureCompletionCriteria completionCriteria)
	{
		this.completionCriteria = completionCriteria;
	}

	/**
	 * Sets the completion type
	 * @param completionType type
	 */
	public void setCompletionType(AdventureCompletionType completionType)
	{
		this.completionType = completionType;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "Quest " + questID + ":     " + "Adventure  " + adventureID + "      " + adventureDescription;
	}


	/**
	 * @return the number of points you get when you complete this adventure
	 */
	public int getExperiencePointsGained()
	{
		return experiencePointsGained;
	}

	/**
	 * @return the criteria for completing this adventure
	 */
	public AdventureCompletionCriteria getCompletionCriteria()
	{
		return completionCriteria;
	}

	/**
	 * @return true if the adventure must be completed in real life
	 */
	public boolean isRealLifeAdventure()
	{
		return completionType == AdventureCompletionType.REAL_LIFE;
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
		result = prime * result + ((completionCriteria == null) ? 0 : completionCriteria.hashCode());
		result = prime * result + ((completionType == null) ? 0 : completionType.hashCode());
		result = prime * result + experiencePointsGained;
		result = prime * result + questID;
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
		AdventureRecord other = (AdventureRecord) obj;
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
		if (completionCriteria == null)
		{
			if (other.completionCriteria != null)
			{
				return false;
			}
		}
		else if (!completionCriteria.equals(other.completionCriteria))
		{
			return false;
		}
		if (completionType != other.completionType)
		{
			return false;
		}
		if (experiencePointsGained != other.experiencePointsGained)
		{
			return false;
		}
		if (questID != other.questID)
		{
			return false;
		}
		return true;
	}
}
