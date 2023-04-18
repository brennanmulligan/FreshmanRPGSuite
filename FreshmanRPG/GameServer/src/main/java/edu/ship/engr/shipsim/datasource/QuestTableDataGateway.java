package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.dataENUM.QuestCompletionActionType;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.QuestRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuestTableDataGateway
{
    private static QuestTableDataGateway instance;

    private QuestTableDataGateway()
    {
        //do nothing this just explicitly makes it private
    }

    public static QuestTableDataGateway getSingleton()
    {
        if (instance == null)
        {
            instance = new QuestTableDataGateway();
        }
        return instance;
    }

    public void setSingleton(QuestTableDataGateway instance)
    {
        this.instance = instance;
    }

    public void resetData()
    {

    }

    public ArrayList<QuestRecord> getAllQuests() throws DatabaseException
    {
        final ArrayList<QuestRecord> quests = new ArrayList<>();
        final Connection connection =
                DatabaseManager.getSingleton().getConnection();

        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM Quests"))
        {
            final ResultSet rs = stmt.executeQuery();

            while (rs.next())
            {
                quests.add(buildQuestRecord(rs));
            }


        }
        catch (SQLException ex)
        {
            throw new DatabaseException(
                    "Unable to get all quests from the database", ex);
        }

        return quests;
    }

    private QuestRecord buildQuestRecord(ResultSet rs)
            throws SQLException, DatabaseException
    {
        QuestCompletionActionType qcat = QuestCompletionActionType.findByID(
                rs.getInt("completionActionType"));

        return new QuestRecord(
                rs.getInt("questID"),
                rs.getString("questTitle"),
                rs.getString("questDescription"),
                rs.getString("triggerMapName"),
                new Position(rs.getInt("triggerRow"),
                        rs.getInt("triggerColumn")),
                null,
                rs.getInt("experiencePointsGained"),
                rs.getInt("objectivesForFulfillment"),
                qcat,
                QuestRowDataGateway.extractCompletionActionParameter(rs, qcat),
                rs.getDate("startDate"),
                rs.getDate("endDate"),
                rs.getBoolean("easterEgg"));
    }
}
