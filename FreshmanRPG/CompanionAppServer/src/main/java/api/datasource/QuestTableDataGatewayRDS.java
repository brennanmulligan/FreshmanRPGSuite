package api.datasource;

import edu.ship.engr.shipsim.dataENUM.QuestCompletionActionType;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.DatabaseManager;
import edu.ship.engr.shipsim.datasource.ObjectiveTableDataGateway;
import edu.ship.engr.shipsim.datasource.QuestRowDataGateway;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.QuestRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Encapsulates access to the RDS Quest table.
 */
public class QuestTableDataGatewayRDS implements QuestTableDataGateway
{
    private static QuestTableDataGatewayRDS instance;

    /**
     * Create a new gateway
     */
    private QuestTableDataGatewayRDS()
    {
    }

    /**
     * @return The gateway instance.
     */
    public static QuestTableDataGatewayRDS getInstance()
    {
        if (instance == null)
        {
            instance = new QuestTableDataGatewayRDS();
        }

        return instance;
    }

    /**
     * @see QuestTableDataGateway#resetData()
     */
    @Override
    public void resetData()
    {
        // does nothing!
    }

    /**
     * @see api.datasource.QuestTableDataGateway#getAllQuests()
     */
    @Override
    public ArrayList<QuestRecord> getAllQuests() throws DatabaseException
    {
        ArrayList<QuestRecord> quests = new ArrayList<>();
        Connection connection = DatabaseManager.getSingleton().getConnection();

        try (PreparedStatement stmt = connection.prepareStatement("SELECT * From Quests");
             ResultSet rs = stmt.executeQuery())
        {
            while (rs.next())
            {
                quests.add(buildQuestRecord(rs));
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to get all quests from Quests", e);
        }

        return quests;
    }

    private QuestRecord buildQuestRecord(ResultSet rs) throws SQLException, DatabaseException
    {
        QuestCompletionActionType qcat = QuestCompletionActionType.findByID(rs.getInt("completionActionType"));
        Date startDate = rs.getDate("startDate");
        Date endDate = rs.getDate("endDate");

        return new QuestRecord(
                rs.getInt("questID"),
                rs.getString("questTitle"),
                rs.getString("questDescription"),
                rs.getString("triggerMapName"),
                new Position(rs.getInt("triggerRow"), rs.getInt("triggerColumn")),
                ObjectiveTableDataGateway.getSingleton()
                        .getObjectivesForQuest(rs.getInt("questID")),
                rs.getInt("experiencePointsGained"),
                rs.getInt("objectivesForFulfillment"),
                qcat,
                QuestRowDataGateway.extractCompletionActionParameter(rs, qcat),
                startDate,
                endDate
        );
    }

}
