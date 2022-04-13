package datasource;

import java.util.HashMap;

import datatypes.Crew;
import datatypes.Major;
import datatypes.PlayersForTest;
import datatypes.Position;

/**
 * A mock implementation for PlayerRowDataGateway
 *
 * @author Merlin
 *
 */
public class PlayerRowDataGatewayMock implements PlayerRowDataGateway
{

	/**
	 * Map player ID to player information
	 */
	private static HashMap<Integer, MockPlayerTableRow> playerInfo;
	private static int nextKey = 1;
	private int playerID;
	private MockPlayerTableRow info;

	private class MockPlayerTableRow
	{

		String appearanceType;
		int quizScore;
		int experiencePoints;
		Crew crew;
		Major major;
		int section;
		int buffPool;
		boolean online;

		public MockPlayerTableRow(String appearanceType, int quizScore, int experiencePoints, Crew crew, Major major,
								  int section, int buffPool, boolean online)
		{
			this.appearanceType = appearanceType;
			this.quizScore = quizScore;
			this.experiencePoints = experiencePoints;
			this.crew = crew;
			this.major = major;
			this.section = section;
			this.buffPool = buffPool;
			this.online = online;
		}
	}

	/**
	 * Finder constructor - will initialize itself from the stored information
	 *
	 * @param playerID
	 *            the ID of the player we are looking for
	 * @throws DatabaseException
	 *             if the playerID isn't in the data source
	 */
	public PlayerRowDataGatewayMock(int playerID) throws DatabaseException
	{
		if (playerInfo == null)
		{
			resetData();
		}

		if (playerInfo.containsKey(playerID))
		{
			info = playerInfo.get(playerID);
			this.playerID = playerID;
		}
		else
		{
			throw new DatabaseException("Couldn't find player with ID " + playerID);
		}
	}

	/**
	 * Create constructor - will add the information as a new row in the data
	 * source as the object is constructed
	 *
	 * @param appearanceType the appearance type of the player
	 * @param quizScore this player's current quiz score
	 * @param experiencePoints this player's experience points
	 * @param crew the crew to which this player belongs
	 * @param major the major of this player
	 * @param section that player is in
	 * @param pin - pin of player
	 * @param mapName - name of the map that player is currently on
	 * @param position - position of the player
	 * @param buffPool - Quiznasium Bonus Point pool of the player
	 */
	public PlayerRowDataGatewayMock(String appearanceType, int quizScore, int experiencePoints, Crew crew, Major major,
									int section, int buffPool, boolean online)
	{
		if (playerInfo == null)
		{
			resetData();
		}
		playerID = nextKey;
		nextKey++;
		MockPlayerTableRow mockInfo = new MockPlayerTableRow(appearanceType, quizScore, experiencePoints, crew, major,
				section, buffPool, online);
		playerInfo.put(playerID, mockInfo);
		info = mockInfo;
	}

	/**
	 * Just used by tests to reset static information
	 */
	public PlayerRowDataGatewayMock()
	{
	}

	/**
	 * @see datasource.PlayerRowDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
		playerInfo = new HashMap<>();
		nextKey = 1;
		for (PlayersForTest p : PlayersForTest.values())
		{
			playerInfo.put(nextKey, new MockPlayerTableRow(p.getAppearanceType(), p.getKnowledgeScore(),
					p.getExperiencePoints(), p.getCrew(), p.getMajor(), p.getSection(), p.getBuffPool(), p.getOnline()));
			nextKey++;
		}
	}

	/**
	 * Returns the number of mock players.
	 *
	 * @return - the number of mock players
	 */
	public int getNumberOfMockPlayers()
	{
		return playerInfo.size();
	}

	/**
	 * @see datasource.PlayerRowDataGateway#getPlayerID()
	 */
	@Override
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#getAppearanceType()
	 */
	@Override
	public String getAppearanceType()
	{
		return info.appearanceType;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#persist()
	 */
	@Override
	public void persist()
	{
		playerInfo.put(playerID, info);
	}

	/**
	 * @see datasource.PlayerRowDataGateway#setAppearanceType(java.lang.String)
	 */
	@Override
	public void setAppearanceType(String appearanceType)
	{
		info.appearanceType = appearanceType;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#getQuizScore()
	 */
	@Override
	public int getQuizScore()
	{
		return info.quizScore;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#setQuizScore(int)
	 */
	@Override
	public void setQuizScore(int quizScore)
	{
		info.quizScore = quizScore;

	}

	/**
	 * @see datasource.PlayerRowDataGateway#getExperiencePoints()
	 */
	@Override
	public int getExperiencePoints()
	{
		return info.experiencePoints;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#setExperiencePoints(int)
	 */
	@Override
	public void setExperiencePoints(int experiencePoints)
	{
		info.experiencePoints = experiencePoints;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#getCrew()
	 */
	@Override
	public Crew getCrew()
	{
		return info.crew;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#setCrew(datatypes.Crew)
	 */
	@Override
	public void setCrew(Crew crew)
	{
		info.crew = crew;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#getMajor()
	 */
	@Override
	public Major getMajor()
	{
		return info.major;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#setMajor(datatypes.Major)
	 */
	@Override
	public void setMajor(Major major)
	{
		info.major = major;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#getSection()
	 */
	@Override
	public int getSection()
	{
		return info.section;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#setSection(int)
	 */
	@Override
	public void setSection(int section)
	{
		info.section = section;
	}

	/**
	 * Delete the player this instance represents
	 */
	@Override
	public void delete()
	{
		playerInfo.remove(playerID);
	}

	/**
	 * set buffPool
	 * @param buffPool the new buffPool
	 */
	@Override
	public void setBuffPool(int buffPool)
	{
		this.info.buffPool = buffPool;
	}

	/**
	 * get buffPool
	 * @return info.buffPool
	 */
	@Override
	public int getBuffPool()
	{
		return this.info.buffPool;
	}

	/**
	 * get online status
	 * @return online
	 */
	@Override
	public boolean getOnline()
	{
		return this.info.online;
	}

	/**
	 * set online status
	 * @param online
	 */
	@Override
	public void setOnline(boolean online)
	{
		this.info.online = online;
	}
}