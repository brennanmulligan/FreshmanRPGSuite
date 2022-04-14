package datasource;

import dataDTO.RoamingNPCPathsDTO;

import java.util.ArrayList;

public abstract class RoamingNPCTableDataGateway
{
    public abstract ArrayList<RoamingNPCPathsDTO> getAllPathsForNPC(int npcID) throws DatabaseException;
}
