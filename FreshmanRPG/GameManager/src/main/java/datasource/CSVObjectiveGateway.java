package datasource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import criteria.ObjectiveCompletionCriteria;
import criteria.CriteriaIntegerDTO;
import criteria.CriteriaStringDTO;
import criteria.GameLocationDTO;
import dataENUM.ObjectiveCompletionType;
import datatypes.Position;
import model.ObjectiveRecord;
import model.GameManagerQuestManager;
import model.QuestRecord;

/**
 * Gateway to CSV file containing objective info.
 */
public class CSVObjectiveGateway
{
	private String[] columnNames;
	private ArrayList<GameManagerObjectiveDTO> objectiveData;
	private ArrayList<GameManagerQuestDTO> questData;
	private LocalDate semesterStartLocalDate;

	/**
	 * Constructor for the CSV Objective and Quest Gateway
	 *
	 * @param file
	 *            Imported CSV File
	 * @param semesterStart
	 *            Start date of the semester
	 * @throws IOException
	 *             Bad File
	 * @throws InvalidColumnException
	 *             Wrong Columns
	 * @throws DatabaseException
	 *             Exception
	 */
	public CSVObjectiveGateway(File file, LocalDate semesterStart)
			throws IOException, InvalidColumnException, DatabaseException
	{
		this.semesterStartLocalDate = semesterStart;
		try (BufferedReader reader = new BufferedReader(new FileReader(file)))
		{
			final ArrayList<String> rows = new ArrayList<>();
			String line;
			while ((line = reader.readLine()) != null)
			{
				rows.add(line);
			}
			parseColumns(rows.remove(0));
			objectiveData = new ArrayList<>();
			questData = new ArrayList<>();
			parseRows(rows);
		}
	}

	private static final String[] REQUIRED_COLUMNS =
			{"objectiveID", "questTitle", "objectiveCompletionType", "description", "triggerMapName", "triggerRow",
					"triggerCol", "exp", "fulfillment", "quest_action_type", "teleport_map", "teleport_x", "teleport_y",
					"start_week", "end_week"};

	/**
	 * Parse out the columns. Verify that all required columns are present in csv
	 * file.
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
				throw new InvalidColumnException("CSV file must contain the following columns: " + req);
			}
		}
		columnNames = columns.toArray(new String[columns.size()]);
	}

	/**
	 * Get name associated with this index
	 *
	 * @param i
	 *            - the index
	 * @return name associated with the index
	 */
	public String getColumn(int i)
	{
		return this.columnNames[i];
	}

	/**
	 * Parse all player data out into list of player DTOs. Breaks columns down in to
	 * an array of strings, has the ability to break up text in quotation marks as
	 * well.
	 *
	 * @param rows
	 *            - player data
	 * @throws DatabaseException
	 */
	private void parseRows(ArrayList<String> rows) throws DatabaseException
	{
		for (String row : rows)
		{
			final String[] values = row.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
			if (!values[0].isEmpty())
			{
				GameManagerObjectiveDTO objective = parseObjective(values);
				if (objective != null)
				{
					objectiveData.add(objective);
				}
			}
		}
	}

	/**
	 * Parse out objective data into an Objective DTO.
	 *
	 * @param values
	 *            - the values
	 * @return - player DTO
	 * @throws DatabaseException
	 */
	private GameManagerObjectiveDTO parseObjective(String[] values) throws DatabaseException
	{
		int questID = 0;
		String questName = null;
		String description = null;
		String parameterText = null;
		int exp = 0;
		String triggerMapName = null;
		int triggerRow = 0;
		int triggerCol = 0;

		ObjectiveCompletionCriteria parameter = null;

		ObjectiveCompletionType type = ObjectiveCompletionType.REAL_LIFE;
		LocalDate startDate = semesterStartLocalDate;
		LocalDate endDate = semesterStartLocalDate;

		for (int i = 0; i < values.length; i++)
		{
			values[i] = values[i].trim();
			switch (getColumn(i))
			{
				case "questTitle":
					questName = values[i];
					break;
				case "objectiveCompletionType":
					if (values[i].toString().equalsIgnoreCase("REAL_LIFE"))
					{
						type = ObjectiveCompletionType.REAL_LIFE;
					}
					else if (values[i].toString().equalsIgnoreCase("MOVEMENT"))
					{
						type = ObjectiveCompletionType.MOVEMENT;
					}
					else if (values[i].toString().equalsIgnoreCase("CHAT"))
					{
						type = ObjectiveCompletionType.CHAT;
					}
					else if (values[i].toString().equalsIgnoreCase("DOUBLOONS"))
					{
						type = ObjectiveCompletionType.DOUBLOONS;
					}
					else if (values[i].toString().equalsIgnoreCase("KEYSTROKE"))
					{
						type = ObjectiveCompletionType.KEYSTROKE;

					}
					else if (values[i].toString().equalsIgnoreCase("FRIENDS"))
					{
						type = ObjectiveCompletionType.FRIENDS;

					}
					else if (values[i].toString().equalsIgnoreCase("TERMINAL"))
					{
						type = ObjectiveCompletionType.TERMINAL;

					}
					break;

				case "description":
					description = values[i];
					break;
				case "parameter":
					parameterText = values[i];
					break;
				case "triggerMapName":
					if (type == ObjectiveCompletionType.MOVEMENT)
					{
						triggerMapName = values[i];
					}
					break;
				case "triggerRow":
					if (type == ObjectiveCompletionType.MOVEMENT)
					{
						triggerRow = Integer.parseInt(values[i]);
					}
					break;
				case "triggerCol":
					if (type == ObjectiveCompletionType.MOVEMENT)
					{
						triggerCol = Integer.parseInt(values[i]);
					}
					break;
				case "exp":
					exp = Integer.parseInt(values[i]);
					break;
				case "start_week":
					int startWeek = Integer.parseInt(values[i]);
					startDate.plusWeeks(startWeek);
					break;
				case "end_week":
					int endWeek = Integer.parseInt(values[i]);
					endDate.plusWeeks(endWeek);
					break;

			}
		}

		// If ObjectiveActionType is Real Life, create CriteriaStringDTO for
		// description.
		if (type.equals(ObjectiveCompletionType.REAL_LIFE))
		{
			parameter = new CriteriaStringDTO(parameterText);
		}
		else if (type.equals(ObjectiveCompletionType.MOVEMENT))
		{
			Position p = new Position(triggerRow, triggerCol);
			parameter = new GameLocationDTO(triggerMapName, p);

		}
		else if (type.equals(ObjectiveCompletionType.CHAT))
		{
			parameter = new CriteriaStringDTO(parameterText);

		}
		else if (type.equals(ObjectiveCompletionType.DOUBLOONS))
		{
			parameter = new CriteriaIntegerDTO(Integer.parseInt(parameterText));

		}
		else if (type.equals(ObjectiveCompletionType.KEYSTROKE))
		{
			parameter = new CriteriaStringDTO(parameterText);

		}

		// get questId for objective
		QuestRecord quest = GameManagerQuestManager.getInstance().getQuest(questName);
		questID = quest.getQuestID();

		// Check if objective already exists with quest
		for (ObjectiveRecord record : quest.getObjectives())
		{
			if (record.getObjectiveDescription().equals(description))
			{
				return null;
			}

		}

		return new GameManagerObjectiveDTO(questID, questName, description, exp, type, parameter,
				java.sql.Date.valueOf(startDate), java.sql.Date.valueOf(endDate));
	}

	/**
	 * Return objective data.
	 *
	 * @return - list of DTOs containing objective data
	 */
	public ArrayList<GameManagerObjectiveDTO> getObjectiveData()
	{
		return objectiveData;
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
	 * Returns the number of objectives parsed out of the CSV.
	 *
	 * @return - number of objectives
	 */
	public int getNumberOfObjectives()
	{
		return objectiveData.size();
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
