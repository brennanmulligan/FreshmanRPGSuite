package sequencetests;

import communication.messages.BuffMessage;
import communication.messages.ChatMessage;
import datatypes.ChatType;
import datatypes.ChatTextDetails;
import model.ClientModelTestUtilities;
import model.ClientPlayerManager;
import model.Command;
import model.CommandChatMessageSent;
import model.InteractObjectManager;
import model.MapManager;
import model.MessageFlow;
import model.PlayerManager;
import model.SequenceTest;
import model.ServerType;
import model.cheatCodeBehaviors.BuffBehavior;
import datatypes.PlayersForTest;

/**
 * Tests that when a Client presses e the server adds exp points to the player
 *
 * @author ed9737, sk5587, tc9538
 *
 */
public class CheatCodeForBuffSequenceTest extends SequenceTest
{

	/**
	 * the sequence of messages to occur
	 */
	private MessageFlow[] sequence =
			{
					new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
							new ChatMessage(PlayersForTest.JAWN.getPlayerID(), 0, "Magic Buff", PlayersForTest.JAWN.getPosition(),
									ChatType.Local),
							true),
					new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
							new BuffMessage(PlayersForTest.JAWN.getPlayerID(), BuffBehavior.BUFF_VALUE), true)

			};

	/**
	 * Runs through the message flow
	 */
	public CheatCodeForBuffSequenceTest()
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
		return new CommandChatMessageSent(new ChatTextDetails("Magic Buff", ChatType.Local));
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
		PlayersForTest jawn = PlayersForTest.JAWN;
		ClientPlayerManager.resetSingleton();
		MapManager.getSingleton().changeToNewFile(jawn.getMapName());
		ClientModelTestUtilities.setUpThisClientsPlayerForTest(jawn);
		ClientPlayerManager.getSingleton().getPlayerFromID(jawn.getPlayerID()).setPosition(jawn.getPosition());

		// set up players through player manager
		PlayerManager pm = PlayerManager.getSingleton();
		pm.addPlayer(jawn.getPlayerID());
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
