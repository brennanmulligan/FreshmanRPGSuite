package sequencetests;

import java.util.ArrayList;

import communication.messages.ReceiveTerminalTextMessage;
import communication.messages.SendTerminalTextMessage;
import dataDTO.PlayerMapLocationDTO;
import datasource.DatabaseException;
import model.ClientModelTestUtilities;
import model.Command;
import model.CommandSendTerminalText;
import model.InteractObjectManager;
import model.MapManager;
import model.MessageFlow;
import model.OptionsManager;
import model.PlayerManager;
import model.SequenceTest;
import model.ServerType;
import model.terminal.CommandTerminalTextWho;
import datatypes.PlayersForTest;

/**
 * A player presses 'o' and the list of online players is displayed in the console(for now)
 *
 * @author Nathaniel Manning, Austin Smale
 */
public class TerminalTextSequenceTest extends SequenceTest
{

	ArrayList<PlayerMapLocationDTO> mapList;

	/**
	 * runs through the message flow
	 */
	public TerminalTextSequenceTest()
	{
		// This needs to create the string
		String expected = "";
		for (PlayersForTest p : PlayersForTest.values())
		{
			if (p.getOnline())
			{
				expected = expected + p.getPlayerName() + ':' + p.getMapName() + ":";
			}
		}

		CommandTerminalTextWho cttw = new CommandTerminalTextWho();
		expected = cttw.formatString(expected);

		MessageFlow[] sequence = new MessageFlow[]{new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
				new SendTerminalTextMessage(PlayersForTest.JEFF.getPlayerID(), "who"), true),

				new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
						new ReceiveTerminalTextMessage(PlayersForTest.JEFF.getPlayerID(),
								expected), true)};

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
		return new CommandSendTerminalText("who");
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
		return PlayersForTest.JEFF.getPlayerID();
	}


	/**
	 * any setup required
	 * @throws DatabaseException  shouldn't
	 */
	@Override
	public void setUpMachines() throws DatabaseException
	{


		MapManager.getSingleton().changeToNewFile(PlayersForTest.JEFF.getMapName());
		ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.JEFF);
		PlayerManager.getSingleton().addPlayer(PlayersForTest.JEFF.getPlayerID());

		OptionsManager.getSingleton().setMapName("sortingRoom.tmx");
		PlayerManager.getSingleton().loadNpcs(false);
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
