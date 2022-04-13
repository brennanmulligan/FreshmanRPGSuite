package datasource;

import java.util.ArrayList;
import java.util.HashMap;

import dataDTO.InteractableItemDTO;
import dataDTO.KnowledgePointPrizeDTO;
import datatypes.KnowledgePointPrizesForTest;


/**
 *
 * @author Andrew M, Christian C.
 *
 * This is the mock gateway that we use to get the prizes from our enum
 * instead of the database
 *
 */

public class KnowledgePointPrizesTDGMock extends KnowledgePointPrizesTableDataGateway
{


	private static KnowledgePointPrizesTDGMock singleton = null;

	private KnowledgePointPrizesTDGMock()
	{
		resetData();
	}

	/**
	 * gets the instance of the singleton
	 * @return singleton
	 */
	public static synchronized KnowledgePointPrizesTDGMock getInstance()
	{
		if (singleton == null)
		{
			singleton = new KnowledgePointPrizesTDGMock();
		}
		return singleton;
	}

	//The hashmap
	private HashMap<String, KnowledgePointPrizeDTO> mockKnowledgePointPrizeTable = new HashMap<>();


	/**
	 * Populates the hashmap using the name as the reference
	 */
	public void resetData()
	{
		this.mockKnowledgePointPrizeTable = new HashMap<>();
		for (KnowledgePointPrizesForTest prizes : KnowledgePointPrizesForTest.values())
		{
			this.mockKnowledgePointPrizeTable.put(prizes.getName(), new KnowledgePointPrizeDTO(prizes.getName(), prizes.getCost(), prizes.getDescription()));
		}
	}

	/**
	 * Gets all of the prizes from the enum
	 * @return
	 */
	@Override
	public ArrayList<KnowledgePointPrizeDTO> getAllKnowledgePointPrizes() throws DatabaseException
	{
		ArrayList<KnowledgePointPrizeDTO> prizeList = new ArrayList<>();
		prizeList.addAll(this.mockKnowledgePointPrizeTable.values());
		return prizeList;
	}
}
