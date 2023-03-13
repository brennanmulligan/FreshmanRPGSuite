package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.ItemPurchasedMessage;
import edu.ship.engr.shipsim.model.*;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetModelFacade;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Josh Wood
 */
@GameTest("GameServer")
@ResetModelFacade(waitUntilEmpty = true)
@ResetPlayerManager
public class ItemPurchasedHandlerTest
{
    /**
     * Testing getTypeWeHandled method returns the correct type
     * .
     */
    @Test
    public void testTestTypeWeHandle()
    {

        ItemPurchasedHandler j = new ItemPurchasedHandler();
        assertEquals(ItemPurchasedMessage.class, j.getMessageTypeWeHandle());

    }

    /**
     * checking and seeing if points have been spent by a player or not
     *
     * @throws ModelFacadeException Shouldn't
     */
    @Test
    public void doubloonsSpent() throws ModelFacadeException
    {
        int playerID = 7;
        int price = 2;
        int startingScore = 100;

        PlayerManager.getSingleton().addPlayer(7);
        Player p = PlayerManager.getSingleton().getPlayerFromID(7);
        p.setDoubloons(startingScore);

        assertEquals(startingScore, p.getDoubloons());

        ItemPurchasedMessage msg = new ItemPurchasedMessage(playerID, false, price);
        ItemPurchasedHandler handler = new ItemPurchasedHandler();

        handler.process(msg);

        ModelFacadeTestHelper.waitForFacadeToProcess();

        assertEquals(startingScore - price, p.getQuizScore());
    }
}