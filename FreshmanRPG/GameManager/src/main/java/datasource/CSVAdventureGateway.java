package datasource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import criteria.AdventureCompletionCriteria;
import criteria.CriteriaIntegerDTO;
import criteria.CriteriaStringDTO;
import criteria.GameLocationDTO;
import dataENUM.AdventureCompletionType;
import datatypes.Position;
import model.AdventureRecord;
import model.GameManagerQuestManager;
import model.QuestRecord;

/**
 * Gateway to CSV file containing adventure info.
 */
public class CSVAdventureGateway
{
	private String[] columnNames;
	private ArrayList<GameManagerAdventureDTO> adventureData;
	private ArrayList<GameManagerQuestDTO> questData;
	private LocalDate semesterStartLocalDate;

	/**
	 * Constructor for the CSV Adventure and Quest Gateway
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
	public CSVAdventureGateway(File file, LocalDate semesterStart)
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
			adventureData = new ArrayList<>();
			questData = new ArrayList<>();
			parseRows(rows);
		}
	}

	private static final String[] REQUIRED_COLUMNS =
			{"adventureID", "questTitle", "adventureCompletionType", "description", "triggerMapName", "triggerRow",
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
				GameManagerAdventureDTO adventure = parseAdventure(values);
				if (adventure != null)
				{
					adventureData.add(adventure);
				}
			}
		}
	}

	/**
	 * Parse out adventure data into an Adventure DTO.
	 *
	 * @param values
	 *            - the values
	 * @return - player DTO
	 * @throws DatabaseException
	 */
	private GameManagerAdventureDTO parseAdventure(String[] values) throws DatabaseException
	{
		int questID = 0;
		String questName = null;
		String description = null;
		String parameterText = null;
		int exp = 0;
		String triggerMapName = null;
		int triggerRow = 0;
		int triggerCol = 0;

		AdventureCompletionCriteria parameter = null;

		AdventureCompletionType type = AdventureCompletionType.REAL_LIFE;
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
				case "adventureCompletionType":
					if (values[i].toString().equalsIgnoreCase("REAL_LIFE"))
					{
						type = AdventureCompletionType.REAL_LIFE;
					}
					else if (values[i].toString().equalsIgnoreCase("MOVEMENT"))
					{
						type = AdventureCompletionType.MOVEMENT;
					}
					else if (values[i].toString().equalsIgnoreCase("CHAT"))
					{
						type = AdventureCompletionType.CHAT;
					}
					else if (values[i].toString().equalsIgnoreCase("KNOWLEDGE_POINTS"))
					{
						type = AdventureCompletionType.KNOWLEDGE_POINTS;
					}
					else if (values[i].toString().equalsIgnoreCase("KEYSTROKE"))
					{
						type = AdventureCompletionType.KEYSTROKE;

					}
					else if (values[i].toString().equalsIgnoreCase("FRIENDS"))
					{
						type = AdventureCompletionType.FRIENDS;

					}
					else if (values[i].toString().equalsIgnoreCase("TERMINAL"))
					{
						type = AdventureCompletionType.TERMINAL;

					}
					break;

				case "description":
					description = values[i];
					break;
				case "parameter":
					parameterText = values[i];
					break;
				case "triggerMapName":
					if (type == AdventureCompletionType.MOVEMENT)
					{
						triggerMapName = values[i];
					}
					break;
				case "triggerRow":
					if (type == AdventureCompletionType.MOVEMENT)
					{
						triggerRow = Integer.parseInt(values[i]);
					}
					break;
				case "triggerCol":
					if (type == AdventureCompletionType.MOVEMENT)
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

		// If AdventureActionType is Real Life, create CriteriaStringDTO for
		// description.
		if (type.equals(AdventureCompletionType.REAL_LIFE))
		{
			parameter = new CriteriaStringDTO(parameterText);
		}
		else if (type.equals(AdventureCompletionType.MOVEMENT))
		{
			Position p = new Position(triggerRow, triggerCol);
			parameter = new GameLocationDTO(triggerMapName, p);

		}
		else if (type.equals(AdventureCompletionType.CHAT))
		{
			parameter = new CriteriaStringDTO(parameterText);

		}
		else if (type.equals(AdventureCompletionType.KNOWLEDGE_POINTS))
		{
			parameter = new CriteriaIntegerDTO(Integer.parseInt(parameterText));

		}
		else if (type.equals(AdventureCompletionType.KEYSTROKE))
		{
			parameter = new CriteriaStringDTO(parameterText);

		}

		// get questId for adventure
		QuestRecord quest = GameManagerQuestManager.getInstance().getQuest(questName);
		questID = quest.getQuestID();

		// Check if adventure already exists with quest
		for (AdventureRecord a : quest.getAdventures())
		{
			if (a.getAdventureDescription().equals(description))
			{
				return null;
			}

		}

		return new GameManagerAdventureDTO(questID, questName, description, exp, type, parameter,
				java.sql.Date.valueOf(startDate), java.sql.Date.valueOf(endDate));
	}

	/**
	 * Return adventure data.
	 *
	 * @return - list of DTOs containing adventure data
	 */
	public ArrayList<GameManagerAdventureDTO> getAdventureData()
	{
		return adventureData;
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
	 * Returns the number of adventures parsed out of the CSV.
	 *
	 * @return - number of adventures
	 */
	public int getNumberOfAdventures()
	{
		return adventureData.size();
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
