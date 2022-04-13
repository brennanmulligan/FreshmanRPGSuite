package datasource;

import java.time.LocalDate;

import dataDTO.QuestionDTO;

/**
 * Behavior required by gateways for the NPC Question table used by the quizbot
 *
 * @author Merlin
 *
 */
public interface NPCQuestionRowDataGateway
{

	/**
	 * For Testing Only
	 */
	public void resetData();

	/**
	 *
	 * @return the text of a question
	 */
	public String getQuestionStatement();

	/**
	 * The correct answer for this question
	 *
	 * @return the answer
	 */
	public String getAnswer();

	/**
	 * The date this question is available
	 *
	 * @return The date this question is available
	 */
	public LocalDate getStartDate();

	/**
	 * The last day the question is available
	 *
	 * @return The last day this question is available
	 */
	public LocalDate getEndDate();

	/**
	 * Sets a new value for question
	 * @param question - the new value
	 */
	public void setQuestion(String question);

	/**
	 * Sets a new value for answer
	 * @param answer - the new value
	 */
	public void setAnswer(String answer);

	/**
	 * Sets a new Start Date
	 * @param date - the new start date
	 */
	public void setStartDate(LocalDate date);

	/**
	 * Sets a new End Date
	 * @param date - the new end date
	 */
	public void setEndDate(LocalDate date);

	/**
	 * Sends the most up to date values to the database
	 * @throws DatabaseException - if it fails to write to the database
	 */
	public void updateGateway() throws DatabaseException;

	/**
	 * Delete this question from the database.
	 * @throws DatabaseException If question does not exist in the database.
	 */
	public void delete() throws DatabaseException;

	/**
	 * Gets the questionID
	 * @return the questionID from the table
	 */
	public int getQuestionID();

	/**
	 * @return the details of the question
	 */
	public QuestionDTO getQuestionInfo();

}
