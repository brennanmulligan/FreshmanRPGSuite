package api.datasource;

import java.util.ArrayList;

import datasource.DatabaseException;
import model.QuestRecord;

/**
 * Encapsulates access to the Quest database table.
 */
public interface QuestTableDataGateway
{
    /**
     * Sets test data back to the initial state.
     */
    void resetData();

    /**
     * Return a list of all quests.
     *
     * @return A list of quests in the database.
     * @throws DatabaseException - problem reading the Quests table
     */
    ArrayList<QuestRecord> getAllQuests() throws DatabaseException;
}
