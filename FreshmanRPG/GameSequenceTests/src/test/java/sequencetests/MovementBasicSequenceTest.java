package sequencetests;

import java.io.IOException;

import communication.messages.OtherPlayerMovedMessage;
import communication.messages.PlayerMovedMessage;
import datatypes.Position;
import model.ClientModelTestUtilities;
import model.Command;
import model.CommandClientMovePlayer;
import model.MapManager;
import model.MessageFlow;
import model.PlayerManager;
import model.SequenceTest;
import model.ServerType;
import datatypes.PlayersForTest;

/**
 * Defines the protocol for a successful login sequence
 *
 * @author Merlin
 *
 */
public class MovementBasicSequenceTest extends SequenceTest
{

	private MessageFlow[] sequence =
			{
					new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
							new PlayerMovedMessage(PlayersForTest.MATT.getPlayerID(),
									new Position(PlayersForTest.MATT.getPosition().getRow(),
											PlayersForTest.MATT.getPosition().getColumn() + 1)),
							true),
					new MessageFlow(ServerType.AREA_SERVER, ServerType.OTHER_CLIENT,
							new OtherPlayerMovedMessage(PlayersForTest.MATT.getPlayerID(),
									new Position(PlayersForTest.MATT.getPosition().getRow(),
											PlayersForTest.MATT.getPosition().getColumn() + 1)),
							true)};

	/**
	 * @throws IOException
	 *             shouldn't
	 */
	public MovementBasicSequenceTest() throws IOException
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
		return new CommandClientMovePlayer(PlayersForTest.MATT.getPlayerID(), new Position(
				PlayersForTest.MATT.getPosition().getRow(), PlayersForTest.MATT
				.getPosition().getColumn() + 1));
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
	}
}
