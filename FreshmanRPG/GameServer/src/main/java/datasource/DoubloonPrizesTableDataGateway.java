package datasource;

import java.util.ArrayList;

import dataDTO.DoubloonPrizeDTO;


/**
 * @author Mina Kindo, Christian Chroutamel
 *
 */
public interface DoubloonPrizesTableDataGateway extends TableDataGateway
{

	/**
	 * @return list of all doubloon prizes available
	 */
	ArrayList<DoubloonPrizeDTO> getAllDoubloonPrizes() throws DatabaseException;

}
