package datasource;

import dataDTO.MowreyInfoDTO;
import dataDTO.QuestStateRecordDTO;
import datatypes.QuestStateEnum;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Holds dialog information for the Mowrey NPC
 *
 * @authors Ryan and Ktyal
 */
public interface MowreyInfoTableDataGateway
{

    /**
     * Get all of the dialog options for an npc
     *
     * @return a list of states
     * @throws DatabaseException if we can't talk to the data source
     */
    ArrayList<MowreyInfoDTO> getDialogOptions() throws DatabaseException;

    /**
     * Create the table from the data in QuestStatesForTest
     *
     * @throws DatabaseException if we can't talk to the data source
     */
    void createTable() throws DatabaseException;

    /**
     * Put a new row in the table
     *
     * @throws DatabaseException if we can't talk to the data source
     */
    void createRow(int id, String question, String answer, LocalDate startDate, LocalDate endDate)
            throws DatabaseException;

    /**
     * Put data back to its original state - only useful in mock gateways for
     * testing purposes
     */
    void resetData();
}
