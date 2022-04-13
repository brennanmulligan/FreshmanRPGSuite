package datasource;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import dataDTO.GameManagerPlayerDTO;

/**
 * Gateway to CSV file containing player info.
 */
public class CSVPlayerGateway
{
	private String[] columnNames;
	private ArrayList<GameManagerPlayerDTO> playerData;

	/**
	 * Create a new gateway.
	 *
	 * @param input
	 *            The CSV text to parse.
	 * @throws InvalidColumnException
	 *             - invalid header
	 */
	public CSVPlayerGateway(String input) throws InvalidColumnException
	{
		String[] lines = input.split(System.getProperty("line.separator"));
		final ArrayList<String> rows = new ArrayList<>();
		for (String line : lines)
		{
			rows.add(line);
		}

		try
		{
			parseColumns(rows.remove(0));
			playerData = new ArrayList<>();
			parsePlayers(rows);
		}
		catch (InvalidColumnException e)
		{
			throw new InvalidColumnException(e.getMessage());
		}
	}

	/**
	 * Create a new CSVPlayerGateway with given path.
	 *
	 * @param pathToFile
	 *            - the path to the file to read
	 *
	 * @throws IOException
	 *             - error reading file
	 * @throws InvalidColumnException
	 *             - invalid header
	 */
	public CSVPlayerGateway(Path pathToFile) throws IOException, InvalidColumnException
	{
		try (BufferedReader reader = Files.newBufferedReader(pathToFile))
		{
			final ArrayList<String> rows = new ArrayList<>();
			String line;
			while ((line = reader.readLine()) != null)
			{
				rows.add(line);
			}
			parseColumns(rows.remove(0));
			playerData = new ArrayList<>();
			parsePlayers(rows);
		}
	}

	/**
	 * Parse out the columns.
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
		String[] headerNames = header.split(",");
		for (int i = 0; i < headerNames.length; i++)
		{
			headerNames[i] = headerNames[i].trim();
		}
		final ArrayList<String> columns = new ArrayList<>(Arrays.asList(headerNames));

		// TODO this bad please fix.
		if (!columns.contains("name") || !columns.contains("password") || !columns.contains("crew")
				|| !columns.contains("major") || !columns.contains("section"))
		{
			throw new InvalidColumnException(
					"must contain columns 'name', 'password', 'crew' " + "'major', 'section' in any order!");
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
	 * Parse all player data out into list of player DTOs.
	 *
	 * @param rows
	 *            - player data
	 */
	private void parsePlayers(ArrayList<String> rows)
	{
		for (String row : rows)
		{
			final String[] values = row.split(" *, *");
			if (isRowValid(values))
			{
				playerData.add(parsePlayer(values));
			}
		}
	}

	/**
	 * Parse out player data into a DTO.
	 *
	 * @param values
	 *            - the values
	 * @return - player DTO
	 */
	private GameManagerPlayerDTO parsePlayer(String[] values)
	{
		String name = null;
		String password = null;
		int crew = 0;
		int major = 0;
		int section = 0;

		for (int i = 0; i < values.length; i++)
		{
			values[i] = values[i].trim();
			switch (getColumn(i))
			{
				case "name":
					name = values[i];
					break;
				case "password":
					password = values[i];
					break;
				case "crew":
					crew = Integer.parseInt(values[i]);
					break;
				case "major":
					major = Integer.parseInt(values[i]);
					break;
				case "section":
					section = Integer.parseInt(values[i]);
					break;
			}
		}
		return new GameManagerPlayerDTO(name, password, crew, major, section);
	}

	/**
	 * Return player data.
	 *
	 * @return - list of DTOs containing player data
	 */
	public ArrayList<GameManagerPlayerDTO> getPlayerData()
	{
		return playerData;
	}

	/**
	 * Returns the number of players parsed out of the CSV.
	 *
	 * @return - number of players
	 */
	public int getNumberOfPlayers()
	{
		return playerData.size();
	}

	/**
	 * Check if the values parsed from the row are valid meaning they should not
	 * be empty or contain just whitespace.
	 *
	 * @param values
	 *            - row expressed in terms of its values
	 * @return true if row is valid
	 */
	private boolean isRowValid(String[] values)
	{
		final Pattern pattern = Pattern.compile("^\\s*$");
		final List<String> matching = Arrays.asList(values).stream().filter(pattern.asPredicate())
				.collect(Collectors.toList());

		return matching.size() == 0;
	}

}
