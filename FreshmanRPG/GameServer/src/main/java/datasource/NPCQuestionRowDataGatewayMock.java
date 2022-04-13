package datasource;

import java.time.LocalDate;

import dataDTO.QuestionDTO;

/**
 * A mock implementation for PlayerRowDataGateway
 *
 * @author Merlin
 *
 */
public class NPCQuestionRowDataGatewayMock implements NPCQuestionRowDataGateway
{

	private int questionID;
	private NPCQuestionMockDataSource dataSource;

	/**
	 * Just used by tests to reset static information
	 */
	public NPCQuestionRowDataGatewayMock()
	{
		dataSource = NPCQuestionMockDataSource.getSingleton();
	}

	/**
	 * Finder constructor - will initialize itself from the stored information
	 *
	 * @param questionID
	 *            the ID of the player we are looking for
	 * @throws DatabaseException
	 *             if the playerID isn't in the data source
	 */
	public NPCQuestionRowDataGatewayMock(int questionID) throws DatabaseException
	{
		this();
		if (dataSource.containsKey(questionID))
		{
			this.questionID = questionID;
		}
		else
		{
			throw new DatabaseException("Couldn't find NPC with ID " + questionID);
		}
	}

	/**
	 * Create Constructor
	 *
	 * @param question
	 *            .
	 * @param answer
	 *            .
	 * @param startDate
	 *            when this question should start to be asked
	 * @param endDate
	 *            when this question should no longer be asked
	 */
	public NPCQuestionRowDataGatewayMock(String question, String answer, LocalDate startDate, LocalDate endDate)
	{
		if (dataSource == null)
		{
			resetData();
		}
		dataSource.insert(question, answer, startDate, endDate);
	}

	/**
	 * @see datasource.NPCQuestionRowDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
		dataSource = NPCQuestionMockDataSource.getSingleton();
		dataSource.resetData();
	}

	/**
	 * @see datasource.NPCQuestionRowDataGateway#getQuestionStatement()
	 */
	@Override
	public String getQuestionStatement()
	{
		return dataSource.getQuestionStatement(questionID);
	}

	/**
	 * @return this question's unique ID
	 */
	public int getQuestionID()
	{
		return questionID;
	}

	/**
	 * @see datasource.NPCQuestionRowDataGateway#getAnswer()
	 */
	@Override
	public String getAnswer()
	{
		return dataSource.getAnswer(questionID);
	}

	/**
	 * @see datasource.NPCQuestionRowDataGateway#getStartDate()
	 */
	@Override
	public LocalDate getStartDate()
	{
		return dataSource.getStartDate(questionID);
	}

	/**
	 * @see datasource.NPCQuestionRowDataGateway#getEndDate()
	 */
	@Override
	public LocalDate getEndDate()
	{
		return dataSource.getEndDate(questionID);
	}

	/**
	 * @return a gateway for a random row in the table that is available
	 * @throws DatabaseException
	 *             if the datasource cannot complete the request
	 */
	public static NPCQuestionRowDataGateway findRandomGateway() throws DatabaseException
	{
		new NPCQuestionRowDataGatewayMock(1).resetData();

		NPCQuestionMockDataSource dataSource = NPCQuestionMockDataSource.getSingleton();
		int randomKey = dataSource.getRandomKey();

		return new NPCQuestionRowDataGatewayMock(randomKey);
	}

	/**
	 * Sends the most up to date values to the database
	 *
	 * @throws DatabaseException
	 *             - if it fails to write to the database
	 */
	public void updateGateway() throws DatabaseException
	{
	}

	/**
	 * Sets a new value for question
	 *
	 * @param question
	 *            - the new value
	 */
	public void setQuestion(String question)
	{
		dataSource.setQuestion(questionID, question);
	}

	/**
	 * Sets a new value for answer
	 *
	 * @param answer
	 *            - the new value
	 */
	public void setAnswer(String answer)
	{
		dataSource.setAnswer(questionID, answer);
	}

	/**
	 * Sets a new Start Date
	 *
	 * @param date
	 *            - the new start date
	 */
	public void setStartDate(LocalDate date)
	{
		dataSource.setStartDate(questionID, date);
	}

	/**
	 * Sets a new End Date
	 *
	 * @param date
	 *            - the new end date
	 */
	public void setEndDate(LocalDate date)
	{
		dataSource.setEndDate(questionID, date);
	}

	/**
	 * Removes a question based off the provided questionID
	 */
	public void delete()
	{
		dataSource.remove(questionID);
	}

	/**
	 * @see datasource.NPCQuestionRowDataGateway#getQuestionInfo()
	 */
	@Override
	public QuestionDTO getQuestionInfo()
	{
		return new QuestionDTO(questionID, getQuestionStatement(), getAnswer(), getStartDate(), getEndDate());
	}

}
