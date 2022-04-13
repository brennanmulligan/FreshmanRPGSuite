package sequencetests;

import communication.messages.NoMoreBuffMessage;
import model.ClientModelTestUtilities;
import model.Command;
import model.InteractObjectManager;
import model.MapManager;
import model.MessageFlow;
import model.PlayerManager;
import model.SequenceTest;
import model.ServerType;
import datatypes.PlayersForTest;

/**
 * Test that NoMoreBuffMessage is sent to the player when they lose all of their bonus points.
 */
public class NoMoreBuffSequenceTest extends SequenceTest
{
	private MessageFlow[] sequence =
			{
					new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
							new NoMoreBuffMessage(PlayersForTest.JAWN.getPlayerID()), true)
			};

	/**
	 * Runs through the message flow
	 */
	public NoMoreBuffSequenceTest()
	{
		for (MessageFlow mf : sequence)
		{
			messageSequence.add(mf);
		}
	}

	/** (non-Javadoc)
	 * @see model.SequenceTest#getInitiatingCommand()
	 */
	@Override
	public Command getInitiatingCommand()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/** (non-Javadoc)
	 * @see model.SequenceTest#getInitiatingServerType()
	 */
	@Override
	public ServerType getInitiatingServerType()
	{
		return ServerType.AREA_SERVER;
	}

	/** (non-Javadoc)
	 * @see model.SequenceTest#getInitiatingPlayerID()
	 */
	@Override
	public int getInitiatingPlayerID()
	{
		return PlayersForTest.JAWN.getPlayerID();
	}

	/** (non-Javadoc)
	 * @see model.SequenceTest#setUpMachines()
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
		pm.getPlayerFromID(PlayersForTest.JAWN.getPlayerID());
		InteractObjectManager.getSingleton();
	}

	/** (non-Javadoc)
	 * @see model.SequenceTest#resetDataGateways()
	 */
	@Override
	public void resetDataGateways()
	{
		PlayerManager.resetSingleton();
		InteractObjectManager.resetSingleton();
	}
}
