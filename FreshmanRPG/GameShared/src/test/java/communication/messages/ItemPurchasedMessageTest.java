package communication.messages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;


/**
 * Tests an ItemPurchasedMessage
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
