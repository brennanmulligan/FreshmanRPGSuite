package datasource;

import java.util.ArrayList;

import dataDTO.FriendDTO;
import datatypes.FriendStatusEnum;


/**
 *
 * @author Christian C, Andrew M
 *
 */
public interface FriendTableDataGateway extends TableDataGateway
{

	/**
	 * used for testing to reset the data back to a known state
	 */
	void resetTableGateway();

	/**
	 * @return the list of friends
	 */
	ArrayList<FriendDTO> getAllFriends(int id) throws DatabaseException;

	void add(int playerID, String friendName, FriendStatusEnum status) throws DatabaseException;

	int accept(int playerID, String friendName) throws DatabaseException;

	int getFriendCounter(int id) throws DatabaseException;

	String getSpecificNameFromId(int playerId);

	int getSpecificIDFromName(String playerName);

}
