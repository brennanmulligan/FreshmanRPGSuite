package datasource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import DatabaseBuilders.BuildTestQuestions;
import dataDTO.QuestionDTO;

/**
 * @author Robert Windisch
 *
 *         The Table Data Gateway that connects to the database
 *
 */
public class NPCQuestionTableDataGatewayRDS implements NPCQuestionTableDataGateway
{

	private static NPCQuestionTableDataGatewayRDS singleton;

	private ArrayList<QuestionDTO> data;

	/**
	 * @see datasource.NPCQuestionTableDataGateway#resetData()
	 */
	public void resetData()
	{
		try
		{
			BuildTestQuestions.createQuestionTable();
		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * returns the singleton of the RDS table data gateway
	 *
	 * @return singleton
	 */
	public static synchronized NPCQuestionTableDataGatewayRDS getSingleton()
	{

		if (singleton == null)
		{
			singleton = new NPCQuestionTableDataGatewayRDS();
		}
		return singleton;
	}

	/**
	 * @see datasource.NPCQuestionTableDataGateway#getAllQuestions()
	 */
	public ArrayList<QuestionDTO> getAllQuestions() throws DatabaseException
	{

		data = new ArrayList<>();
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection.prepareStatement( "SELECT * From NPCQuestions");
			ResultSet rs = stmt.executeQuery();

			while (rs.next())
			{
				QuestionDTO question = buildNPCQuestion(rs);
				data.add(question);
			}

		}
		catch (SQLException e)
		{
			throw new DatabaseException("Unable to get all questions from NPCQuestionTableGateway", e);
		}

		return data;

	}

	/**
	 * Creates a question info object from the result set
	 *
	 * @param rs the result set we are encoding
	 *
	 * @return questionInfo
	 */
	private QuestionDTO buildNPCQuestion(ResultSet rs)
	{
		try
		{
			int questionID = rs.getInt("questionID");
			String questionStmt = rs.getString("questionStatement");
			String answer = rs.getString("answer");
			Date startDate = rs.getDate("startDate");
			Date endDate = rs.getDate("endDate");

			return new QuestionDTO(questionID, questionStmt, answer, startDate.toLocalDate(),
					endDate.toLocalDate());

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * Drop the table if it exists and re-create it empty
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 */
	public static void createTable() throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection.prepareStatement(
					"DROP TABLE IF EXISTS NPCQuestions");
			stmt.executeUpdate();
			stmt.close();

			stmt = connection.prepareStatement("Create TABLE NPCQuestions (questionID INT NOT NULL AUTO_INCREMENT "+
					"PRIMARY KEY, questionStatement VARCHAR(256), answer VARCHAR(80), startDate DATE, endDate DATE)");
			stmt.executeUpdate();
			stmt.close();
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Unable to create the NPCQuestions table", e);
		}
	}

	/**
	 * @see datasource.NPCQuestionTableDataGateway#deleteAllQuestions()
	 */
	@Override
	public void deleteAllQuestions() throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection.prepareStatement("TRUNCATE TABLE NPCQuestions");
			stmt.executeUpdate();
			stmt.close();

		}
		catch (SQLException e)
		{
			throw new DatabaseException("Unable to  empty the NPCQuestions table", e);
		}

	}

	/**
	 * @see datasource.NPCQuestionTableDataGateway#deleteQuestion(int)
	 */
	@Override
	public void deleteQuestion(int id) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection.prepareStatement(
					"DELETE FROM NPCQuestions WHERE questionID=?;");
			stmt.setInt(1, id);
			stmt.executeUpdate();
			stmt.close();

		}
		catch (SQLException e)
		{
			throw new DatabaseException("Unable to delete question " + id + " from the db", e);
		}
	}

	/**
	 * @see datasource.NPCQuestionTableDataGateway#addQuestion(java.lang.String,
	 *      java.lang.String, java.time.LocalDate, java.time.LocalDate)
	 */
	@Override
	public QuestionDTO addQuestion(String question, String answer, LocalDate startDate, LocalDate endDate)
			throws DatabaseException
	{
		int questionID;
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection.prepareStatement(
					"INSERT INTO NPCQuestions SET questionStatement = ?, answer = ?, startDate = ?, endDate = ?;",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, question);
			stmt.setString(2, answer);
			stmt.setDate(3, java.sql.Date.valueOf(startDate));
			stmt.setDate(4, java.sql.Date.valueOf(endDate));
			stmt.executeUpdate();

			try (ResultSet generatedKeys = stmt.getGeneratedKeys())
			{
				if (generatedKeys.next())
				{
					questionID = generatedKeys.getInt(1);
				}
				else
				{
					throw new SQLException("Creating question failed, no ID obtained.");
				}
			}

			stmt.close();

		}
		catch (SQLException e)
		{
			throw new DatabaseException("Unable to insert question " + question + " to the db", e);
		}
		return new QuestionDTO(questionID, question, answer, startDate, endDate);
	}

	/**
	 * @see datasource.NPCQuestionTableDataGateway#getQuestion(int)
	 */
	@Override
	public QuestionDTO getQuestion(int questionID) throws DatabaseException
	{

		data = new ArrayList<>();
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT * From NPCQuestions WHERE questionID = ?");
			stmt.setInt(1, questionID);
			ResultSet rs = stmt.executeQuery();

			rs.next();
			return buildNPCQuestion(rs);

		}
		catch (SQLException e)
		{
			throw new DatabaseException("Unable to getquestions " + questionID + " from NPCQuestionTableGateway", e);
		}

	}

}
