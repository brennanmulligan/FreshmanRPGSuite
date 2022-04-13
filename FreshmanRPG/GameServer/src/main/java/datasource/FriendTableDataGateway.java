package datasource;

import java.util.ArrayList;

import dataDTO.FriendDTO;
import datatypes.FriendStatusEnum;


/**
 *
 * @author Christian C, Andrew M
 * @Description interface for the friend gateways 
 *
 */
public interface FriendTableDataGateway
{

	/**
	 * used for testing to reset the data back to a known state
	 */
	public abstract void resetData();

	/**
	 * @return the list of friends 
	 * @throws DatabaseException
	 */
	public abstract ArrayList<FriendDTO> getAllFriends(int id) throws DatabaseException;

	public abstract void add(int playerID, String friendName, FriendStatusEnum status) throws DatabaseException;

	public abstract int accept(int playerID, String friendName) throws DatabaseException;

	public abstract int getFriendCounter(int id) throws DatabaseException;

	public String getSpecificNameFromId(int playerId);

	public int getSpecificIDFromName(String playerName);

}
