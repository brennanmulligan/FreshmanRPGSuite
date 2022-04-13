package sequencetests;

import java.io.IOException;

import communication.messages.AdventureNotificationCompleteMessage;
import model.ClientModelTestUtilities;
import model.Command;
import model.CommandAdventureNotificationComplete;
import model.MapManager;
import model.MessageFlow;
import model.PlayerManager;
import model.SequenceTest;
import model.ServerType;
import datatypes.PlayersForTest;

/**
 * Tests that a notification is sent when an adventure is completed
 * @author Ronald Sease & Evan Stevenson
 */
public class AdventureNotificationCompleteSequenceTest extends SequenceTest
{
	private MessageFlow[] sequence =
			{
					new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
							new AdventureNotificationCompleteMessage(PlayersForTest.MERLIN.getPlayerID(), 1, 1), false)
			};

	/**
	 * @throws IOException
	 *         shouldn't
	 */
	public AdventureNotificationCompleteSequenceTest() throws IOException
	{
		for (MessageFlow mf : sequence)
		{
			messageSequence.add(mf);
		}

		serverList.add(ServerType.THIS_PLAYER_CLIENT);
		serverList.add(ServerType.AREA_SERVER);
	}

	/**
	 * @see model.SequenceTest#getInitiatingCommand()
	 */
	@Override
	public Command getInitiatingCommand()
	{
		return new CommandAdventureNotificationComplete(PlayersForTest.MERLIN.getPlayerID(), 1, 1);
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
		return PlayersForTest.MERLIN.getPlayerID();
	}

	/**
	 * @see model.SequenceTest#setUpMachines()
	 */
	@Override
	public void setUpMachines()
	{
		MapManager.getSingleton().changeToNewFile(PlayersForTest.MERLIN.getMapName());
		ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.MERLIN);
		PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());

	}

	/**
	 * @see model.SequenceTest#resetDataGateways()
	 */
	@Override
	public void resetDataGateways()
	{
		PlayerManager.resetSingleton();
	}
}
