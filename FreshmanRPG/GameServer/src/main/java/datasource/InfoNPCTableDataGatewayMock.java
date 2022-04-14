package datasource;

import dataDTO.InfoNPCDTO;
import datatypes.InfoNPCResponsesForTest;


import java.util.ArrayList;
import java.util.HashMap;

public class InfoNPCTableDataGatewayMock extends InfoNPCTableDataGateway
{
    private static InfoNPCTableDataGatewayMock singleton;

    /**
     * @return the only one of these there is
     */
    public static InfoNPCTableDataGateway getSingleton()
    {
        if (singleton == null)
        {
            singleton = new InfoNPCTableDataGatewayMock();
        }
        return singleton;
    }

    private HashMap<Integer, InfoNPCDTO> data = new HashMap<>();

    private InfoNPCTableDataGatewayMock()
    {
        for (InfoNPCResponsesForTest response : InfoNPCResponsesForTest.values())
        {
            data.put(response.getNpcID(), new InfoNPCDTO(response.getResponse(), response.getInformation(), response.getNpcID()));
        }
    }

    @Override
    public ArrayList<InfoNPCDTO> getAllInfoForNPC(int npcID) throws DatabaseException
    {
        ArrayList<InfoNPCDTO> results = new ArrayList<>();
        for (InfoNPCDTO x : data.values())
        {
            if (x.getNpcID() == npcID)
            {
                results.add(x);
            }
        }
        return results;
    }
}
