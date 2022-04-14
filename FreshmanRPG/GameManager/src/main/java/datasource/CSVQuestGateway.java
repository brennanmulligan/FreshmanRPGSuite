package datasource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import criteria.GameLocationDTO;
import criteria.QuestCompletionActionParameter;
import dataENUM.QuestCompletionActionType;
import datatypes.Position;
import model.GameManagerQuestManager;
import model.QuestRecord;

/**
 * CSV Parser for quests
 *
 * @author Jordan Long
 *
 */
public class CSVQuestGateway
{
	private String[] columnNames;
	private ArrayList<GameManagerQuestDTO> questData;
	private LocalDate semesterStart;


	/**
	 * @param file Imported File
	 * @param semesterStart Start date of the semester
	 * @throws IOException Exception
	 * @throws InvalidColumnException Exception
	 * @throws DatabaseException Exception
	 */
	public CSVQuestGateway(File file, LocalDate semesterStart) throws IOException, InvalidColumnException, DatabaseException
	{

		//Convert to GregorianCalendar for the csv parser
		this.semesterStart = semesterStart;
		try (BufferedReader reader = new BufferedReader(new FileReader(file)))
		{
			final ArrayList<String> rows = new ArrayList<>();
			String line;
			while ((line = reader.readLine()) != null)
			{
				rows.add(line);
			}
			parseColumns(rows.remove(0));
			questData = new ArrayList<>();
			parseRows(rows);
		}
	}


	private static final String[] REQUIRED_COLUMNS = {"objectiveID", "questTitle", "objectiveCompletionType", "description", "triggerMapName", "triggerRow", "triggerCol",
			"exp", "fulfillment", "quest_action_type", "teleport_map", "teleport_x", "teleport_y", "start_week", "end_week"};

	/**
	 * Parse out the columns. Verify that all required columns are present in csv file.
	 *
	 * @param header
	 *            - the header containing the column names
	 * @throws InvalidColumnException
	 *             - if there are any invalid columns
	 */
	private void parseColumns(String header) throws InvalidColumnException
	{
		if (!Character.isAlphabetic(header.charAt(0)))
		{
			header = header.substring(1);
		}
		final ArrayList<String> columns = new ArrayList<>(Arrays.asList(header.split(",")));
		for (String req : REQUIRED_COLUMNS)
		{
			if (!columns.contains(req))
			{
				throw new InvalidColumnException(
						"CSV file must contain the following columns: " + req);
			}
		}
		columnNames = columns.toArray(new String[columns.size()]);
	}

	/**
	 * Get name associated with this index
	 *
	 * @param i - the index
	 * @return name associated with the index
	 */
	public String getColumn(int i)
	{
		return this.columnNames[i];
	}

	/**
	 * Parse all quest data out into list of quest DTOs.
	 * Breaks columns down in to an array of strings, has the ability to break
	 * up text in quotation marks as well.
	 *
	 * @param rows - player data
	 * @throws DatabaseException
	 */
	private void parseRows(ArrayList<String> rows) throws DatabaseException
	{
		for (String row : rows)
		{
			final String[] values = row.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
			if (values[0].isEmpty())
			{
				GameManagerQuestDTO quest = parseQuest(values);
				if (quest != null)
				{
					questData.add(quest);
				}
			}
		}
	}


	/**
	 * Parses the row from the imported file and creates a Quest DTO.
	 * @param values
	 * @return QuestDTO
	 * @throws DatabaseException
	 */
	private GameManagerQuestDTO parseQuest(String[] values)
	{
		String questTitle = null;
		String description = null;
		String triggerMapName = null;
		int triggerRow = 0;
		int triggerCol = 0;
		int exp = 0;
		int fulfillment = 0;
		QuestCompletionActionType completionActionType = QuestCompletionActionType.NO_ACTION;
		QuestCompletionActionParameter completionActionParameter = null;

		//coordinates for teleport
		String teleport_map = null;
		int teleport_x = 0;
		int teleport_y = 0;

		LocalDate startDate = semesterStart;
		LocalDate endDate = semesterStart;

		for (int i = 0; i < values.length; i++)
		{
			values[i] = values[i].trim();
			switch (getColumn(i))
			{
				case "questTitle":
					questTitle = values[i];
					break;
				case "description":
					description = values[i];
					break;
				case "triggerMapName":
					triggerMapName = values[i];
					break;
				case "triggerRow":
					triggerRow = Integer.parseInt(values[i]);
					break;
				case "triggerCol":
					triggerCol = Integer.parseInt(values[i]);
					break;
				case "exp":
					exp = Integer.parseInt(values[i]);
					break;
				case "fulfillment":
					fulfillment = Integer.parseInt(values[i]);
					break;
				case "quest_action_type":
					if (values[i].toString().equalsIgnoreCase("TELEPORT"))
					{
						completionActionType = QuestCompletionActionType.TELEPORT;
					}
					else if (values[i].toString().equalsIgnoreCase("NO_ACTION"))
					{
						completionActionType = QuestCompletionActionType.NO_ACTION;
					}
					break;
				case "teleport_map":
					if (completionActionType == QuestCompletionActionType.TELEPORT)
					{
						teleport_map = values[i];
					}
					break;
				case "teleport_x":
					if (completionActionType == QuestCompletionActionType.TELEPORT)
					{
						teleport_x = Integer.parseInt(values[i]);
					}
					break;
				case "teleport_y":
					if (completionActionType == QuestCompletionActionType.TELEPORT)
					{
						teleport_y = Integer.parseInt(values[i]);
					}
					break;
				case "start_week":
					int startWeek = Integer.parseInt(values[i]);
					startDate = startDate.plusWeeks(startWeek);
					break;
				case "end_week":
					int endWeek = Integer.parseInt(values[i]);
					endDate = endDate.plusWeeks(endWeek);
					break;
			}
		}

		//If completion action type is teleport, create a GameLocationObject to store the map and coordinates.
		if (completionActionType.equals(QuestCompletionActionType.TELEPORT))

		{
			Position p = new Position(teleport_x, teleport_y);
			completionActionParameter = new GameLocationDTO(teleport_map, p);
		}


		//check if existing quest with the same title exists in the database already.

		try
		{
			QuestRecord existingQuest = GameManagerQuestManager.getInstance().getQuest(questTitle);
			if (existingQuest == null)
			{
				return new GameManagerQuestDTO(questTitle, description, triggerMapName, triggerRow, triggerCol, exp,
						fulfillment, completionActionType, completionActionParameter, java.sql.Date.valueOf(startDate), java.sql.Date.valueOf(endDate));
			}

		}
		catch (DatabaseException e)  //invalid title exception
		{
			e.printStackTrace();
		}

		return null;
	}


	/**
	 * Return quest data.
	 *
	 * @return - list of DTOs containing quest data
	 * @author Jordan Long
	 */
	public ArrayList<GameManagerQuestDTO> getQuestData()
	{
		return questData;
	}


	/**
	 * Returns the number of quests parsed out of the CSV.
	 *
	 * @return - number of quests
	 * @author Jordan Long
	 */
	public int getNumberOfQuests()
	{
		return questData.size();
	}


}
