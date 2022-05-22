package datasource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import dataDTO.PlayerDTO;
import dataDTO.VanityDTO;
import datatypes.Crew;
import datatypes.Major;
import datatypes.PlayerScoreRecord;
import datatypes.PlayersForTest;
import datatypes.Position;

/**
 * A mock data source that provides a table data gateway view into the Players
 * table
 *
 * @author Merlin
 *
 */
public class PlayerTableDataGatewayMock
		implements PlayerTableDataGateway
{

	private ArrayList<MockPlayerAndLoginJoinRow> data;

	private final HashMap<Integer, PlayerDTO> saveEdits =
			new HashMap<>();


	private static class MockPlayerAndLoginJoinRow
	{
		String playerName;
		String appearanceType;
		int quizScore;
		int experiencePoints;
		Crew crew;
		Major major;
		int section;
		String password;
		Position position;
		String mapName;
		int playerID;
		boolean online;
		ArrayList<String> visitedMaps;

		public MockPlayerAndLoginJoinRow(int playerID, String playerName, String password, String appearanceType, String mapName,
										 Position position, int quizScore, int experiencePoints, Crew crew, Major major, int section, boolean online,
										 ArrayList<String> visitedMaps)
		{
			this.playerID = playerID;
			this.playerName = playerName;
			this.password = password;
			this.appearanceType = appearanceType;
			this.quizScore = quizScore;
			this.experiencePoints = experiencePoints;
			this.crew = crew;
			this.major = major;
			this.section = section;
			this.position = position;
			this.mapName = mapName;
			this.online = online;
			this.visitedMaps = visitedMaps;
		}
	}


	static TableDataGateway getGateway()
	{
		return new PlayerTableDataGatewayMock();
	}
	/**
	 * just build the data from the PlayersForTest enum
	 */
	private PlayerTableDataGatewayMock()
	{
		resetTableGateway();
	}


	@Override
	public void resetTableGateway()
	{
		data = new ArrayList<>();
		for (PlayersForTest p : PlayersForTest.values())
		{

			data.add(new MockPlayerAndLoginJoinRow(p.getPlayerID(), p.getPlayerName(),
					p.getPlayerPassword(), p.getAppearanceType(),
					p.getMapName(), p.getPosition(), p.getDoubloons(),
					p.getExperiencePoints(), p.getCrew(), p.getMajor(),
					p.getSection(), p.getOnline(), p.getMapsVisited()));
		}

	}

	/**
	 * @see datasource.PlayerTableDataGateway#getTopTenList()
	 */
	@Override
	public ArrayList<PlayerScoreRecord> getTopTenList()
	{
		ArrayList<PlayerScoreRecord> result = new ArrayList<>();

		for (int i = 0; i < 10; i++)
		{
			if (i < data.size())
			{
				MockPlayerAndLoginJoinRow curr = data.get(i);
				result.add(convertToPlayerScoreRecord(curr));
			}
		}
		result.add(0,
				new PlayerScoreRecord(PlayersForTest.MERLIN.getPlayerName(),
						PlayersForTest.MERLIN.getExperiencePoints(), PlayersForTest.MERLIN.getCrew().name(),
						PlayersForTest.MERLIN.getSection()));
		result.remove(10);

		return result;
	}

	private PlayerScoreRecord convertToPlayerScoreRecord(MockPlayerAndLoginJoinRow curr)
	{
		return new PlayerScoreRecord(curr.playerName, curr.experiencePoints, curr.crew.getCrewName(),
				curr.section);
	}

	/**
	 * @see datasource.PlayerTableDataGateway#getHighScoreList()
	 */
	@Override
	public ArrayList<PlayerScoreRecord> getHighScoreList()
	{
		ArrayList<PlayerScoreRecord> playerList = new ArrayList<>();

		for (MockPlayerAndLoginJoinRow row : data)
		{
			playerList.add(convertToPlayerScoreRecord(row));
		}
		Collections.sort(playerList, Comparator.naturalOrder());
		return playerList;
	}


	/**
	 * @see datasource.PlayerTableDataGateway#retrieveAllPlayers()
	 * TODO: Get actual vanity items
	 */
	@Override
	public ArrayList<PlayerDTO> retrieveAllPlayers()
	{
		ArrayList<PlayerDTO> result = new ArrayList<>();
		//For each key and value add a new playerInfos
		for (MockPlayerAndLoginJoinRow row : data)
		{
			if (!saveEdits.containsKey(row.playerID))
			{
				result.add(new PlayerDTO(row.playerID, row.playerName,
						row.password, row.appearanceType, row.quizScore,
						row.position, row.mapName, row.experiencePoints,
						row.crew, row.major, row.section, row.visitedMaps, new ArrayList<>()));

			}
			else
			{
				result.add(saveEdits.get(row.playerID));
			}
		}
		return result;
	}

	/**
	 * For testing purposes
	 * This method is meant to save the playerId of players that will be edited
	 * This will then be used in retrieve all players so PlayersForTest are not
	 * used to overwrite edits
	 * @param playerID - id of the player
	 * @param playerName - players name
	 * @param password - players password
	 * @param appearanceType - players appearance type
	 * @param quizScore - players quiz score
	 * @param position - position of the player
	 * @param mapName - name of the map the player is on
	 * @param experiencePoints - the amount of experience player has
	 * @param crew - the players crew
	 * @param major - the players major
	 * @param section - the section number of the player
	 * @param visitedMaps - maps the player has visited
	 */
	public void saveEditedPlayer(int playerID, String playerName,
								 String password, String appearanceType, int quizScore,
								 Position position, String mapName, int experiencePoints,
								 Crew crew, Major major, int section, ArrayList<String> visitedMaps, ArrayList<VanityDTO> vanityDTOs)
	{
		saveEdits.put(playerID, new PlayerDTO(playerID, playerName,
				password, appearanceType, quizScore,
				position, mapName, experiencePoints,
				crew, major, section, visitedMaps, vanityDTOs));
	}

	/**
	 * Retrieve all online players
	 * TODO: Get actual vanity items
	 */
	@Override
	public ArrayList<PlayerDTO> retrieveAllOnlinePlayers()
	{
		ArrayList<PlayerDTO> result = new ArrayList<>();
		//for each key and value, check if player is online and add to result
		for (MockPlayerAndLoginJoinRow row : data)
		{
			// check if player for test is online
			if (row.online)
			{
				// check if they are not in saveEdits
				if (!saveEdits.containsKey(row.playerID))
				{
					result.add(new PlayerDTO(row.playerID, row.playerName,
							row.password, row.appearanceType, row.quizScore,
							row.position, row.mapName, row.experiencePoints,
							row.crew, row.major, row.section, row.visitedMaps, new ArrayList<>()));
				}
				// if they are, grab them and add it to the list
				else
				{
					result.add(saveEdits.get(row.playerID));
				}
			}
		}
		return result;
	}
}
