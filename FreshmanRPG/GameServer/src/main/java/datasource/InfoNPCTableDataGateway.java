package datasource;

import dataDTO.InfoNPCDTO;


import java.util.ArrayList;

/**
 * @author John Lang and Noah Macminn
 * gateway for all our player response/info npc information provided by that response
 */
public abstract class InfoNPCTableDataGateway
{

    public abstract ArrayList<InfoNPCDTO> getAllInfoForNPC(int npcID) throws DatabaseException;

}
