package sequencetests;

import communication.messages.ReceiveTerminalTextMessage;
import communication.messages.SendTerminalTextMessage;
import datasource.DatabaseException;
import datatypes.PlayersForTest;
import model.*;
import model.terminal.CommandTerminalTextWho;

/**
 * A player presses 'o' and the list of online players is displayed in the console(for now)
 *
 * @author Nathaniel Manning, Austin Smale
 */
public class TerminalTextSequenceTest extends SequenceTest
{

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

        MessageFlow[] sequence = new MessageFlow[]{
                new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
                        new SendTerminalTextMessage(PlayersForTest.JEFF.getPlayerID(),
                                "who"), true),

                new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                        new ReceiveTerminalTextMessage(PlayersForTest.JEFF.getPlayerID(),
                                expected), true)};

        serverList.add(ServerType.THIS_PLAYER_CLIENT);
        serverList.add(ServerType.AREA_SERVER);

        interaction = new Interaction(sequence,
                new CommandSendTerminalText("who"),
                PlayersForTest.JEFF.getPlayerID(),
                ServerType.THIS_PLAYER_CLIENT);
    }

    /**
     * reset data
     */
    @Override
    public void resetNecessarySingletons()
    {
        PlayerManager.resetSingleton();
        InteractObjectManager.resetSingleton();
    }

    /**
     * any setup required
     *
     * @throws DatabaseException shouldn't
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

}
