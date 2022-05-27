package communication.packers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import datasource.ServerSideTest;
import org.junit.Test;

import communication.StateAccumulator;
import communication.messages.BuffMessage;
import model.reports.InteractableObjectBuffReport;

/**
 * Tests the packer for Buff messages
 * @author Elisabeth Ostrow, Stephen Clabaugh
 *
 */
public class InteractableObjectBuffMessagePackerTest extends ServerSideTest
{

	/**
	 * Tests that we are listening for the correct reports.
	 */
	@Test
	public void testReportsWePack()
	{
		InteractableObjectBuffMessagePacker packer = new InteractableObjectBuffMessagePacker();
		assertEquals(InteractableObjectBuffReport.class, packer.getReportTypesWePack().get(0));
	}

	/**
	 * Test that the player ID is retrieved correctly
	 */
	@Test
	public void testPlayerIDIsCorrect()
	{
		int playerID = 1234;
		InteractableObjectBuffMessagePacker packer = new InteractableObjectBuffMessagePacker();
		InteractableObjectBuffReport report = new InteractableObjectBuffReport(playerID, 5);
		StateAccumulator stateAccumulator = new StateAccumulator(null);
		stateAccumulator.setPlayerId(playerID);
		packer.setAccumulator(stateAccumulator);

		BuffMessage message = (BuffMessage) packer.pack(report);

		assertEquals(message.getPlayerID(), report.getPlayerID());
	}

	/**
	 * Test that the player ID is retrieved correctly
	 */
	@Test
	public void testPointPoolIsCorrect()
	{
		int playerID = 1234;
		InteractableObjectBuffMessagePacker packer = new InteractableObjectBuffMessagePacker();
		InteractableObjectBuffReport report = new InteractableObjectBuffReport(playerID, 5);
		StateAccumulator stateAccumulator = new StateAccumulator(null);
		stateAccumulator.setPlayerId(playerID);
		packer.setAccumulator(stateAccumulator);

		BuffMessage message = (BuffMessage) packer.pack(report);

		assertEquals(message.getExperiencePointPool(), report.getExpPointPool());
	}

	/**
	 * Tests that the packer doesn't work for a player that isn't connected
	 */
	@Test
	public void testIncorrectPlayerPacker()
	{
		int playerID = 1234;
		int incorrectID = 400;
		InteractableObjectBuffMessagePacker packer = new InteractableObjectBuffMessagePacker();
		InteractableObjectBuffReport report = new InteractableObjectBuffReport(incorrectID, 5);
		StateAccumulator stateAccumulator = new StateAccumulator(null);
		stateAccumulator.setPlayerId(playerID);
		packer.setAccumulator(stateAccumulator);

		packer.pack(report);

		assertNull(packer.pack(report));
	}
}
