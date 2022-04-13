package datasource;

import java.time.LocalDate;
import java.util.ArrayList;

import dataDTO.QuestionDTO;

/**
 * @author Robert Windisch
 *
 *         interface for the NPCQuestion Table Data Gateway
 */
public interface NPCQuestionTableDataGateway
{

	/**
	 * Used for testing to set the data back to a known state
	 */
	public void resetData();

	/**
	 * @return the questions in the NPCQuestion table
	 * @throws DatabaseException
	 *             if can't reach data
	 */
	public ArrayList<QuestionDTO> getAllQuestions() throws DatabaseException;

	/**
	 * Empty the table of questions
	 *
	 * @throws DatabaseException
	 *             if the deletion failed
	 */
	public void deleteAllQuestions() throws DatabaseException;

	/**
	 * @param id
	 *            the ID of the question to be deleted
	 * @throws DatabaseException
	 *             if the deletion failed at the DB
	 */
	public void deleteQuestion(int id) throws DatabaseException;

	/**
	 * @param question
	 *            the text of the question
	 * @param answer
	 *            the text of the expected answer
	 * @param startDate
	 *            the date the question should become available
	 * @param endDate
	 *            the date the question should no longer be available
	 * @return a description of resulting question
	 * @throws DatabaseException
	 *             if the DB failed to do the add
	 */
	public QuestionDTO addQuestion(String question, String answer, LocalDate startDate, LocalDate endDate)
			throws DatabaseException;

	/**
	 * @param questionID
	 *            identifies the question you want
	 * @return the appropriate question
	 * @throws DatabaseException
	 *             if it couldn't find the question
	 */
	public QuestionDTO getQuestion(int questionID) throws DatabaseException;
}
