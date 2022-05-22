package datasource;

import dataDTO.InfoNPCDTO;
import datatypes.InfoNPCResponsesForTest;


import java.util.ArrayList;
import java.util.HashMap;

public class InfoNPCTableDataGatewayMock extends InfoNPCTableDataGateway
{
    static TableDataGateway getGateway()
    {
        return new InfoNPCTableDataGatewayMock();
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
    public ArrayList<InfoNPCDTO> getAllInfoForNPC(int npcID)
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

    @Override
    public void resetTableGateway()
    {
        data = new HashMap<>();
    }
}
