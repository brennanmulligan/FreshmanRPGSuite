package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import dataDTO.GameManagerPlayerDTO;
import datasource.CSVPlayerGateway;
import datasource.DatabaseException;
import datasource.InvalidColumnException;
import model.reports.ErrorReport;

/**
 * Command that imports players from a CSV file
 * @author Abe Loscher and Chris Boyer
 *
 */
public class CommandImportPlayer extends Command
{

	private File file;

	/**
	 * Initialize command to execute on a given file.
	 * @param file file being executed on
	 */
	public CommandImportPlayer(File file)
	{
		this.file = file;
	}

	/**
	 * Imports players from File object 
	 * @return true if successful, false if hits an exception
	 */
	@Override
	boolean execute()
	{
		GameManagerPlayerManager playerManager = null;
		CSVPlayerGateway gateway = null;

		try
		{
			playerManager = GameManagerPlayerManager.getInstance();
		}
		catch (DatabaseException e)
		{
			sendErrorReport("Database error.");
			return false;
		}

		try
		{
			gateway = new CSVPlayerGateway(Paths.get(file.getPath()));
		}
		catch (IOException e)
		{
			sendErrorReport(e.getMessage());
			return false;
			// TODO Auto-generated catch block
		}
		catch (InvalidColumnException e)
		{
			sendErrorReport("Invalid Columns in CSV File");
			return false;
			// TODO Auto-generated catch block
		}


		for (GameManagerPlayerDTO player : gateway.getPlayerData())
		{
			try
			{
				playerManager.savePlayer(player.getName(), player.getPassword(), player.getCrew(), player.getMajor(), player.getSection());
			}
			catch (DatabaseException e)
			{
				sendErrorReport("Database error.");
				return false;
			}
		}

		playerManager.sendPlayerReport();

		return true;
	}

	/**
	 * This sends error reports with a specified error message
	 * @param errorMsg
	 * 		Error message sent in the report
	 */
	private void sendErrorReport(String errorMsg)
	{
		ErrorReport error = new ErrorReport(errorMsg);
		QualifiedObservableConnector.getSingleton().sendReport(error);
	}
}
