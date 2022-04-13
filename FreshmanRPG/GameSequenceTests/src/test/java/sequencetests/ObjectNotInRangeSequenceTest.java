package sequencetests;

import communication.messages.KeyInputMessage;
import model.ClientModelTestUtilities;
import model.Command;
import model.CommandKeyInputSent;
import model.InteractObjectManager;
import model.MapManager;
import model.MessageFlow;
import model.PlayerManager;
import model.SequenceTest;
import model.ServerType;
import datatypes.PlayersForTest;

/**
 * A player presses e and sends a message to the server, no message gets sent back
 * @author Elisabeth Ostrow, Stephen Clabaugh
 *
 */
public class ObjectNotInRangeSequenceTest extends SequenceTest
{

	/**
	 * the flow of messages to occur
	 */
	private MessageFlow[] sequence =
			{
					new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER, new KeyInputMessage("e"), true)

			};

	/**
	 * runs through the message flow
	 */
	public ObjectNotInRangeSequenceTest()
	{
		for (MessageFlow mf : sequence)
		{
			messageSequence.add(mf);
		}

		serverList.add(ServerType.THIS_PLAYER_CLIENT);
		serverList.add(ServerType.AREA_SERVER);
	}

	/**
	 * what starts the sequence of messages
	 */
	@Override
	public Command getInitiatingCommand()
	{
		return new CommandKeyInputSent("e");
	}

	/**
	 * the server the command starts on
	 */
	@Override
	public ServerType getInitiatingServerType()
	{
		return ServerType.THIS_PLAYER_CLIENT;
	}

	/**
	 * the player for these tests
	 */
	@Override
	public int getInitiatingPlayerID()
	{
		return PlayersForTest.MERLIN.getPlayerID();
	}

	/**
	 * any setup required
	 */
	@Override
	public void setUpMachines()
	{
		MapManager.getSingleton().changeToNewFile(PlayersForTest.MERLIN.getMapName());
		;
		ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.MERLIN);
		PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());

	}

	/**
	 * reset data
	 */
	@Override
	public void resetDataGateways()
	{
		PlayerManager.resetSingleton();
		InteractObjectManager.resetSingleton();
	}

}
