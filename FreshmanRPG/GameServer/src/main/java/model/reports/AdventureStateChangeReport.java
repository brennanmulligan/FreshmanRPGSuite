package model.reports;

import datatypes.AdventureStateEnum;
import model.QualifiedObservableReport;

import java.util.Objects;

/**
 * Report to the client that an adventure state change had occured.
 *
 * @author nk3668
 *
 */
public final class AdventureStateChangeReport implements QualifiedObservableReport
{
	private final int playerID;
	private final int questID;
	private final int adventureID;
	private final String adventureDescription;
	private final AdventureStateEnum newState;
	private final boolean realLifeAdventure;
	private final String witnessTitle;

	/**
	 * @param id players ID
	 * @param questID id of the quest
	 * @param adventureID adventures ID to change state
	 * @param adventureDescription description of adventure
	 * @param newState new state to be changed to
	 * @param realLifeAdventure true if the player must complete this adventure
	 *            outside of the game
	 * @param witnessTitle the title of the person who can witness completion if
	 *            this is a real life adventure
	 */
	public AdventureStateChangeReport(int id, int questID, int adventureID, String adventureDescription,
									  AdventureStateEnum newState, boolean realLifeAdventure, String witnessTitle)
	{
		this.playerID = id;
		this.questID = questID;
		this.adventureID = adventureID;
		this.adventureDescription = adventureDescription;
		this.newState = newState;
		this.realLifeAdventure = realLifeAdventure;
		this.witnessTitle = witnessTitle;
	}

	/**
	 * @return player ID
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @return adventure ID
	 */
	public int getAdventureID()
	{
		return adventureID;
	}

	/**
	 * @return adventure Description
	 */
	public String getAdventureDescription()
	{
		return adventureDescription;
	}

	/**
	 * @return new State
	 */
	public AdventureStateEnum getNewState()
	{
		return newState;
	}

	/**
	 * @return quest id
	 */
	public int getQuestID()
	{
		return questID;
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
		AdventureStateChangeReport that = (AdventureStateChangeReport) o;
		return playerID == that.playerID && questID == that.questID && adventureID == that.adventureID &&
				realLifeAdventure == that.realLifeAdventure &&
				Objects.equals(adventureDescription, that.adventureDescription) && newState == that.newState &&
				Objects.equals(witnessTitle, that.witnessTitle);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(playerID, questID, adventureID, adventureDescription, newState, realLifeAdventure, witnessTitle);
	}

	/**
	 * @return true if the player must complete this adventure in real life
	 */
	public boolean isRealLifeAdventure()
	{
		return realLifeAdventure;
	}

	/**
	 * @return if the player must complete this adventure in real life, the
	 *         title of the person who can witness completion
	 */
	public String getWitnessTitle()
	{
		return witnessTitle;
	}

}
