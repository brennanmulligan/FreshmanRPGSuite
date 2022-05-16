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
        StringBuilder expected = new StringBuilder();
        for (PlayersForTest p : PlayersForTest.values())
        {
            if (p.getOnline())
            {
                expected.append(p.getPlayerName()).append(':').append(p.getMapName())
                        .append(":");
            }
        }

        CommandTerminalTextWho cttw = new CommandTerminalTextWho();
        expected = new StringBuilder(cttw.formatString(expected.toString()));

        MessageFlow[] sequence = new MessageFlow[]{
                new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
                        new SendTerminalTextMessage(PlayersForTest.JEFF.getPlayerID(),
                                "who"), true),

                new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                        new ReceiveTerminalTextMessage(PlayersForTest.JEFF.getPlayerID(),
                                expected.toString()), true)};

        serverList.add(ServerType.THIS_PLAYER_CLIENT);
        serverList.add(ServerType.AREA_SERVER);

        interactions.add(new Interaction(new CommandSendTerminalText("who"),
                PlayersForTest.JEFF.getPlayerID(), ServerType.THIS_PLAYER_CLIENT,
                sequence));
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
