package edu.ship.engr.shipsim.sequencetests;

import edu.ship.engr.shipsim.communication.messages.ChatMessageToClient;
import edu.ship.engr.shipsim.communication.messages.ChatMessageToServer;
import edu.ship.engr.shipsim.communication.messages.DoubloonsChangedMessage;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.datatypes.NPCQuestionsForTest;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.*;

import java.util.ArrayList;

/**
 * If a player has buff points, they should get a bonus in their doubloons.
 *
 * @author Adam Pine, Truc Chau, Andy Kim
 */
public class RecCenterGrantsDoubloonsWithBuffSequenceTest extends SequenceTest
{

    /**
     * the flow of messages to occur
     */
    @SuppressWarnings("FieldCanBeLocal")
    private final MessageFlow[] sequence = new MessageFlow[]{
            new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
                    new ChatMessageToServer(PlayersForTest.JEFF.getPlayerID(), 0,
                            NPCQuestionsForTest.ONE.getA(),
                            PlayersForTest.JEFF.getPosition(), ChatType.Zone), false),
            new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                    new ChatMessageToClient(PlayersForTest.JEFF.getPlayerID(), 0,
                            "First answer", PlayersForTest.JEFF.getPosition(),
                            ChatType.Zone), true),
            new MessageFlow(ServerType.AREA_SERVER, ServerType.OTHER_CLIENT,
                    new ChatMessageToClient(PlayersForTest.JEFF.getPlayerID(), 0,
                            "First answer", PlayersForTest.JEFF.getPosition(),
                            ChatType.Zone), true),
            new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                    new ChatMessageToClient(PlayersForTest.QUIZBOT.getPlayerID(), 0,
                            "Jeff answered correctly.  The answer was First answer",
                            PlayersForTest.QUIZBOT.getPosition(), ChatType.Local), true),
            new MessageFlow(ServerType.AREA_SERVER, ServerType.OTHER_CLIENT,
                    new ChatMessageToClient(PlayersForTest.QUIZBOT.getPlayerID(), 0,
                            "Jeff answered correctly.  The answer was First answer",
                            PlayersForTest.QUIZBOT.getPosition(), ChatType.Local), true),

            new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                    new DoubloonsChangedMessage(PlayersForTest.JEFF.getPlayerID(),
                            PlayersForTest.JEFF.getDoubloons() + 2,
                            PlayersForTest.JEFF.getBuffPool() - 1), true),
            new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                    new ChatMessageToClient(PlayersForTest.QUIZBOT.getPlayerID(), 0,
                            "Jeff score is now 2", PlayersForTest.QUIZBOT.getPosition(),
                            ChatType.Local), true),
            new MessageFlow(ServerType.AREA_SERVER, ServerType.OTHER_CLIENT,
                    new ChatMessageToClient(PlayersForTest.QUIZBOT.getPlayerID(), 0,
                            "Jeff score is now 2", PlayersForTest.QUIZBOT.getPosition(),
                            ChatType.Local), true),

    };

    /**
     * runs through the message flow
     */
    public RecCenterGrantsDoubloonsWithBuffSequenceTest()
    {
        serverList.add(ServerType.THIS_PLAYER_CLIENT);
        serverList.add(ServerType.AREA_SERVER);

        interactions.add(new Interaction(null, PlayersForTest.JEFF.getPlayerID(),
                ServerType.AREA_SERVER, sequence));
    }

    /**
     * reset data
     */
    @Override
    public void resetNecessarySingletons()
    {
        PlayerManager.resetSingleton();

    }

    /**
     * any setup required
     *
     * @throws DatabaseException shouldn't
     */
    @Override
    public void setUpMachines() throws DatabaseException
    {
        MapManager.getSingleton().changeToNewFile(PlayersForTest.JEFF.getMapName(),
                "JEFFSTUFF");
        ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.JEFF);
        PlayerManager.getSingleton().addPlayer(PlayersForTest.JEFF.getPlayerID());
        OptionsManager.getSingleton().setMapFileTitle("recCenter.tmx");
        PlayerManager.getSingleton().loadNpcs(true);
        ArrayList<NPC> npcs = PlayerManager.getSingleton().getNpcs();
        for (NPC npc : npcs)
        {
            if (npc.getBehavior().getClass().equals(QuizBotBehavior.class))
            {
                QuizBotBehavior behavior = (QuizBotBehavior) npc.getBehavior();
                behavior.setExpectedQuestion(NPCQuestion.getSpecificQuestion(
                        NPCQuestionsForTest.ONE.getQuestionID()));

            }
        }

    }
}
