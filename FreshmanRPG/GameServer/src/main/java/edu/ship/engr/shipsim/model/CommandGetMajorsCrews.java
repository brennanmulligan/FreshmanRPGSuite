package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.DatabaseManager;
import edu.ship.engr.shipsim.datasource.PlayerRowDataGateway;
import edu.ship.engr.shipsim.datatypes.Crew;
import edu.ship.engr.shipsim.datatypes.Major;
import edu.ship.engr.shipsim.

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CommandGetMajorsCrews extends Command
{
    private int playerID;

    /**
     *
     * @param playerID the player's id
     */
    public CommandGetMajorsCrews() {
    }
    @Override
    void execute()
    {
        try
        {
            Connection connection = DatabaseManager.getSingleton().getConnection();
            try (PreparedStatement stmt = connection.prepareStatement(
                    "Insert INTO Players SET appearanceType = ?, quizScore = ?, experiencePoints = ?, crew = ?, major = ?, section = ?, buffPool = ?, online = ?",
                    Statement.RETURN_GENERATED_KEYS))
            {
                stmt.setString(1, appearanceType);
                stmt.setInt(2, quizScore);
                stmt.setInt(3, experiencePoints);
                stmt.setInt(4, crew.getID());
                stmt.setInt(5, major.getID());
                stmt.setInt(6, section);
                stmt.setInt(7, buffPool);
                stmt.setBoolean(8, online);
                stmt.executeUpdate();

                try (ResultSet rs = stmt.getGeneratedKeys())
                {
                    if (rs.next())
                    {
                        playerID = rs.getInt(1);
                        this.appearanceType = appearanceType;
                        this.quizScore = quizScore;
                        this.experiencePoints = experiencePoints;

                        this.connection = connection;
                        this.crew = crew;
                        this.major = major;
                        this.section = section;

                        this.buffPool = 0;
                        this.online = online;
                    }
                    else
                    {
                        throw new DatabaseException(
                                "Generated key for player not found.");
                    }
                }
        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
        }
    }
}
