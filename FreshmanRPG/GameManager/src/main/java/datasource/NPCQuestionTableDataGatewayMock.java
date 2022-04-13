package datasource;

import java.time.LocalDate;
import java.util.ArrayList;

import dataDTO.QuestionDTO;

/**
 * @author Robert Windisch
 *
 *         The Mock NPCQuestion Table Data Gateway
 *
 */
public class NPCQuestionTableDataGatewayMock implements NPCQuestionTableDataGateway
{

	private NPCQuestionMockDataSource data;

	private static NPCQuestionTableDataGatewayMock instance;

	private NPCQuestionTableDataGatewayMock()
	{
		data = NPCQuestionMockDataSource.getSingleton();
	}

	/**
	 * @return the only one there is
	 */
	public static NPCQuestionTableDataGatewayMock getSingleton()
	{
		if (instance == null)
		{
			instance = new NPCQuestionTableDataGatewayMock();
		}
		return instance;
	}

	/**
	 * @see datasource.NPCQuestionTableDataGateway#resetData()
	 */
	public void resetData()
	{
		data = NPCQuestionMockDataSource.getSingleton();
		data.resetData();
	}

	/**
	 * @see datasource.NPCQuestionTableDataGateway#getAllQuestions()
	 */
	public ArrayList<QuestionDTO> getAllQuestions() throws DatabaseException
	{

		return data.values();
	}

	/**
	 * @see datasource.NPCQuestionTableDataGateway#deleteAllQuestions()
	 */
	@Override
	public void deleteAllQuestions() throws DatabaseException
	{
		data.deleteAllQuestions();
	}

	/**
	 * @see datasource.NPCQuestionTableDataGateway#deleteQuestion(int)
	 */
	@Override
	public void deleteQuestion(int id) throws DatabaseException
	{
		data.remove(id);

	}


	/**
	 * @see datasource.NPCQuestionTableDataGateway#addQuestion(java.lang.String, java.lang.String, java.time.LocalDate, java.time.LocalDate)
	 */
	@Override
	public QuestionDTO addQuestion(String question, String answer, LocalDate startDate, LocalDate endDate)
	{
		return data.insert(question, answer, startDate, endDate);
	}

	/**
	 * @see datasource.NPCQuestionTableDataGateway#getQuestion(int)
	 */
	@Override
	public QuestionDTO getQuestion(int questionID) throws DatabaseException
	{
		return data.getQuestionDTO(questionID);
	}
}
