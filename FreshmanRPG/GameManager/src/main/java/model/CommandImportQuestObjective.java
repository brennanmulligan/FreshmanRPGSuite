package model;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import datasource.CSVObjectiveGateway;
import datasource.CSVQuestGateway;
import datasource.DatabaseException;
import datasource.GameManagerObjectiveDTO;
import datasource.GameManagerQuestDTO;
import datasource.InvalidColumnException;
import model.reports.ErrorReport;

/**
 * The Import Quest and Objective Command
 *
 * @author Jordan Long
 *
 */
public class CommandImportQuestObjective extends Command
{
	private File file;
	private LocalDate date;

	/**
	 * @param file
	 *            File imported by the File Chooser
	 * @param date
	 *            Semester start date
	 */
	public CommandImportQuestObjective(File file, LocalDate date)
	{
		this.file = file;
		this.date = date;
	}

	/**
	 * @see model.Command#execute()
	 */
	@Override
	boolean execute()
	{
		GameManagerQuestManager questManager = null;
		GameManagerQuestManager objectiveManager = null;
		CSVObjectiveGateway objectiveGateway = null;
		CSVQuestGateway questGateway = null;

		questManager = GameManagerQuestManager.getInstance();
		objectiveManager = GameManagerQuestManager.getInstance();

		// Quests
		// Try to parse selected file. Throw error if wrong column headers or invalid
		// file.
		try
		{
			try
			{
				questGateway = new CSVQuestGateway(file, date);
			}
			catch (DatabaseException e)
			{
				e.printStackTrace();
			}
		}
		catch (IOException e)
		{

			return false;
		}
		catch (InvalidColumnException e)
		{
			sendErrorReport("Invalid Columns in CSV File");
			return false;
		}

		// Sends commands to add all quests from import
		for (GameManagerQuestDTO quest : questGateway.getQuestData())
		{
			try
			{
				questManager.addQuest(quest.getQuestTitle(), quest.getDescription(), quest.getTriggerMapName(),
						quest.getTriggerPosition(), quest.getExperiencePointsEarned(), quest.getFulfillment(),
						quest.getCompletionActionType(), quest.getCompletionActionParameter(), quest.getStartDate(),
						quest.getEndDate());
			}
			catch (DatabaseException e)
			{
				sendErrorReport("Database error.");
				return false;
			}
		}
		try
		{
			questManager.sendQuestReport();
		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
		}

		// Import objectives
		try
		{
			try
			{
				objectiveGateway = new CSVObjectiveGateway(file, date);
			}
			catch (DatabaseException e)
			{
				e.printStackTrace();
			}
		}
		catch (IOException e)
		{

			return false;
		}
		catch (InvalidColumnException e)
		{
			sendErrorReport("Invalid Columns in CSV File");
			return false;
		}

		// Sends commands to add all objectives from import
		for (GameManagerObjectiveDTO objective : objectiveGateway.getObjectiveData())
		{
			try
			{
				objectiveManager.addObjective(objective.getQuestID(), objective.getDescription(),
						objective.getExperiencePointsEarned(), objective.getCompletionType(),
						objective.getCompletionParameter());
			}
			catch (DatabaseException e)
			{
				sendErrorReport("Database error.");
				return false;
			}
		}
		try
		{
			objectiveManager.sendQuestReport();
		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * This sends error reports with a specified error message
	 *
	 * @param errorMsg
	 *            Error message sent in the report
	 */
	private void sendErrorReport(String errorMsg)
	{
		ErrorReport error = new ErrorReport(errorMsg);
		QualifiedObservableConnector.getSingleton().sendReport(error);
	}
}
