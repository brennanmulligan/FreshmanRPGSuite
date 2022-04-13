package datasource;

/**
 * Creates adventures for the DB
 * @author merlin (copied to client and edited by : cody, scott)
 *
 */
public enum AdventuresForTest
{
	/**
	 * 
	 */
	ONE(1, "Adventure Description 1", 1, 1),
	/**
	 * 
	 */
	TWO(2, "Adventure Description 2", 1, 2),
	/**
	 * 
	 */
	THREE(1, "Another Adventure Description 1", 2, 3),
	/**
	 * 
	 */
	FOUR(2, "Another Adventure Description 2", 2, 4),
	/**
	 * 
	 */
	FIVE(1, "Adventure for Quest 3", 3, 5),
	/**
	 * 
	 */
	ANOTHER_FOR_QUEST_1(3, "One more Adventure", 1, 3),
	/**
	 * 
	 */
	QUEST_4_ADVENTURE_1(1, "Quest 4 Adventure", 4, 5),
	/**
	 * 
	 */
	QUEST_4_ADVENTURE_2(2, "Quest 4 Adventure", 4, 5);
	
	
	private int adventureID;
	private String adventureDescription;
	private int questID;
	private int experiencePointsGained;
	
	/**
	 * Constructor for Adventures Enum
	 * @param adventureID this adventure's unique ID
	 * @param adventureDescription what the player has to do
	 * @param questID the ID of the quest that contains this adventure
	 * @param experiencePointsGained the number of experience points you gain for completing this adventure
	 */
	AdventuresForTest(int adventureID, String adventureDescription, int questID, int experiencePointsGained)
	{
		this.adventureID = adventureID;
		this.adventureDescription = adventureDescription;
		this.questID = questID;
		this.experiencePointsGained = experiencePointsGained;
	}

	/**
	 * @return the adventureDescription
	 */
	public String getAdventureDescription()
	{
		return adventureDescription;
	}

	/**
	 * @return the adventureID
	 */
	public int getAdventureID()
	{
		return adventureID;
	}

	/**
	 * @return the number of points you get for completing the adventure
	 */
	public int getExperiencePointsGained()
	{
		return experiencePointsGained;
	}

	/**
	 * @return the questID
	 */
	public int getQuestID()
	{
		return questID;
	}
	
}