package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;

import dataDTO.QuestionDTO;
import datasource.CSVQuestionGateway;
import datasource.DatabaseException;
import datasource.InvalidColumnException;
import model.reports.ErrorReport;

/**
 * Command that imports questions from a CSV file.
 * @author Christopher Boyer, Mohammed Almaslamani
 *
 */
public class CommandImportQuestion extends Command
{

	private File file;
	private LocalDate gregorianCalendar;

	/**
	 * Initialize command to execute on a given file with a start date.
	 * @param file - csv file 
	 * @param gregorianCalendar - gregorianCalendar date
	 */
	public CommandImportQuestion(File file, LocalDate gregorianCalendar)
	{
		this.file = file;
		this.gregorianCalendar = gregorianCalendar;
	}

	/**
	 * Imports question from File object 
	 * @return true if successful, false if hits an exception
	 */
	@Override
	boolean execute()
	{
		QuestionManager questionManager = null;
		CSVQuestionGateway gateway = null;

		questionManager = QuestionManager.getInstance();
		try
		{
			gateway = new CSVQuestionGateway(Paths.get(file.getPath()), gregorianCalendar);
		}
		catch (InvalidColumnException e)
		{
			ErrorReport error = new ErrorReport(e.getMessage());
			QualifiedObservableConnector.getSingleton().sendReport(error);
			return false;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		for (QuestionDTO question : gateway.getQuestionData())
		{
			try
			{
				questionManager.addQuestion(question.getQuestion(), question.getAnswer(), question.getStartDate(), question.getEndDate());
			}
			catch (DatabaseException e)
			{
				e.printStackTrace();
			}
		}
		return true;
	}

}
