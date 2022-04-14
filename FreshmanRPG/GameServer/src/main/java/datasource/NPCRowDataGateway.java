package datasource;

/**
 * Behavior required by gateways for the NPC table
 *
 * @author Merlin
 *
 */
public interface NPCRowDataGateway
{

	/**
	 * For Testing Only
	 */
	public void resetData();

	/**
	 *
	 * @return the name of the class encoding the behavior for this NPC
	 */
	public String getBehaviorClass();

	/**
	 *
	 * @return the NPC data file path
	 */
	public String getFilePath();

	/**
	 * This NPCs unique playerID
	 *
	 * @return the player ID
	 */
	public int getPlayerID();

}
