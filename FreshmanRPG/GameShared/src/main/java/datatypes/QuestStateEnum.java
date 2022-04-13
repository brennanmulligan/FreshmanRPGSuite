package datatypes;

/**
 * The legal states a quest can be in for a given player
 *
 * @author Merlin
 *
 */
public enum QuestStateEnum
{


	/**
	 * The player can know that there is a quest, but knows nothing about it
	 */
	AVAILABLE("can be triggered"),
	/**
	 * The player can see the description of the quest and the adventures within
	 * it
	 */
	TRIGGERED("has been triggered"),
	/**
	 * The player has completed enough adventures to get the points for this
	 * quest
	 */
	FULFILLED("has been fulfilled"),
	/**
	 * The player has completed all of the adventures within this quest
	 */
	COMPLETED("is complete"),
	/**
	 * The player ran out of time to complete the quest
	 */
	EXPIRED("is expired"),
	;

	private String description;

	/**
	 * @return the English description of this state
	 */
	public String getDescription()
	{
		return description;
	}

	QuestStateEnum(String description)
	{
		this.description = description;
	}

	/**
	 * @return the unique id of the enum object
	 */
	public int getID()
	{
		return this.ordinal();
	}
}
