package sequencetests;

import communication.messages.OtherPlayerMovedMessage;
import communication.messages.PlayerMovedMessage;
import communication.messages.QuestStateChangeMessage;
import datasource.DatabaseException;
import datasource.PlayerConnectionRowDataGatewayMock;
import datatypes.PlayersForTest;
import datatypes.QuestStateEnum;
import datatypes.QuestsForTest;
import model.*;

import java.io.IOException;


/**
 * Test the sequence of messages that should flow when completing a test forces teleportation to
 * another server
 *
 * @author Chris Hersh and Zach Thompson
 */
public class FinishingQuestTeleportsSequenceTest extends SequenceTest
{

    private final MessageFlow[] sequence =
            {
                    new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
                            new PlayerMovedMessage(PlayersForTest.MATT.getPlayerID(),
                                    QuestsForTest.THE_LITTLE_QUEST.getPosition()), true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.OTHER_CLIENT,
                            new OtherPlayerMovedMessage(PlayersForTest.MATT.getPlayerID(),
                                    QuestsForTest.THE_LITTLE_QUEST.getPosition()), true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                            new QuestStateChangeMessage(PlayersForTest.MATT.getPlayerID(),
                                    QuestsForTest.THE_LITTLE_QUEST.getQuestID(),
                                    QuestsForTest.THE_LITTLE_QUEST.getQuestTitle(),
                                    QuestsForTest.THE_LITTLE_QUEST.getQuestDescription(),
                                    QuestStateEnum.TRIGGERED), true)};

    /**
     * @throws IOException shouldn't
     */
    public FinishingQuestTeleportsSequenceTest()
    {
        serverList.add(ServerType.THIS_PLAYER_CLIENT);
        serverList.add(ServerType.OTHER_CLIENT);
        interaction = new Interaction(sequence,
                new CommandClientMovePlayer(PlayersForTest.MATT.getPlayerID(),
                        QuestsForTest.THE_LITTLE_QUEST.getPosition()),
                PlayersForTest.MATT.getPlayerID(),
                ServerType.THIS_PLAYER_CLIENT);
    }

    /**
     * @see model.SequenceTest#resetNecessarySingletons()
     */
    public void resetNecessarySingletons()
    {
        PlayerManager.resetSingleton();
        QuestManager.resetSingleton();
        try
        {
            (new PlayerConnectionRowDataGatewayMock(2)).resetData();
        } catch (DatabaseException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @see model.SequenceTest#setUpMachines()
     */
    @Override
    public void setUpMachines()
    {
        MapManager.getSingleton().changeToNewFile(PlayersForTest.MATT.getMapName());
        ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.MATT);
        PlayerManager.getSingleton().addPlayer(PlayersForTest.MATT.getPlayerID());
    }

}
