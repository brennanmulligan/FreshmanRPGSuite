package communication.packers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import communication.StateAccumulator;
import communication.messages.PlayerAppearanceChangeMessage;
import model.OptionsManager;
import model.Player;
import model.PlayerManager;
import model.QuestManager;
import model.reports.PlayerAppearanceChangeReport;
import datatypes.PlayersForTest;

/**
 * @author sb6844
 *
 */
public class PlayerAppearanceChangePackerTest
{
	private StateAccumulator stateAccumulator;

	/**
	 * reset the necessary singletons and sets
	 * the stateAccumulator
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
	 *Ensures that we are getting the proper report to pack
	 */
	@Test
	public void testReportTypeWePack()
	{
		PlayerAppearanceChangeMessagePacker packer = new PlayerAppearanceChangeMessagePacker();
		assertEquals(PlayerAppearanceChangeReport.class, packer.getReportTypesWePack().get(0));
		assertEquals(1, packer.getReportTypesWePack().size());
	}


	/**
	 * The message should contain the proper player ID and appearance type
	 */
	@Test
	public void testPackedObjectIsCurrentPlayer()
	{
		Player playerMerlin = PlayerManager.getSingleton().getPlayerFromID(PlayersForTest.MERLIN.getPlayerID());
		PlayerAppearanceChangeReport report = new PlayerAppearanceChangeReport(PlayersForTest.MERLIN.getPlayerID(),
				PlayersForTest.MERLIN.getAppearanceType());
		PlayerAppearanceChangeMessagePacker packer = new PlayerAppearanceChangeMessagePacker();
		packer.setAccumulator(stateAccumulator);

		PlayerAppearanceChangeMessage msg = (PlayerAppearanceChangeMessage) packer.pack(report);

		assertEquals(PlayersForTest.MERLIN.getAppearanceType(), msg.getAppearanceType());
		assertEquals(playerMerlin.getAppearanceType(), msg.getAppearanceType());
	}


	/**
	 * If the report is not about the player we are communicating with, the message should be ignored.
	 */
	@Test
	public void ifItIsntAboutUsDontPack()
	{
		PlayerAppearanceChangeMessagePacker packer = new PlayerAppearanceChangeMessagePacker();
		packer.setAccumulator(stateAccumulator);
		Player playerMerlin = PlayerManager.getSingleton().getPlayerFromID(PlayersForTest.MERLIN.getPlayerID());
		PlayerAppearanceChangeReport report = new PlayerAppearanceChangeReport(PlayersForTest.JOHN.getPlayerID(),
				playerMerlin.getAppearanceType());
		assertNull(packer.pack(report));
	}


}
