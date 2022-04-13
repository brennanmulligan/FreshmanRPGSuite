package sequencetests;

import java.io.IOException;

import communication.messages.OtherPlayerMovedMessage;
import communication.messages.PlayerMovedMessage;
import communication.messages.QuestStateChangeMessage;
import datasource.DatabaseException;
import datasource.PlayerConnectionRowDataGatewayMock;
import datatypes.QuestStateEnum;
import model.ClientModelTestUtilities;
import model.Command;
import model.CommandClientMovePlayer;
import model.MapManager;
import model.MessageFlow;
import model.PlayerManager;
import model.QuestManager;
import model.SequenceTest;
import model.ServerType;
import datatypes.PlayersForTest;
import datatypes.QuestsForTest;


/**
 *
 * Test the sequence of messages that should flow when completing a test forces teleportation to
 * another server
 * @author Chris Hersh and Zach Thompson
 *
 */
public class FinishingQuestTeleportsSequenceTest extends SequenceTest
{

	private MessageFlow[] sequence =
			{
					new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
							new PlayerMovedMessage(PlayersForTest.MATT.getPlayerID(),
									QuestsForTest.THE_LITTLE_QUEST.getPosition()), true),
					new MessageFlow(ServerType.AREA_SERVER, ServerType.OTHER_CLIENT,
							new OtherPlayerMovedMessage(PlayersForTest.MATT.getPlayerID(),
									QuestsForTest.THE_LITTLE_QUEST.getPosition()), true),
					new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
							new QuestStateChangeMessage(PlayersForTest.MATT.getPlayerID(),
									QuestsForTest.THE_LITTLE_QUEST.getQuestID(),
									QuestsForTest.THE_LITTLE_QUEST.getQuestTitle(),
									QuestsForTest.THE_LITTLE_QUEST.getQuestDescription(), QuestStateEnum.TRIGGERED), true)};

	/**
	 * @throws IOException
	 *             shouldn't
	 */
	public FinishingQuestTeleportsSequenceTest() throws IOException
	{
		for (MessageFlow mf : sequence)
		{
			messageSequence.add(mf);
		}

		serverList.add(ServerType.THIS_PLAYER_CLIENT);
		serverList.add(ServerType.OTHER_CLIENT);
	}

	/**
	 * @see SequenceTest#getInitiatingCommand()
	 */
	public Command getInitiatingCommand()
	{
		return new CommandClientMovePlayer(PlayersForTest.MATT.getPlayerID(),
				QuestsForTest.THE_LITTLE_QUEST.getPosition());
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
		return PlayersForTest.MATT.getPlayerID();
	}

	/**
	 * @see model.SequenceTest#setUpMachines()
	 */
	@Override
	public void setUpMachines()
	{
		MapManager.getSingleton().changeToNewFile(PlayersForTest.MATT.getMapName());
		ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.MATT);
		PlayerManager.getSingleton().addPlayer(PlayersForTest.MATT.getPlayerID());
	}

	/**
	 * @see model.SequenceTest#resetDataGateways()
	 */
	public void resetDataGateways()
	{
		PlayerManager.resetSingleton();
		QuestManager.resetSingleton();
		try
		{
			(new PlayerConnectionRowDataGatewayMock(2)).resetData();
		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
		}
	}

}
