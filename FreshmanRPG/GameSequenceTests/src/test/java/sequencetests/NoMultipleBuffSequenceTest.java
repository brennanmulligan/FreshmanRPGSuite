package sequencetests;

import communication.messages.InteractionDeniedMessage;
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
 * Tests that a player cannot receive another buff if it already has one
 * @author Jake Moore
 */
public class NoMultipleBuffSequenceTest extends SequenceTest
{
	private MessageFlow[] sequence =
			{
					new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER, new KeyInputMessage("e"), true),
					new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
							new InteractionDeniedMessage(PlayersForTest.JEFF.getPlayerID()), false)
			};

	/**
	 * Runs through the message flow
	 */
	public NoMultipleBuffSequenceTest()
	{
		for (MessageFlow mf : sequence)
		{
			messageSequence.add(mf);
		}
		this.serverList.add(ServerType.THIS_PLAYER_CLIENT);
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
		return PlayersForTest.JEFF.getPlayerID();
	}

	/**
	 * Sets up machines
	 */
	@Override
	public void setUpMachines()
	{
		MapManager.getSingleton().changeToNewFile(PlayersForTest.JEFF.getMapName());
		ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.JEFF);

		// set up players through player manager
		PlayerManager pm = PlayerManager.getSingleton();
		pm.addPlayer(PlayersForTest.JEFF.getPlayerID());

		// set player position
		Player playerFromID = pm.getPlayerFromID(PlayersForTest.JEFF.getPlayerID());
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
