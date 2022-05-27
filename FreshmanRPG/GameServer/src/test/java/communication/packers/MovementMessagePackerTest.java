package communication.packers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import datasource.ServerSideTest;
import org.junit.Before;
import org.junit.Test;

import communication.StateAccumulator;
import communication.messages.OtherPlayerMovedMessage;
import communication.messages.PlayerMovedMessage;
import datatypes.Position;
import model.PlayerManager;
import model.reports.PlayerMovedReport;

/**
 *
 * @author Andrew
 * @author Steve
 * @author Matt
 *
 */
public class MovementMessagePackerTest extends ServerSideTest
{
	private StateAccumulator stateAccumulator;

	/**
	 * reset the necessary singletons
	 */
	@Before
	public void localSetUp()
	{
		PlayerManager.resetSingleton();
		PlayerManager.getSingleton().addPlayer(1);
		stateAccumulator = new StateAccumulator(null);
		stateAccumulator.setPlayerId(1);
	}

	/**
	 * Test that we pack a PlayerMovedReport
	 */
	@Test
	public void testReportTypeWePack()
	{
		OtherPlayerMovedMessagePacker packer = new OtherPlayerMovedMessagePacker();
		assertEquals(PlayerMovedReport.class, packer.getReportTypesWePack().get(0));
	}

	/**
	 * Given a PlayerMovedReport for the current player, the message should be
	 * null
	 */
	@Test
	public void testPackedObjectIsCurrentPlayer()
	{
		Position position = new Position(1, 2);
		PlayerMovedReport report = new PlayerMovedReport(stateAccumulator.getPlayerID(), "fred", position, "mapName");
		OtherPlayerMovedMessagePacker packer = new OtherPlayerMovedMessagePacker();
		packer.setAccumulator(stateAccumulator);

		PlayerMovedMessage message = (PlayerMovedMessage) packer.pack(report);
		assertNull(message);
	}

	/**
	 * Given a PlayerMovedReport for a player other than the client, ensure that
	 * the MovementMessage is what we expect
	 */
	@Test
	public void testPackedObjectNotCurrentPlayer()
	{
		Position position = new Position(1, 2);
		PlayerMovedReport report = new PlayerMovedReport(-1, "fred", position, "mapName");
		OtherPlayerMovedMessagePacker packer = new OtherPlayerMovedMessagePacker();
		packer.setAccumulator(stateAccumulator);

		OtherPlayerMovedMessage message = (OtherPlayerMovedMessage) packer.pack(report);
		assertEquals(-1, message.getPlayerID());
		assertEquals(position, message.getPosition());
	}
}
