package model;

import datatypes.PlayersForTest;
import org.junit.Test;

import dataDTO.ClientPlayerAdventureStateDTO;
import dataDTO.ClientPlayerQuestStateDTO;
import datatypes.AdventureStateEnum;
import datatypes.QuestStateEnum;

/**
 * @author Merlin
 *
 */
public class PDFAdventureWriterTest
{
	/**
	 * This is really just a driver to generate a file - it doesn't "test"
	 * anything. You have to look at test.pdf to see if it was built correctly
	 */
	@Test
	public void testCanWritePDF()
	{
		buildAPlayerWithAdventures();

		PDFAdventureWriter writer = new PDFAdventureWriter();
		writer.createPDFOfTriggeredExternalAdventures("test.pdf");
	}

	/**
	 * 
	 */
	public static void buildAPlayerWithAdventures()
	{
		ThisClientsPlayer cp = ThisClientsPlayerTest
				.setUpThisClientsPlayer(PlayersForTest.MERLIN);
		ClientPlayerAdventureStateDTO a = new ClientPlayerAdventureStateDTO(
				1,
				"Find the Department Secretary of the Computer Science & Engineering Department and introduce yourself",
				5, AdventureStateEnum.TRIGGERED, true, true, "The gods", QuestStateEnum.AVAILABLE);
		ClientPlayerQuestStateDTO q = new ClientPlayerQuestStateDTO(1, "First Quest", "Test Quest 1",
				QuestStateEnum.COMPLETED, 1, 2, true, null);
		q.addAdventure(a);
		a = new ClientPlayerAdventureStateDTO(1, "Another adventure's description which should not be in the PDF", 10,
				AdventureStateEnum.TRIGGERED, true, false, null, QuestStateEnum.AVAILABLE);
		q.addAdventure(a);
		a = new ClientPlayerAdventureStateDTO(1, "Another adventure's description", 10,
				AdventureStateEnum.TRIGGERED, true, true, "Csar", QuestStateEnum.AVAILABLE);
		q.addAdventure(a);
		cp.addQuest(q);
	}
}
