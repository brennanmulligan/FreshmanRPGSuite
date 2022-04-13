package model.reports;

import datatypes.AdventureStateEnum;
import model.QualifiedObservableReport;

import java.util.Objects;

/**
 * This class will send a report that contains the strings of adventures that
 * are currently of state "needing notification" so that the client can be
 * informed of their completion.
 *
 * @author nk3668 & ew4344
 */
public final class AdventureNeedingNotificationReport implements
		QualifiedObservableReport
{

	private final int questID;
	private final int adventureID;
	private final int playerID;
	private final String adventureDescription;
	private final AdventureStateEnum state;
	private final boolean realLifeAdventure;
	private final String witnessTitle;

	/**
	 * Constructor
	 *
	 * @param playerID
	 *            id of the player
	 * @param questID
	 *            id of the quest
	 * @param adventureID
	 *            id of the adventure
	 * @param adventureDescription
	 *            the description of the adventure
	 * @param state
	 *            the state of the adventure for this player
	 * @param realLifeAdventure
	 *            true if the player must complete this adventure in real life
	 * @param witnessTitle
	 *            if this is a real life adventure, this will hold the title of
	 *            the person who can witness its completion
	 */
	public AdventureNeedingNotificationReport(int playerID, int questID, int adventureID,
											  String adventureDescription, AdventureStateEnum state,
											  boolean realLifeAdventure, String witnessTitle)
	{
		this.playerID = playerID;
		this.questID = questID;
		this.adventureID = adventureID;
		this.adventureDescription = adventureDescription;
		this.state = state;
		this.realLifeAdventure = realLifeAdventure;
		this.witnessTitle = witnessTitle;
	}


	/**
	 * @return description of adventure
	 */
	public String getAdventureDescription()
	{
		return adventureDescription;
	}

	/**
	 * @return id of the adventure
	 */
	public int getAdventureID()
	{
		return adventureID;
	}

	/**
	 * @return id of the player
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @return id of the quest
	 */
	public int getQuestID()
	{
		return questID;
	}

	/**
	 * @return the state of this adventure for this player
	 */
	public AdventureStateEnum getState()
	{
		return state;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}
		AdventureNeedingNotificationReport that = (AdventureNeedingNotificationReport) o;
		return questID == that.questID && adventureID == that.adventureID && playerID == that.playerID &&
				realLifeAdventure == that.realLifeAdventure &&
				Objects.equals(adventureDescription, that.adventureDescription) && state == that.state &&
				Objects.equals(witnessTitle, that.witnessTitle);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(questID, adventureID, playerID, adventureDescription, state, realLifeAdventure, witnessTitle);
	}

	/**
	 * @return true if the player must complete this adventure in real life
	 */
	public boolean isRealLifeAdventure()
	{
		return realLifeAdventure;
	}

	/**
	 * @return the title of the witness who can vouch for completion if this is
	 *         a real life title
	 */
	public String getWitnessTitle()
	{
		return witnessTitle;
	}
}
