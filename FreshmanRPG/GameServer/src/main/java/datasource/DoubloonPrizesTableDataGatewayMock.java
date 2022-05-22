package datasource;

import dataDTO.DoubloonPrizeDTO;
import datatypes.DoubloonPrizesForTest;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author Andrew M, Christian C.
 * <p>
 * This is the mock gateway that we use to get the prizes from our enum
 * instead of the database
 */

public class DoubloonPrizesTableDataGatewayMock
        implements DoubloonPrizesTableDataGateway
{

    private HashMap<String, DoubloonPrizeDTO> mockDoubloonPrizeTable = new HashMap<>();

    private DoubloonPrizesTableDataGatewayMock()
    {
        resetTableGateway();
    }

    static TableDataGateway getGateway()
    {
        return new DoubloonPrizesTableDataGatewayMock();
    }

    /**
     * Gets all of the prizes from the enum
     */
    @Override
    public ArrayList<DoubloonPrizeDTO> getAllDoubloonPrizes()
    {
        return new ArrayList<>(this.mockDoubloonPrizeTable.values());
    }

    /**
     * Populates the hashmap using the name as the reference
     */
    public void resetTableGateway()
    {
        this.mockDoubloonPrizeTable = new HashMap<>();
        for (DoubloonPrizesForTest prizes : DoubloonPrizesForTest.values())
        {
            this.mockDoubloonPrizeTable.put(prizes.getName(),
                    new DoubloonPrizeDTO(prizes.getName(), prizes.getCost(),
                            prizes.getDescription()));
        }
    }
}
