package communication.packers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import datasource.ServerSideTest;
import org.junit.Before;
import org.junit.Test;

import communication.StateAccumulator;
import communication.messages.ExperienceChangedMessage;
import dataDTO.LevelManagerDTO;
import datasource.LevelRecord;
import model.PlayerManager;
import model.QuestManager;
import model.reports.ExperienceChangedReport;
import datatypes.PlayersForTest;

/**
 * @author Ryan
 *
 *         Make sure that the ExperienceChangedMessagePacker behaves properly.
 */
public class ExperienceChangedMessagePackerTest extends ServerSideTest
{
	private StateAccumulator stateAccumulator;

	/**
	 * reset the necessary singletons
	 */
	@Before
	public void localSetUp()
	{
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
		ExperienceChangedMessagePacker packer = new ExperienceChangedMessagePacker();

		assertEquals(ExperienceChangedReport.class, packer.getReportTypesWePack().get(0));
	}

	/**
	 * the message should contain the appropriate player's ID, experience points
	 * and level object
	 */
	@Test
	public void testPackedObjectIsCurrentPlayer()
	{
		LevelRecord record = LevelManagerDTO.getSingleton().getLevelForPoints(PlayersForTest.MERLIN.getExperiencePoints());
		ExperienceChangedReport report = new ExperienceChangedReport(PlayersForTest.MERLIN.getPlayerID(),
				PlayersForTest.MERLIN.getExperiencePoints(), record);
		ExperienceChangedMessagePacker packer = new ExperienceChangedMessagePacker();
		packer.setAccumulator(stateAccumulator);

		ExperienceChangedMessage msg = (ExperienceChangedMessage) packer.pack(report);

		assertEquals(PlayersForTest.MERLIN.getExperiencePoints(), msg.getExperiencePoints());
		assertEquals(record, msg.getLevel());
	}

	/**
	 * If the report is not about the player we are communicating with, we
	 * should ignore it
	 */
	@Test
	public void ifItIsntAboutUsDontPack()
	{

		ExperienceChangedMessagePacker packer = new ExperienceChangedMessagePacker();
		packer.setAccumulator(stateAccumulator);

		ExperienceChangedReport report = new ExperienceChangedReport(PlayersForTest.JOHN.getPlayerID(),
				PlayersForTest.JOHN.getExperiencePoints(),
				LevelManagerDTO.getSingleton().getLevelForPoints(PlayersForTest.JOHN.getExperiencePoints()));
		assertNull(packer.pack(report));
	}

}
