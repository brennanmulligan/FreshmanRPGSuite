package datasource;

import java.util.ArrayList;

import dataDTO.DoubloonPrizeDTO;


/**
 * @author Mina Kindo, Christian Chroutamel
 *
 */
public abstract class DoubloonPrizesTableDataGateway
{

	/**
	 * used for testing to reset the data back to a known state
	 */
	public abstract void resetData();

	/**
	 * @return list of all doubloon prizes available
	 * @throws DatabaseException
	 */
	public abstract ArrayList<DoubloonPrizeDTO> getAllDoubloonPrizes() throws DatabaseException;

}
