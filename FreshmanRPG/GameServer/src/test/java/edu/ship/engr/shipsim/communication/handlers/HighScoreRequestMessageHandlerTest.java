package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.StateAccumulator;
import edu.ship.engr.shipsim.communication.messages.HighScoreRequestMessage;
import edu.ship.engr.shipsim.communication.messages.HighScoreResponseMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.ModelFacade;
import edu.ship.engr.shipsim.model.PlayerManager;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetModelFacade;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Make sure the right response gets queued back to the player
 *
 * @author Merlin
 */
@GameTest("GameServer")
@ResetModelFacade
@ResetPlayerManager
public class HighScoreRequestMessageHandlerTest
{
    /**
     * It should correctly report the type of messages it handles
     */
    @Test
    public void messageTypeCorrect()
    {
        HighScoreRequestMessageHandler handler = new HighScoreRequestMessageHandler();
        assertEquals(HighScoreRequestMessage.class, handler.getMessageTypeWeHandle());
    }

    /**
     * Make sure that the appropriate response message gets queued into the
     * accumulator
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void generatesCorrectResponse() throws DatabaseException
    {
        PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
        HighScoreRequestMessageHandler handler = new HighScoreRequestMessageHandler();
        StateAccumulator accum = new StateAccumulator(null);
        accum.setPlayerId(PlayersForTest.MERLIN.getPlayerID());
        handler.setAccumulator(accum);
        HighScoreResponseMessage msg = new HighScoreResponseMessage(PlayerManager.getSingleton().getTopTenPlayers(), false);

        handler.process(msg);

        // nothing should be queued to the model
        assertEquals(0, ModelFacade.getSingleton().queueSize());

        // make sure we queued the appropriate response
        ArrayList<Message> queue = accum.getPendingMsgs();
        assertEquals(1, queue.size());
        HighScoreResponseMessage response = (HighScoreResponseMessage) queue.get(0);
        assertEquals(10, response.getScoreReports().size());

    }

}
