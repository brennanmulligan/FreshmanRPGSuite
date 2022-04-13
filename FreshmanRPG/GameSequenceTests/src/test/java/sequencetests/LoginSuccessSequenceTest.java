package sequencetests;

import java.io.IOException;
import java.util.ArrayList;

import communication.messages.ConnectMessage;
import communication.messages.InitializeThisClientsPlayerMessage;
import communication.messages.KnowledgePointPrizeMessage;
import communication.messages.LoginMessage;
import communication.messages.LoginSuccessfulMessage;
import communication.messages.MapFileMessage;
import communication.messages.PlayerJoinedMessage;
import communication.messages.TimeToLevelUpDeadlineMessage;
import communication.packers.MapFileMessagePacker;
import dataDTO.AdventureStateRecordDTO;
import dataDTO.ClientPlayerAdventureStateDTO;
import dataDTO.ClientPlayerQuestStateDTO;
import dataDTO.FriendDTO;
import dataDTO.KnowledgePointPrizeDTO;
import dataDTO.QuestStateRecordDTO;
import datasource.AdventureStateTableDataGateway;
import datasource.AdventureStateTableDataGatewayMock;
import datasource.AdventureTableDataGateway;
import datasource.AdventureTableDataGatewayMock;
import datasource.DatabaseException;
import datasource.FriendTableDataGateway;
import datasource.FriendTableDataGatewayMock;
import datasource.KnowledgePointPrizesTDGMock;
import datasource.LevelRecord;
import datasource.PlayerConnectionRowDataGatewayMock;
import datasource.QuestRowDataGateway;
import datasource.QuestRowDataGatewayMock;
import datasource.QuestStateTableDataGateway;
import datasource.QuestStateTableDataGatewayMock;
import datatypes.QuestStateEnum;
import model.AdventureRecord;
import model.Command;
import model.CommandLogin;
import model.MessageFlow;
import model.OptionsManager;
import model.PlayerManager;
import model.QuestManager;
import model.SequenceTest;
import model.ServerType;
import datatypes.LevelsForTest;
import datatypes.PlayersForTest;
import datatypes.ServersForTest;

/**
 * Defines the protocol for a successful login sequence
 *
 * @author Merlin
 *
 */
public class LoginSuccessSequenceTest extends SequenceTest
{

	private static final LevelRecord LEVEL_TWO_RECORD = new LevelRecord(LevelsForTest.TWO.getDescription(), LevelsForTest.TWO.getLevelUpPoints(),
			LevelsForTest.TWO.getLevelUpMonth(), LevelsForTest.TWO.getLevelUpDayOfMonth());

	private MessageFlow[] sequence =
			{new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.LOGIN_SERVER,
					new LoginMessage(PlayersForTest.MERLIN_OFFLINE.getPlayerName(), PlayersForTest.MERLIN_OFFLINE.getPlayerPassword()), true),
					new MessageFlow(ServerType.LOGIN_SERVER, ServerType.THIS_PLAYER_CLIENT,
							new LoginSuccessfulMessage(PlayersForTest.MERLIN_OFFLINE.getPlayerID(),
									ServersForTest.THEGREEN.getHostName(), ServersForTest.THEGREEN.getPortNumber(), 33),
							true),
					new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
							new ConnectMessage(PlayersForTest.MERLIN_OFFLINE.getPlayerID(), PlayersForTest.MERLIN_OFFLINE.getPin()), true),
					new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
							new PlayerJoinedMessage(PlayersForTest.MERLIN_OFFLINE.getPlayerID(), PlayersForTest.MERLIN_OFFLINE.getPlayerName(),
									PlayersForTest.MERLIN_OFFLINE.getAppearanceType(), PlayersForTest.MERLIN_OFFLINE.getPosition(),
									PlayersForTest.MERLIN_OFFLINE.getCrew(), PlayersForTest.MERLIN_OFFLINE.getMajor(), PlayersForTest.MERLIN_OFFLINE.getSection()),
							true),
					new MessageFlow(ServerType.AREA_SERVER, ServerType.OTHER_CLIENT,
							new PlayerJoinedMessage(PlayersForTest.MERLIN_OFFLINE.getPlayerID(), PlayersForTest.MERLIN_OFFLINE.getPlayerName(),
									PlayersForTest.MERLIN_OFFLINE.getAppearanceType(), PlayersForTest.MERLIN_OFFLINE.getPosition(),
									PlayersForTest.MERLIN_OFFLINE.getCrew(), PlayersForTest.MERLIN_OFFLINE.getMajor(), PlayersForTest.MERLIN_OFFLINE.getSection()),
							true),
					new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
							new MapFileMessage(MapFileMessagePacker.DIRECTORY_PREFIX + ServersForTest.THEGREEN.getMapName()),
							true),
					new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
							new InitializeThisClientsPlayerMessage(getPlayersQuest(PlayersForTest.MERLIN_OFFLINE.getPlayerID()), getPlayersFriends(PlayersForTest.MERLIN_OFFLINE.getPlayerID()),
									PlayersForTest.MERLIN_OFFLINE.getExperiencePoints(), PlayersForTest.MERLIN_OFFLINE.getKnowledgeScore(),
									LEVEL_TWO_RECORD),
							true),

					new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
							new TimeToLevelUpDeadlineMessage(PlayersForTest.MERLIN_OFFLINE.getPlayerID(), LEVEL_TWO_RECORD.getDeadlineDate(),
									LevelsForTest.TWO.getDescription()),
							true),
					new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
							new KnowledgePointPrizeMessage(PlayersForTest.MERLIN_OFFLINE.getPlayerID(), getKnowledgePointPrizeList()),
							true)};


	/**
	 * @throws IOException
	 *             shouldn't
	 */
	public LoginSuccessSequenceTest() throws IOException
	{
		for (MessageFlow mf : sequence)
		{
			messageSequence.add(mf);
		}

		serverList.add(ServerType.THIS_PLAYER_CLIENT);
		serverList.add(ServerType.OTHER_CLIENT);
		serverList.add(ServerType.LOGIN_SERVER);
		serverList.add(ServerType.AREA_SERVER);

	}

	private ArrayList<KnowledgePointPrizeDTO> getKnowledgePointPrizeList()
	{
		try
		{
			return KnowledgePointPrizesTDGMock.getInstance().getAllKnowledgePointPrizes();
		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private ArrayList<FriendDTO> getFriendList()
	{
		try
		{
			return FriendTableDataGatewayMock.getSingleton().getAllFriends(PlayersForTest.MERLIN_OFFLINE.getPlayerID());
		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private ArrayList<ClientPlayerQuestStateDTO> getPlayersQuest(int playerID)
	{
		ArrayList<ClientPlayerQuestStateDTO> result = new ArrayList<>();
		QuestStateTableDataGateway qsGateway = QuestStateTableDataGatewayMock.getSingleton();
		try
		{
			for (QuestStateRecordDTO q : qsGateway.getQuestStates(playerID))
			{
				result.add(createClientPlayerQuestFor(q));
			}
		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	private ArrayList<FriendDTO> getPlayersFriends(int playerID)
	{
		ArrayList<FriendDTO> result = new ArrayList<>();
		FriendTableDataGateway friendGateway = FriendTableDataGatewayMock.getSingleton();
		try
		{
			result = friendGateway.getAllFriends(playerID);
		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	private ClientPlayerQuestStateDTO createClientPlayerQuestFor(QuestStateRecordDTO q) throws DatabaseException
	{
		QuestRowDataGateway qGateway = new QuestRowDataGatewayMock(q.getQuestID());
		ClientPlayerQuestStateDTO cpq = new ClientPlayerQuestStateDTO(q.getQuestID(), qGateway.getQuestTitle(),
				qGateway.getQuestDescription(), q.getState(), qGateway.getExperiencePointsGained(),
				qGateway.getAdventuresForFulfillment(), q.isNeedingNotification(), null);
		AdventureStateTableDataGateway asGateway = AdventureStateTableDataGatewayMock.getSingleton();
		ArrayList<AdventureStateRecordDTO> adventuresForPlayer = asGateway.getAdventureStates(q.getPlayerID(),
				q.getQuestID());
		for (AdventureStateRecordDTO adv : adventuresForPlayer)
		{

			AdventureTableDataGateway aGateway = AdventureTableDataGatewayMock.getSingleton();
			AdventureRecord adventureRecord = aGateway.getAdventure(q.getQuestID(), adv.getAdventureID());

			cpq.addAdventure(new ClientPlayerAdventureStateDTO(adv.getAdventureID(),
					adventureRecord.getAdventureDescription(), adventureRecord.getExperiencePointsGained(),
					adv.getState(), adv.isNeedingNotification(), adventureRecord.isRealLifeAdventure(),
					adventureRecord.getCompletionCriteria().toString(), QuestStateEnum.AVAILABLE));

		}
		return cpq;
	}

	/**
	 * @see SequenceTest#getInitiatingCommand()
	 */
	@Override
	public Command getInitiatingCommand()
	{
		return new CommandLogin(PlayersForTest.MERLIN_OFFLINE.getPlayerName(), PlayersForTest.MERLIN_OFFLINE.getPlayerPassword());
	}

	/**
	 * @see model.SequenceTest#getInitiatingServerType()
	 */
	@Override
	public ServerType getInitiatingServerType()
	{
		return ServerType.THIS_PLAYER_CLIENT;
	}

	/**
	 * @see model.SequenceTest#getInitiatingPlayerID()
	 */
	@Override
	public int getInitiatingPlayerID()
	{
		return PlayersForTest.MERLIN_OFFLINE.getPlayerID();
	}

	/**
	 * @see model.SequenceTest#setUpMachines()
	 */
	@Override
	public void setUpMachines()
	{
		OptionsManager.getSingleton().setMapName(PlayersForTest.MERLIN_OFFLINE.getMapName());
		PlayerManager.resetSingleton();
		QuestManager.resetSingleton();
	}

	/**
	 * @see model.SequenceTest#resetDataGateways()
	 */
	@Override
	public void resetDataGateways()
	{
		try
		{
			PlayerManager.resetSingleton();
			(new PlayerConnectionRowDataGatewayMock(PlayersForTest.MERLIN_OFFLINE.getPlayerID())).resetData();
		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
		}
	}

}
