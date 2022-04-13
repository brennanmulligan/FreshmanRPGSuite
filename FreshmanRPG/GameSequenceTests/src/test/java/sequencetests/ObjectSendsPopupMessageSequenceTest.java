package sequencetests;

import communication.messages.DisplayTextMessage;
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
 * Tests that when a player is near an object of the type that
 * displays text, and the player presses 'e', the server sends back
 * a message to display appropriate text.
 * @author Elisabeth Ostrow, Jacob Knight
 *
 */
public class ObjectSendsPopupMessageSequenceTest extends SequenceTest
{

	/**
	 * the sequence of messages
	 */
	private MessageFlow[] sequence =
			{
					new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER, new KeyInputMessage("e"), true),
					new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
							new DisplayTextMessage(PlayersForTest.JAWN.getPlayerID(), InteractableItemsForTest.BOOKSHELF.getMessage()), true)
			};

	/**
	 * runs through message flow
	 */
	public ObjectSendsPopupMessageSequenceTest()
	{
		for (MessageFlow mf : sequence)
		{
			messageSequence.add(mf);
		}

		serverList.add(ServerType.THIS_PLAYER_CLIENT);
		serverList.add(ServerType.AREA_SERVER);
	}

	/**
	 * the command that begins the message flow
	 */
	@Override
	public Command getInitiatingCommand()
	{
		return new CommandKeyInputSent("e");
	}

	/**
	 * the server that the initiating command is 
	 * executed on
	 */
	@Override
	public ServerType getInitiatingServerType()
	{
		return ServerType.THIS_PLAYER_CLIENT;
	}

	/**
	 * the player we are using for the test
	 */
	@Override
	public int getInitiatingPlayerID()
	{
		return PlayersForTest.JAWN.getPlayerID();
	}

	/**
	 * various setup
	 * puts player next to a valid interactable object
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
		playerFromID.setPlayerPosition(InteractableItemsForTest.BOOKSHELF.getPosition());
		InteractObjectManager.getSingleton();

	}

	/**
	 * resets singletons
	 */
	@Override
	public void resetDataGateways()
	{
		PlayerManager.resetSingleton();
		InteractObjectManager.resetSingleton();
	}

}
