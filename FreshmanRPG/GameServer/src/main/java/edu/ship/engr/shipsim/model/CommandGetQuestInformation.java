package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DuplicateNameException;

import java.util.List;

public class CommandGetQuestInformation extends Command
{
    private final String requestedOperation;

    private List<String> mapNames;

    public CommandGetQuestInformation(String requestedOperation){
        this.requestedOperation = requestedOperation;
    }

    /**
     * Since we need to do three operations in one command,
     * we'll use a string to determine which data to return.
     * @throws DuplicateNameException
     */
    @Override
    void execute() throws DuplicateNameException
    {
        if(requestedOperation.equals("GetMapNames")){
            //Create a mapTableDataGateway and get all maps
            //go through each mapDTO and fill mapName with the
            //names of each map
            //create new GetAllQuestNames report and pass in mapNames
            //queue report to get sent out
        }

    }
}
