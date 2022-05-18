package datasource;

import java.util.ArrayList;

import dataDTO.DoubloonPrizeDTO;


/**
 * @author Mina Kindo, Christian Chroutamel
 *
 */
public abstract class DoubloonPrizesTableDataGateway implements TableDataGateway
{

	/**
	 * @return list of all doubloon prizes available
	 */
	public abstract ArrayList<DoubloonPrizeDTO> getAllDoubloonPrizes() throws DatabaseException;

}
