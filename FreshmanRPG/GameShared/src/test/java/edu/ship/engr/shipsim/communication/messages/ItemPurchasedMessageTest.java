package edu.ship.engr.shipsim.communication.messages;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Tests an ItemPurchasedMessage
 *
 * @author Kevin Marek, Zachary Semanco
 */
public class ItemPurchasedMessageTest
{
    /**
     * Tests the constructor
     */
    @Test
    public void testInitialization()
    {
        ItemPurchasedMessage msg = new ItemPurchasedMessage(1, 10);
        assertEquals(1, msg.getPlayerID());
        assertEquals(10, msg.getPrice());

    }

    /**
     * Tests the equals method
     */
    @Test
    public void testEquality()
    {
        EqualsVerifier.forClass(ItemPurchasedMessage.class).verify();
    }
}
