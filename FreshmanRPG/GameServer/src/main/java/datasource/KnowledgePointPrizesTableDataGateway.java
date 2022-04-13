package datasource;

import java.util.ArrayList;

import dataDTO.KnowledgePointPrizeDTO;


/**
 * @author Mina Kindo, Christian Chroutamel
 *
 */
public abstract class KnowledgePointPrizesTableDataGateway
{

	/**
	 * used for testing to reset the data back to a known state
	 */
	public abstract void resetData();

	/**
	 * @return list of all knowledge prizes available
	 * @throws DatabaseException
	 */
	public abstract ArrayList<KnowledgePointPrizeDTO> getAllKnowledgePointPrizes() throws DatabaseException;

}
