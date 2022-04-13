package model;

import java.time.LocalDate;

import datasource.DatabaseException;

/**
 * @author am8081
 *
 */
public class CommandUpdateQuestion extends Command
{

	private int questionID;
	private String questionText;
	private String answerText;
	private LocalDate startDate;
	private LocalDate endDate;

	/**
	 * @param endDate
	 *            - the question's end date
	 * @param startDate
	 *            - the question's start date
	 * @param answerText
	 *            - the question's answer
	 * @param questionText
	 *            - the question
	 * @param questionID
	 *            - the question's ID
	 */
	public CommandUpdateQuestion(int questionID, String questionText, String answerText, LocalDate startDate,
								 LocalDate endDate)
	{
		this.questionID = questionID;
		this.questionText = questionText;
		this.answerText = answerText;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * @see model.Command
	 * @return whether the command completed successfully or not
	 */
	boolean execute()
	{
		try
		{
			QuestionManager manager = QuestionManager.getInstance();
			manager.updateQuestion(questionID, questionText, answerText, startDate, endDate);
		}
		catch (DatabaseException | QuestionNotPresentException e)
		{
			return false;
		}

		return true;
	}

}
