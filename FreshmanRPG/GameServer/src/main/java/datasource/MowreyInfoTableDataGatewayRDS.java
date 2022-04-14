package datasource;


import dataDTO.MowreyInfoDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * RDS implementation of MowreyInfoTableDataGateway
 *
 * @authors Ryan and Ktyal
 */
public class MowreyInfoTableDataGatewayRDS implements MowreyInfoTableDataGateway
{
    private static MowreyInfoTableDataGatewayRDS singleton;

    /**
     * @return the one and only
     */
    public static synchronized MowreyInfoTableDataGatewayRDS getSingleton()
    {
        if (singleton == null)
        {
            singleton = new MowreyInfoTableDataGatewayRDS();
        }
        return singleton;
    }

    /**
     * A private constructor only called by the getSingleton method
     */
    private MowreyInfoTableDataGatewayRDS()
    {
        //do nothing this just explicitly makes it private
    }

    @Override
    public ArrayList<MowreyInfoDTO> getDialogOptions() throws DatabaseException
    {
        return null;
    }

    /**
     * Drop the table if it exists and re-create it empty
     *
     * @throws DatabaseException shouldn't
     */
    public void createTable() throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try
        {
            PreparedStatement stmt =  connection.prepareStatement(
                    "DROP TABLE IF EXISTS MowreyQuestions");
            stmt.executeUpdate();
            stmt.close();

            stmt =  connection.prepareStatement(
                    "Create TABLE MowreyQuestions (questionID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, questionStatement VARCHAR(256), answer CHAR, information VARCHAR(80)");
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to create the Mowrey NPC question table", e);
        }

    }

    @Override
    public void createRow(int id, String question, String answer, LocalDate startDate, LocalDate endDate) throws DatabaseException
    {

    }

    @Override
    public void resetData()
    {

    }
}
