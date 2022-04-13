package datasource;

import java.util.ArrayList;

import dataDTO.RandomFactDTO;

/**
 * @author merlin
 *
 */
public abstract class RandomFactsTableDataGateway
{
	/**
	 * @param npcID
	 *            the id of the NPC we are interested
	 * @return all of the facts for the given NPC
	 * @throws DatabaseException if we can't read the facts from the db
	 */
	public abstract ArrayList<RandomFactDTO> getAllFactsForNPC(int npcID) throws DatabaseException;

}
