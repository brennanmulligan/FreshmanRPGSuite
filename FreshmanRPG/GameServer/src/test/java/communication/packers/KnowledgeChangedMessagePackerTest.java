package communication.packers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import communication.StateAccumulator;
import communication.messages.KnowledgeChangedMessage;
import datasource.DatabaseException;
import model.OptionsManager;
import model.PlayerManager;
import model.QuestManager;
import model.reports.KnowledgePointsChangeReport;
import datatypes.PlayersForTest;

/**
 * @author Matthew Croft
 *
 */
public class KnowledgeChangedMessagePackerTest
{
	private StateAccumulator stateAccumulator;

	/**
	 * reset the necessary singletons
	 */
	@Before
	public void setUp()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		QuestManager.resetSingleton();

		PlayerManager playerManager = PlayerManager.getSingleton();
		playerManager.addPlayer(PlayersForTest.JOHN.getPlayerID());
		playerManager.addPlayer(PlayersForTest.MERLIN.getPlayerID());
		stateAccumulator = new StateAccumulator(null);
		stateAccumulator.setPlayerId(PlayersForTest.MERLIN.getPlayerID());
	}

	/**
	 *
	 */
	@Test
	public void testReportWePack()
	{
		KnowledgeChangePacker packer = new KnowledgeChangePacker();

		assertEquals(KnowledgePointsChangeReport.class, packer.getReportTypesWePack().get(0));
	}

	/**
	 * the message should contain the appropriate player's ID, experience points
	 * and level object
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testPackedObjectIsCurrentPlayer() throws DatabaseException
	{
		KnowledgePointsChangeReport report = new KnowledgePointsChangeReport(PlayersForTest.MERLIN.getPlayerID(),
				PlayersForTest.MERLIN.getKnowledgeScore(), PlayersForTest.MERLIN.getBuffPool());
		KnowledgeChangePacker packer = new KnowledgeChangePacker();
		packer.setAccumulator(stateAccumulator);

		KnowledgeChangedMessage msg = (KnowledgeChangedMessage) packer.pack(report);

		assertEquals(PlayersForTest.MERLIN.getKnowledgeScore(), msg.getKnowledgePoints());
	}

	/**
	 * If the report is not about the player we are communicating with, we
	 * should ignore it
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void ifItIsntAboutUsDontPack() throws DatabaseException
	{

		KnowledgeChangePacker packer = new KnowledgeChangePacker();
		packer.setAccumulator(stateAccumulator);

		KnowledgePointsChangeReport report = new KnowledgePointsChangeReport(PlayersForTest.JOHN.getPlayerID(),
				PlayersForTest.JOHN.getKnowledgeScore(), PlayersForTest.MERLIN.getBuffPool());
		assertNull(packer.pack(report));
	}
}
