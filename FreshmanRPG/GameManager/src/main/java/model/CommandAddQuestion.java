package model;

import java.time.LocalDate;

import datasource.DatabaseException;

/**
 * Command to add a question
 * @author Darin Alleman / Darnell Martin
 *
 */
public class CommandAddQuestion extends Command
{

	private String question;
	private String answer;
	private LocalDate startDate;
	private LocalDate endDate;

	/**
	 * Construct the command to add a question
	 * @param question the question to add
	 * @param answer the answer
	 * @param startDate the start date
	 * @param endDate the end date
	 */
	public CommandAddQuestion(String question, String answer, LocalDate startDate, LocalDate endDate)
	{
		this.question = question;
		this.answer = answer;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * Add the question with the question manager
	 */
	@Override
	boolean execute()
	{
		try
		{
			QuestionManager manager = QuestionManager.getInstance();
			manager.addQuestion(this.question, this.answer, this.startDate, this.endDate);

		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
		}
		return false;
	}


}
