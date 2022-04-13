package sequencetests;

import communication.messages.BuffMessage;
import communication.messages.KeyInputMessage;
import model.ClientModelTestUtilities;
import model.Command;
import model.CommandKeyInputSent;
import model.InteractObjectManager;
import model.MapManager;
import model.MessageFlow;
import model.Player;
import model.PlayerManager;
import model.SequenceTest;
import model.ServerType;
import datatypes.InteractableItemsForTest;
import datatypes.PlayersForTest;

/**
 * Tests that when a Client presses e the server adds exp points to the player
 *
 * @author ed9737, sk5587, tc9538
 *
 */
public class TriggerBuffMessageSequenceTest extends SequenceTest
{

	/**
	 * the sequence of messages to occur
	 */
	private MessageFlow[] sequence =
			{
					new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER, new KeyInputMessage("e"), true),
					new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
							new BuffMessage(PlayersForTest.JAWN.getPlayerID(), InteractableItemsForTest.BOOK.getExperiencePointPool()), true)

			};

	/**
	 * Runs through the message flow
	 */
	public TriggerBuffMessageSequenceTest()
	{
		for (MessageFlow mf : sequence)
		{
			messageSequence.add(mf);
		}

		serverList.add(ServerType.THIS_PLAYER_CLIENT);
		serverList.add(ServerType.AREA_SERVER);
	}

	/**
	 * The command that starts the sequence
	 */
	@Override
	public Command getInitiatingCommand()
	{
		return new CommandKeyInputSent("e");
	}

	/**
	 * the Server the command starts on
	 */
	@Override
	public ServerType getInitiatingServerType()
	{
		return ServerType.THIS_PLAYER_CLIENT;
	}

	/**
	 * the starting player
	 */
	@Override
	public int getInitiatingPlayerID()
	{
		return PlayersForTest.JAWN.getPlayerID();
	}

	/**
	 * generic setup
	 */
	@Override
	public void setUpMachines()
	{
		MapManager.getSingleton().changeToNewFile(PlayersForTest.JAWN.getMapName());
		ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.JAWN);

		// set up players through player manager
		PlayerManager pm = PlayerManager.getSingleton();
		pm.addPlayer(PlayersForTest.JAWN.getPlayerID());

		// set player position
		Player playerFromID = pm.getPlayerFromID(PlayersForTest.JAWN.getPlayerID());
		playerFromID.setPlayerPosition(InteractableItemsForTest.BOOK.getPosition());

		InteractObjectManager.getSingleton();
	}

	/**
	 * Clear used data
	 */
	@Override
	public void resetDataGateways()
	{
		PlayerManager.resetSingleton();
		InteractObjectManager.resetSingleton();
	}

}
