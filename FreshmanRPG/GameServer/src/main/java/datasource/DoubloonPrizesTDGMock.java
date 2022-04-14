package datasource;

import java.util.ArrayList;
import java.util.HashMap;

import dataDTO.DoubloonPrizeDTO;
import datatypes.DoubloonPrizesForTest;


/**
 *
 * @author Andrew M, Christian C.
 *
 * This is the mock gateway that we use to get the prizes from our enum
 * instead of the database
 *
 */

public class DoubloonPrizesTDGMock extends DoubloonPrizesTableDataGateway
{


	private static DoubloonPrizesTDGMock singleton = null;

	private DoubloonPrizesTDGMock()
	{
		resetData();
	}

	/**
	 * gets the instance of the singleton
	 * @return singleton
	 */
	public static synchronized DoubloonPrizesTDGMock getInstance()
	{
		if (singleton == null)
		{
			singleton = new DoubloonPrizesTDGMock();
		}
		return singleton;
	}

	//The hashmap
	private HashMap<String, DoubloonPrizeDTO> mockDoubloonPrizeTable = new HashMap<>();


	/**
	 * Populates the hashmap using the name as the reference
	 */
	public void resetData()
	{
		this.mockDoubloonPrizeTable = new HashMap<>();
		for (DoubloonPrizesForTest prizes : DoubloonPrizesForTest.values())
		{
			this.mockDoubloonPrizeTable.put(prizes.getName(), new DoubloonPrizeDTO(prizes.getName(), prizes.getCost(), prizes.getDescription()));
		}
	}

	/**
	 * Gets all of the prizes from the enum
	 * @return
	 */
	@Override
	public ArrayList<DoubloonPrizeDTO> getAllDoubloonPrizes() throws DatabaseException
	{
		ArrayList<DoubloonPrizeDTO> prizeList = new ArrayList<>();
		prizeList.addAll(this.mockDoubloonPrizeTable.values());
		return prizeList;
	}
}
