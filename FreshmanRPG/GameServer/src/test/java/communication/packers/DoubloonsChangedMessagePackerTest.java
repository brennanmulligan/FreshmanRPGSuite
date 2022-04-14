package communication.packers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import communication.StateAccumulator;
import communication.messages.DoubloonsChangedMessage;
import datasource.DatabaseException;
import model.OptionsManager;
import model.PlayerManager;
import model.QuestManager;
import model.reports.DoubloonChangeReport;
import datatypes.PlayersForTest;

/**
 * @author Matthew Croft
 *
 */
public class DoubloonsChangedMessagePackerTest
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
		DoubloonChangePacker packer = new DoubloonChangePacker();

		assertEquals(DoubloonChangeReport.class, packer.getReportTypesWePack().get(0));
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
		DoubloonChangeReport report = new DoubloonChangeReport(PlayersForTest.MERLIN.getPlayerID(),
				PlayersForTest.MERLIN.getDoubloons(), PlayersForTest.MERLIN.getBuffPool());
		DoubloonChangePacker packer = new DoubloonChangePacker();
		packer.setAccumulator(stateAccumulator);

		DoubloonsChangedMessage msg = (DoubloonsChangedMessage) packer.pack(report);

		assertEquals(PlayersForTest.MERLIN.getDoubloons(), msg.getDoubloons());
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

		DoubloonChangePacker packer = new DoubloonChangePacker();
		packer.setAccumulator(stateAccumulator);

		DoubloonChangeReport report = new DoubloonChangeReport(PlayersForTest.JOHN.getPlayerID(),
				PlayersForTest.JOHN.getDoubloons(), PlayersForTest.MERLIN.getBuffPool());
		assertNull(packer.pack(report));
	}
}
