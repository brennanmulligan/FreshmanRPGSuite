package datasource;

import java.util.ArrayList;
import java.util.HashMap;

import dataDTO.RandomFactDTO;
import datatypes.RandomFactsForTest;

/**
 * @author merlin
 *
 */
public class RandomFactsTableDataGatewayMock extends RandomFactsTableDataGateway
{

	private static RandomFactsTableDataGatewayMock singleton;

	/**
	 * @return the only one of these there is
	 */
	public static RandomFactsTableDataGateway getSingleton()
	{
		if (singleton == null)
		{
			singleton = new RandomFactsTableDataGatewayMock();
		}
		return singleton;
	}

	private HashMap<Integer, RandomFactDTO> data = new HashMap<>();

	private RandomFactsTableDataGatewayMock()
	{
		for (RandomFactsForTest fact : RandomFactsForTest.values())
		{
			data.put(fact.getRandomFactID(), new RandomFactDTO(fact.getRandomFactID(), fact.getNpcID(),
					fact.getFactText(), fact.getStartDate(), fact.getEndDate()));
		}
	}

	/**
	 * @see datasource.RandomFactsTableDataGateway#getAllFactsForNPC(int)
	 */
	@Override
	public ArrayList<RandomFactDTO> getAllFactsForNPC(int npcID)
	{
		ArrayList<RandomFactDTO> results = new ArrayList<>();
		for (RandomFactDTO x : data.values())
		{
			if (x.getNpcID() == npcID)
			{
				results.add(x);
			}
		}
		return results;
	}
}
