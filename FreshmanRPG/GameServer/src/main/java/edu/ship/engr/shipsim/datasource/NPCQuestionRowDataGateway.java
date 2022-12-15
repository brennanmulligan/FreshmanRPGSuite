package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.dataDTO.QuestionDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

/**
 * The RDS implementation of the gateway
 *
 * @author Merlin
 */
public class NPCQuestionRowDataGateway
{

    private int questionID;
    private String questionStatement;
    private String answer;
    private LocalDate startDate;
    private LocalDate endDate;
    private Connection connection;

    /**
     * Finder constructor
     *
     * @param questionID the questions Unique ID
     * @throws DatabaseException if we fail when talking to the database
     */
    public NPCQuestionRowDataGateway(int questionID) throws DatabaseException
    {
        this.connection = DatabaseManager.getSingleton().getConnection();

        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM NPCQuestions WHERE questionID = ?"))
        {
            stmt.setInt(1, questionID);

            try (ResultSet result = stmt.executeQuery())
            {
                result.next();

                this.questionStatement = result.getString("questionStatement");
                this.answer = result.getString("answer");
                this.startDate = result.getDate("startDate").toLocalDate();
                this.endDate = result.getDate("endDate").toLocalDate();
                this.questionID = result.getInt("questionID");
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException(
                    "Couldn't find an NPC Question with ID " + questionID, e);
        }
    }

    /**
     * Create constructor
     *
     * @param questionStatement the wording of the question
     * @param answer            the answer to the question
     * @param startDate         the first day the question is available
     * @param endDate           the last day the question is available
     * @throws DatabaseException if we fail when talking to the database
     *                           (question ID already exists would be common)
     */
    public NPCQuestionRowDataGateway(String questionStatement, String answer,
                                     LocalDate startDate,
                                     LocalDate endDate) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(
                "Insert INTO NPCQuestions SET questionStatement = ?, answer = ?, startDate = ?, endDate = ?",
                Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setString(1, questionStatement);
            stmt.setString(2, answer);
            stmt.setDate(3, java.sql.Date.valueOf(startDate));
            stmt.setDate(4, java.sql.Date.valueOf(endDate));
            int affectedRow = stmt.executeUpdate();

            if (affectedRow == 0)
            {
                throw new SQLException("Creating NPCQuestion failed");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys())
            {
                if (generatedKeys.next())
                {
                    this.questionID = generatedKeys.getInt(1);
                }
                else
                {
                    throw new SQLException(
                            "Creating NPCQuestion failed, although key was generated");
                }
            }
            this.questionStatement = questionStatement;
            this.answer = answer;
            this.startDate = startDate;
            this.endDate = endDate;

            try (ResultSet keys = stmt.getGeneratedKeys())
            {
                keys.next();
                this.questionID = keys.getInt(1);
            }

        }
        catch (SQLException e)
        {
            throw new DatabaseException(
                    "Couldn't create a NPC question record for question with ID " + e);
        }
    }

    private NPCQuestionRowDataGateway()
    {
    }

    /**
     * Drop the table if it exists and re-create it empty
     *
     * @throws DatabaseException shouldn't
     */
    public static void createTable() throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();

        try (PreparedStatement pstm = connection.prepareStatement("DROP TABLE IF EXISTS NPCQuestions"))
        {
            pstm.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to delete NPC Question table", e);
        }

        try (PreparedStatement pstm = connection.prepareStatement(
                "Create TABLE NPCQuestions (questionID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, questionStatement VARCHAR(256), answer VARCHAR(80), startDate DATE, endDate DATE)"))
        {
            pstm.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to create NPC Question table", e);
        }
    }

    /**
     * @return a random question from the data source
     * @throws DatabaseException if we can't talk to the database
     */
    public static NPCQuestionRowDataGateway findRandomGateway()
            throws DatabaseException
    {

        Connection connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM NPCQuestions ORDER BY RAND() LIMIT 1");
             ResultSet result = stmt.executeQuery())
        {
            result.next();
            NPCQuestionRowDataGateway gateway = new NPCQuestionRowDataGateway();
            gateway.questionID = result.getInt("questionID");
            gateway.questionStatement = result.getString("questionStatement");
            gateway.answer = result.getString("answer");
            gateway.startDate = result.getDate("startDate").toLocalDate();
            gateway.endDate = result.getDate("endDate").toLocalDate();
            gateway.questionID = result.getInt("questionID");
            return gateway;
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't find a a random NPC Question", e);
        }
    }

    /**
     * Deletes a question from the database table based off of ID.
     *
     * @throws DatabaseException There was not an ID matching a question in the database
     */
    public void delete() throws DatabaseException
    {
        this.connection = DatabaseManager.getSingleton().getConnection();

        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM NPCQuestions WHERE questionID = ? LIMIT 1"))
        {
            stmt.setInt(1, this.questionID);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException(
                    "There was no npc question with that id to delete", e);
        }
    }

    public String getAnswer()
    {
        return answer;
    }

    /**
     * Sets a new value for answer
     *
     * @param answer - the new value
     */
    public void setAnswer(String answer)
    {
        this.answer = answer;
    }

    public LocalDate getEndDate()
    {
        return endDate;
    }

    /**
     * Sets a new End Date
     *
     * @param date - the new end date
     */
    public void setEndDate(LocalDate date)
    {
        this.endDate = date;
    }

    public int getQuestionID()
    {
        return this.questionID;
    }

    /**
     * Returns the DTO of QuestionInfo
     *
     * @return the data transfer object
     */
    public QuestionDTO getQuestionInfo()
    {
        return new QuestionDTO(questionID, questionStatement, answer, startDate,
                endDate);
    }

    public String getQuestionStatement()
    {
        return questionStatement;
    }

    public LocalDate getStartDate()
    {
        return startDate;
    }

    /**
     * Sets a new Start Date
     *
     * @param date - the new start date
     */
    public void setStartDate(LocalDate date)
    {
        this.startDate = date;
    }

    public void resetData()
    {
    }

    /**
     * Sets a new value for question
     *
     * @param question - the new value
     */
    public void setQuestion(String question)
    {
        this.questionStatement = question;
    }

    /**
     * Sends the most up to date values to the database
     *
     * @throws DatabaseException - if it fails to write to the database
     */
    public void updateGateway() throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();

        try (PreparedStatement stmt = connection.prepareStatement(
                "UPDATE NPCQuestions SET questionStatement = ?, answer = ?, startDate = ?, endDate = ? WHERE questionID=?"))
        {
            stmt.setInt(5, this.questionID);
            stmt.setString(1, this.questionStatement);
            stmt.setString(2, this.answer);
            stmt.setDate(3, java.sql.Date.valueOf(this.startDate));
            stmt.setDate(4, java.sql.Date.valueOf(this.endDate));
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't update NPCQuestionsTable", e);
        }
    }

}