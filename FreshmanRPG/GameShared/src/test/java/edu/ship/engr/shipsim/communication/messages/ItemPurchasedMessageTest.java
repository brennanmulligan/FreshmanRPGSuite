package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Tests an ItemPurchasedMessage
 *
 * @author Kevin Marek, Zachary Semanco
 */
@GameTest("GameShared")
public class ItemPurchasedMessageTest
{
    /**
     * Tests the constructor
     */
    @Test
    public void testInitialization()
    {
        ItemPurchasedMessage msg = new ItemPurchasedMessage(1, false, 10);
        assertEquals(1, msg.getRelevantPlayerID());
        assertEquals(10, msg.getPrice());

    }

}
