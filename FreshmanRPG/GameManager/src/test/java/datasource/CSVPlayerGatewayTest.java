package datasource;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import dataDTO.GameManagerPlayerDTO;

/**
 * Tests the functionality of CSVPlayerGateway.
 */
public class CSVPlayerGatewayTest
{
	/**
	 * Instantiate a new CSVPlayerGateway and return the instance.
	 *
	 * @param input - the player data
	 * @return instance of CSVPlayerGateway
	 * @throws InvalidColumnException - invalid header
	 */
	private CSVPlayerGateway createFromString(String input) throws InvalidColumnException
	{
		return new CSVPlayerGateway(input);
	}

	/**
	 * Ensure that we can parse out the header.
	 *
	 * @throws InvalidColumnException - invalid header
	 */
	@Test
	public void testColumnIndices() throws InvalidColumnException
	{
		final String password = "password";
		final String crew = "crew";
		final String major = "major";
		final String section = "section";
		final String name = "name";
		final CSVPlayerGateway gateway = createFromString(password + ", " + crew + ", " + major + ", "
				+ section + ", " + name);

		assertEquals(password, gateway.getColumn(0));
		assertEquals(crew, gateway.getColumn(1));
		assertEquals(major, gateway.getColumn(2));
		assertEquals(section, gateway.getColumn(3));
		assertEquals(name, gateway.getColumn(4));
	}

	/**
	 * Should throw an exception with invalid column names in header.
	 *
	 * @throws InvalidColumnException - invalid columns
	 */
	@Test(expected = InvalidColumnException.class)
	public void testThrowsExceptionWithInvalidHeader() throws InvalidColumnException
	{
		createFromString("chocolate, strawberry, Jackie Chan, Boomerang, The Lord of the Rings");
	}

	/**
	 * Should be able to create an instance with a path to a CSV and that it correctly
	 * reads from that file.
	 *
	 * @throws InvalidColumnException - invalid header
	 * @throws IOException - bad path
	 */
	@Test
	public void testCreateFromCSV() throws InvalidColumnException, IOException
	{
		final Path path = Paths.get("testdata/players.csv");
		final CSVPlayerGateway gateway = new CSVPlayerGateway(path);

		assertEquals(4, gateway.getNumberOfPlayers());

		final GameManagerPlayerDTO player = gateway.getPlayerData().get(1);
		assertEquals("Armstrong", player.getName());
		assertEquals("algorithms_is_Fun", player.getPassword());
		assertEquals(2, player.getCrew());
		assertEquals(1, player.getMajor());
		assertEquals(3, player.getSection());
	}

	/**
	 * Single player.
	 *
	 * @throws InvalidColumnException - invalid header
	 */
	@Test
	public void testPlayerWithOneRow() throws InvalidColumnException
	{
		final String name = "a name";
		final String password = "hi";
		final int crew = 2;
		final int major = 2;
		final int section = 7;
		final String input = "major, section, name, crew, password" +
				System.getProperty("line.separator") +
				major + ", " + section + ", " + name + ", " + crew + ", " + password;

		final CSVPlayerGateway gateway = createFromString(input);
		final GameManagerPlayerDTO player = gateway.getPlayerData().get(0);

		assertEquals(major, player.getMajor());
		assertEquals(section, player.getSection());
		assertEquals(name, player.getName());
		assertEquals(crew, player.getCrew());
		assertEquals(password, player.getPassword());
	}

	/**
	 * Multiple players.
	 *
	 * @throws InvalidColumnException - invalid header
	 */
	@Test
	public void testPlayerWithThreeRows() throws InvalidColumnException
	{
		final int numPlayers = 3;
		final String[] names = {"a name", "are you sure", "other name"};
		final String[] passwords = {"hi", "hello", "hi"};
		final int[] crews = {1, 2, 3};
		final int[] majors = {3, 2, 1};
		final int[] sections = {7, 4, 7};

		final String input = "name, major, section, crew, password" +
				System.getProperty("line.separator") +
				names[0] + ", " + majors[0] + ", " + sections[0] + ", " + crews[0] + ", " +
				passwords[0] + System.getProperty("line.separator") +
				names[1] + ", " + majors[1] + ", " + sections[1] + ", " + crews[1] + ", " +
				passwords[1] + System.getProperty("line.separator") +
				names[2] + ", " + majors[2] + ", " + sections[2] + ", " + crews[2] + ", " +
				passwords[2];

		final CSVPlayerGateway gateway = createFromString(input);

		for (int i = 0; i < numPlayers; i++)
		{
			assertEquals(names[i], gateway.getPlayerData().get(i).getName());
			assertEquals(passwords[i], gateway.getPlayerData().get(i).getPassword());
			assertEquals(crews[i], gateway.getPlayerData().get(i).getCrew());
			assertEquals(majors[i], gateway.getPlayerData().get(i).getMajor());
			assertEquals(sections[i], gateway.getPlayerData().get(i).getSection());
		}
	}

	/**
	 * The parser should trim any whitespace in the data where applicable.
	 *
	 * @throws InvalidColumnException - invalid header
	 */
	@Test
	public void testTrimData() throws InvalidColumnException
	{
		final String name = "a name";
		final String password = "hi";
		final int crew = 1;
		final int major = 3;
		final int section = 7;
		final String input = "major, section, name, crew, password" +
				System.getProperty("line.separator") +
				"3, 7, a name        ,1, hi          ";

		final CSVPlayerGateway gateway = createFromString(input);
		final GameManagerPlayerDTO player = gateway.getPlayerData().get(0);

		assertEquals(major, player.getMajor());
		assertEquals(section, player.getSection());
		assertEquals(name, player.getName());
		assertEquals(crew, player.getCrew());
		assertEquals(password, player.getPassword());
	}

	/**
	 * No players.
	 *
	 * @throws InvalidColumnException - invalid header
	 */
	@Test
	public void testZeroPlayers() throws InvalidColumnException
	{
		final String input = "name, major, section, crew, password";
		final CSVPlayerGateway gateway = createFromString(input);

		assertEquals(0, gateway.getNumberOfPlayers());
	}

	/**
	 * If an empty field is encountered, the parser should skip that entire row.
	 *
	 * @throws InvalidColumnException - invalid header
	 */
	@Test
	public void testEmptyField() throws InvalidColumnException
	{
		final String input = "major, section, name, crew, password" +
				System.getProperty("line.separator") +
				"1,, a name, 2, hi" +
				System.getProperty("line.separator") +
				"1, 4, Robert, 2, password";

		final CSVPlayerGateway gateway = createFromString(input);

		assertEquals(1, gateway.getNumberOfPlayers());
	}

	/**
	 * If a field with only whitespace is encountered, the parser should skip that entire row.
	 *
	 * @throws InvalidColumnException - invalid header
	 */
	@Test
	public void testWhitespaceOnlyField() throws InvalidColumnException
	{
		final String input = "major, section, name, crew, password" +
				System.getProperty("line.separator") +
				"1, 						           			, a name, 2, hi" +
				System.getProperty("line.separator") +
				"1, 4, Robert, 2, password";

		final CSVPlayerGateway gateway = createFromString(input);

		assertEquals(1, gateway.getNumberOfPlayers());
	}

}
