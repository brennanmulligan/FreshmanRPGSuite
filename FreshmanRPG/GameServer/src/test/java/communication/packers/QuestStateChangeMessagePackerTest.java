package communication.packers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import communication.StateAccumulator;
import communication.messages.QuestStateChangeMessage;
import datasource.DatabaseException;
import datatypes.QuestStateEnum;
import model.OptionsManager;
import model.QuestManager;
import model.reports.QuestStateChangeReport;
import datatypes.QuestsForTest;

/**
 *
 * @author Andrew
 * @author Steve
 * @author Matt
 * @author Olivia
 * @author LaVonne
 */
public class QuestStateChangeMessagePackerTest
{
	/**
	 * reset the necessary singletons
	 */
	@Before
	public void setUp()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		QuestManager.resetSingleton();

	}

	/**
	 * Test that we pack a PlayerMovedReport
	 */
	@Test
	public void testReportTypeWePack()
	{
		QuestStateChangeMessagePacker packer = new QuestStateChangeMessagePacker();
		assertEquals(QuestStateChangeReport.class, packer.getReportTypesWePack().get(0));
		assertEquals(1, packer.getReportTypesWePack().size());
	}

	/**
	 * If we are notified about a player other than the one we are associated
	 * with, we shouldn't pack a message
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void ifThePlayerIsNotOnThisConnection() throws DatabaseException
	{
		StateAccumulator stateAccumulator = new StateAccumulator(null);
		stateAccumulator.setPlayerId(1);

		QuestStateChangeReport report = new QuestStateChangeReport(2, QuestsForTest.ONE_BIG_QUEST.getQuestID(),
				QuestsForTest.ONE_BIG_QUEST.getQuestTitle(), QuestsForTest.ONE_BIG_QUEST.getQuestDescription(),
				QuestStateEnum.AVAILABLE);
		QuestStateChangeMessagePacker packer = new QuestStateChangeMessagePacker();
		packer.setAccumulator(stateAccumulator);

		QuestStateChangeMessage msg = (QuestStateChangeMessage) packer.pack(report);
		assertNull(msg);
	}

	/**
	 * If the msg is to the player we are packing, the message should contain
	 * the details of the quest
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testPackedMessageIsBuiltCorrectly() throws DatabaseException
	{
		StateAccumulator stateAccumulator = new StateAccumulator(null);
		stateAccumulator.setPlayerId(1);

		QuestStateChangeReport report = new QuestStateChangeReport(stateAccumulator.getPlayerID(),
				QuestsForTest.ONE_BIG_QUEST.getQuestID(), QuestsForTest.ONE_BIG_QUEST.getQuestTitle(),
				QuestsForTest.ONE_BIG_QUEST.getQuestDescription(), QuestStateEnum.TRIGGERED);
		QuestStateChangeMessagePacker packer = new QuestStateChangeMessagePacker();
		packer.setAccumulator(stateAccumulator);

		QuestStateChangeMessage msg = (QuestStateChangeMessage) packer.pack(report);
		assertEquals(QuestsForTest.ONE_BIG_QUEST.getQuestID(), msg.getQuestID());
		assertEquals(QuestsForTest.ONE_BIG_QUEST.getQuestDescription(), msg.getQuestDescription());
		assertEquals(QuestStateEnum.TRIGGERED, msg.getNewState());

	}
}
