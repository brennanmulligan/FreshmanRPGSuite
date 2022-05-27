package communication.packers;

import static org.junit.Assert.assertEquals;

import datasource.ServerSideTest;
import org.junit.Before;
import org.junit.Test;

import communication.StateAccumulator;
import communication.messages.TeleportationContinuationMessage;
import criteria.GameLocationDTO;
import model.ModelFacade;
import model.PlayerManager;
import model.reports.TeleportOnQuestCompletionReport;
import datatypes.ObjectivesForTest;

/**
 * makes sure TeleportOnQuestCompletionPacker works well
 *
 * @author Abdul, Chris Hersh, Zach Thompson
 *
 */
public class TeleportOnQuestCompletionPackerTest extends ServerSideTest
{

	/**
	 * reset the necessary singletons
	 */
	@Before
	public void localSetUp()
	{
		PlayerManager.resetSingleton();
		ModelFacade.resetSingleton();
	}

	/**
	 * Test that we pack a PlayerMovedReport
	 */
	@Test
	public void testReportTypeWePack()
	{
		TeleportOnQuestCompletionPacker packer = new TeleportOnQuestCompletionPacker();
		assertEquals(TeleportOnQuestCompletionReport.class, packer.getReportTypesWePack().get(0));
		assertEquals(1, packer.getReportTypesWePack().size());
	}

	/**
	 * If the msg is to the player we are packing, the message should contain
	 * the details of the quest
	 */
	@Test
	public void testPackedMessageIsBuiltCorrectly()
	{
		StateAccumulator stateAccumulator = new StateAccumulator(null);
		stateAccumulator.setPlayerId(1);

		String host = "hostname";
		int port = 22;
		GameLocationDTO gl = new GameLocationDTO("quad.tmx", null);

		TeleportOnQuestCompletionReport report = new TeleportOnQuestCompletionReport(1,
				ObjectivesForTest.QUEST1_OBJECTIVE_1.getQuestID(), gl, host, port);

		TeleportOnQuestCompletionPacker packer = new TeleportOnQuestCompletionPacker();
		packer.setAccumulator(stateAccumulator);

		TeleportationContinuationMessage msg = (TeleportationContinuationMessage) packer.pack(report);
		assertEquals(gl.getMapName(), msg.getMapName());
		assertEquals(host, msg.getHostName());
		assertEquals(port, msg.getPortNumber());
		assertEquals(1, msg.getPlayerID());

	}

}
