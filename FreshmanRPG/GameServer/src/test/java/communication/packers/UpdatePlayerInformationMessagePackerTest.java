package communication.packers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import communication.StateAccumulator;
import communication.messages.InitializeThisClientsPlayerMessage;
import dataDTO.ClientPlayerQuestStateDTO;
import datasource.DatabaseException;
import model.IllegalQuestChangeException;
import model.OptionsManager;
import model.Player;
import model.PlayerManager;
import model.QuestManager;
import model.reports.UpdatePlayerInformationReport;
import datatypes.PlayersForTest;

/**
 *
 * @author Andrew
 * @author Steve
 * @author Matt
 * @author Olivia
 * @author LaVonne
 */
public class UpdatePlayerInformationMessagePackerTest
{
	private StateAccumulator stateAccumulator;

	/**
	 * reset the necessary singletons and set up an accumulator that is
	 * communicating with Merlin
	 */
	@Before
	public void setUp()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		PlayerManager.resetSingleton();
		QuestManager.resetSingleton();

		PlayerManager playerManager = PlayerManager.getSingleton();
		playerManager.addPlayer(PlayersForTest.JOHN.getPlayerID());
		playerManager.addPlayer(PlayersForTest.MERLIN.getPlayerID());
		stateAccumulator = new StateAccumulator(null);
		stateAccumulator.setPlayerId(PlayersForTest.MERLIN.getPlayerID());
	}

	/**
	 * Test that we pack a PlayerMovedReport
	 */
	@Test
	public void testReportTypeWePack()
	{
		UpdatePlayerInformationMessagePacker packer = new UpdatePlayerInformationMessagePacker();
		assertEquals(UpdatePlayerInformationReport.class, packer.getReportTypesWePack().get(0));
	}

	/**
	 * If the report is not about the player we are communicating with, we
	 * should ignore it
	 *
	 * @throws DatabaseException shouldn't
	 * @throws IllegalQuestChangeException the state changed illegally
	 */
	@Test
	public void ifItIsntAboutUsDontPack() throws DatabaseException, IllegalQuestChangeException
	{

		UpdatePlayerInformationMessagePacker packer = new UpdatePlayerInformationMessagePacker();
		packer.setAccumulator(stateAccumulator);

		UpdatePlayerInformationReport report = new UpdatePlayerInformationReport(
				PlayerManager.getSingleton().getPlayerFromID(PlayersForTest.JOHN.getPlayerID()));
		assertNull(packer.pack(report));
	}

	/**
	 * the message should contain the appropriate quests and objectives
	 *
	 * @throws DatabaseException shouldn't
	 * @throws IllegalQuestChangeException the state changed illegally
	 */
	@Test
	public void testPackedObjectIsCurrentPlayer() throws DatabaseException, IllegalQuestChangeException
	{
		Player player = PlayerManager.getSingleton().getPlayerFromID(stateAccumulator.getPlayerID());
		UpdatePlayerInformationReport report = new UpdatePlayerInformationReport(player);
		UpdatePlayerInformationMessagePacker packer = new UpdatePlayerInformationMessagePacker();
		packer.setAccumulator(stateAccumulator);

		InitializeThisClientsPlayerMessage message = (InitializeThisClientsPlayerMessage) packer.pack(report);
		ArrayList<ClientPlayerQuestStateDTO> expected = report.getClientPlayerQuestList();
		ArrayList<ClientPlayerQuestStateDTO> actual = message.getClientPlayerQuestList();
		assertEquals(expected.size(), actual.size());
		for (ClientPlayerQuestStateDTO a : actual)
		{
			assertTrue(expected.contains(a));
		}
	}

	/**
	 * Tests that we can add experience pts and levelrecord to
	 * InitializeThisClientsPlayerMessage message
	 *
	 * @throws DatabaseException shouldn't
	 * @throws IllegalQuestChangeException the state changed illegally
	 */
	@Test
	public void testPackExperiencePtsAndLevel() throws DatabaseException, IllegalQuestChangeException
	{
		Player player = PlayerManager.getSingleton().getPlayerFromID(stateAccumulator.getPlayerID());
		UpdatePlayerInformationReport report = new UpdatePlayerInformationReport(player);
		UpdatePlayerInformationMessagePacker packer = new UpdatePlayerInformationMessagePacker();
		packer.setAccumulator(stateAccumulator);

		InitializeThisClientsPlayerMessage message = (InitializeThisClientsPlayerMessage) packer.pack(report);
		assertEquals(report.getExperiencePts(), message.getExperiencePts());
		assertEquals(report.getLevel(), message.getLevel());
	}
}
