package datasource;

import dataDTO.RoamingNPCPathsDTO;
import datatypes.RoamingNPCPathsForTest;

import java.util.ArrayList;
import java.util.HashMap;

public class RoamingNPCTableDataGatewayMock extends RoamingNPCTableDataGateway
{
    private static RoamingNPCTableDataGatewayMock singleton;

    /**
     * @return the only one of these there is
     */
    public static RoamingNPCTableDataGateway getSingleton()
    {
        if (singleton == null)
        {
            singleton = new RoamingNPCTableDataGatewayMock();
        }
        return singleton;
    }

    private HashMap<Integer, RoamingNPCPathsDTO> data = new HashMap<>();

    private RoamingNPCTableDataGatewayMock()
    {
        for (RoamingNPCPathsForTest path : RoamingNPCPathsForTest.values())
        {
            data.put(path.getNpcID(), new RoamingNPCPathsDTO(path.getPath(), path.getNpcID()));
        }
    }


    @Override
    public ArrayList<RoamingNPCPathsDTO> getAllPathsForNPC(int npcID) throws DatabaseException
    {
        ArrayList<RoamingNPCPathsDTO> results = new ArrayList<>();
        for (RoamingNPCPathsDTO x : data.values())
        {
            if (x.getNpcID() == npcID)
            {
                results.add(x);
            }
        }
        return results;
    }
}
