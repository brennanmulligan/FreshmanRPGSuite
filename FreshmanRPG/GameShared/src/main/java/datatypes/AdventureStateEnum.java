package datatypes;

/**
 * A list of the states an adventure can be in
 *
 * @author Merlin
 *
 */
public enum AdventureStateEnum
{
	/**
	 * This adventure isn't yet available to the players. When this adventure's
	 * quest has yet to be triggered.
	 */
	HIDDEN(""),
	/**
	 * Adventure is ready to be completed.
	 */
	TRIGGERED("has triggered"),

	/**
	 * Player has been notified, nothing left to do.
	 */
	COMPLETED("is completed"),

	/**
	 * Adventure has expired because quest is expired
	 */
	EXPIRED("is expired");

	private String description;

	/**
	 * @return the English description of this adventure state
	 */
	public String getDescription()
	{
		return description;
	}

	AdventureStateEnum(String description)
	{
		this.description = description;
	}

	/**
	 * @return the unique id of the enum
	 */
	public int getID()
	{
		return this.ordinal();
	}

}
