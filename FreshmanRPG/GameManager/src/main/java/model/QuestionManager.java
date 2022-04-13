package model;

import java.time.LocalDate;
import java.util.ArrayList;

import dataDTO.QuestionDTO;
import datasource.DatabaseException;
import datasource.NPCQuestionRowDataGateway;
import datasource.NPCQuestionRowDataGatewayMock;
import datasource.NPCQuestionRowDataGatewayRDS;
import datasource.NPCQuestionTableDataGateway;
import datasource.NPCQuestionTableDataGatewayMock;
import datasource.NPCQuestionTableDataGatewayRDS;
import model.reports.QuestionListReport;

/**
 * Keeps track of all questions.
 *
 * @author Nick Martinez, Alec Waddelow
 *
 */
public class QuestionManager
{
	private static QuestionManager instance;

	/**
	 * @return The QuestionManager instance
	 */
	protected static QuestionManager getInstance()
	{
		if (instance == null)
		{
			instance = new QuestionManager();
		}

		return instance;
	}

	/**
	 * Constructs a new QuestionManager.
	 */
	QuestionManager()
	{
	}

	/**
	 * Used for testing. Force the instance to be reloaded.
	 */
	static void resetSingleton()
	{
		instance = null;
	}

	/**
	 * Returns an arrayList of questions.
	 *
	 * @return the questions
	 * @throws DatabaseException
	 *             if we can't talk to the data source
	 */
	protected ArrayList<QuestionDTO> getQuestions() throws DatabaseException
	{

		NPCQuestionTableDataGateway gateway = getQuestionTableGateway();
		return gateway.getAllQuestions();
	}

	private NPCQuestionTableDataGateway getQuestionTableGateway()
	{
		if (OptionsManager.getSingleton().isUsingMockDataSource())
		{
			return NPCQuestionTableDataGatewayMock.getSingleton();
		}
		return NPCQuestionTableDataGatewayRDS.getSingleton();
	}

	/**
	 * Return the number of questions.
	 *
	 * @return the number of questions
	 * @throws DatabaseException
	 *             if we can't talk to the data source
	 */
	protected int getNumberOfQuestions() throws DatabaseException
	{

		return getQuestions().size();
	}

	/**
	 * Add a question to the list.
	 *
	 * @param question
	 *            - question to be added
	 * @param answer
	 *            quiz answer
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @return a DTO containing the added question
	 * @throws DatabaseException
	 *             if we can't talk to the data source
	 */
	protected QuestionDTO addQuestion(String question, String answer, LocalDate startDate, LocalDate endDate)
			throws DatabaseException
	{
		QuestionDTO dto = null;
		NPCQuestionTableDataGateway gateway;
		if (OptionsManager.getSingleton().isUsingMockDataSource())
		{
			gateway = NPCQuestionTableDataGatewayMock.getSingleton();
		}
		else
		{
			gateway = NPCQuestionTableDataGatewayRDS.getSingleton();
		}

		dto = gateway.addQuestion(question, answer, startDate, endDate);

		refreshQuestionList();

		return dto;

	}

	/**
	 * Send a report with the updated list of questions
	 *
	 * @throws DatabaseException
	 *             if we can't talk to the DB
	 */
	public void refreshQuestionList() throws DatabaseException
	{
		ArrayList<QuestionDTO> list = this.getQuestions();
		QuestionListReport report = new QuestionListReport(list);
		QualifiedObservableConnector.getSingleton().sendReport(report);
	}

	/**
	 * Remove a question from the list.
	 *
	 * @param ID
	 *            the id of the question to be removed
	 * @throws DatabaseException
	 *             if we can't talk to the db
	 */
	protected void removeQuestion(int ID) throws DatabaseException
	{
		NPCQuestionTableDataGateway gateway;
		gateway = getQuestionTableGateway();
		gateway.deleteQuestion(ID);
		refreshQuestionList();
	}

	private NPCQuestionRowDataGateway getQuestionRowDataGateway(int ID) throws DatabaseException
	{
		if (OptionsManager.getSingleton().isUsingMockDataSource())
		{
			return new NPCQuestionRowDataGatewayMock(ID);
		}
		return new NPCQuestionRowDataGatewayRDS(ID);
	}

	/**
	 * Deletes all questions currently in the question table.
	 *
	 * @throws DatabaseException
	 *             if we can't remove them all
	 */
	protected void removeAllQuestions() throws DatabaseException
	{
		getQuestionTableGateway().deleteAllQuestions();
		refreshQuestionList();
	}

	/**
	 * @param questionID
	 *            - this question's ID
	 * @param questionText
	 *            - this question's question
	 * @param answerText
	 *            - this question's answer
	 * @param startDate
	 *            - this question's start date
	 * @param endDate
	 *            - this question's end date
	 * @throws QuestionNotPresentException
	 *             If the target question does not exist.
	 * @throws DatabaseException
	 *             If the question could not be updated in the database.
	 * @throws QuestionNotPresentException
	 *             If the target question does not exist.
	 */
	public void updateQuestion(int questionID, String questionText, String answerText, LocalDate startDate, LocalDate endDate)
			throws QuestionNotPresentException, DatabaseException
	{
		NPCQuestionRowDataGateway questionGateway;
		try
		{
			questionGateway = getQuestionRowDataGateway(questionID);
			questionGateway.setQuestion(questionText);
			questionGateway.setAnswer(answerText);
			questionGateway.setStartDate(startDate);
			questionGateway.setEndDate(endDate);
			questionGateway.updateGateway();

			ArrayList<QuestionDTO> questions = this.getQuestions();
			QualifiedObservableConnector.getSingleton().sendReport(new QuestionListReport(questions));

		}
		catch (DatabaseException e)
		{
			throw new QuestionNotPresentException();
		}
		this.refreshQuestionList();
	}

	/**
	 * Used for testing
	 *
	 * @param questionManager
	 *            the instance of question manager to be set
	 */
	static void setSingleton(QuestionManager questionManager)
	{
		instance = questionManager;
	}

	/**
	 * @param questionID the ID of the question we are interested in
	 * @return the question with the given ID
	 * @throws DatabaseException if we can't find that question
	 */
	public QuestionDTO getQuestion(int questionID) throws DatabaseException
	{
		return this.getQuestionTableGateway().getQuestion(questionID);
	}

}
