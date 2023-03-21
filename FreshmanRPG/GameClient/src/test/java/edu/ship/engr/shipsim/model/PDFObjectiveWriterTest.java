package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.ClientPlayerObjectiveStateDTO;
import edu.ship.engr.shipsim.dataDTO.ClientPlayerQuestStateDTO;
import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

/**
 * @author Merlin
 */
@GameTest("GameClient")
public class PDFObjectiveWriterTest
{
    /**
     * This is really just a driver to generate a file - it doesn't "test"
     * anything. You have to look at test.pdf to see if it was built correctly
     */
    @Test
    public void testCanWritePDF()
    {
        buildAPlayerWithObjectives();

        PDFObjectiveWriter writer = new PDFObjectiveWriter();
        writer.createPDFOfTriggeredExternalObjectives("test.pdf");
    }

    /**
     *
     */
    public static void buildAPlayerWithObjectives()
    {
        ThisClientsPlayer cp = ThisClientsPlayerTest
                .setUpThisClientsPlayer(PlayersForTest.MERLIN);
        ClientPlayerObjectiveStateDTO objective = new ClientPlayerObjectiveStateDTO(
                1,
                "Find the Department Secretary of the Computer Science & Engineering Department and introduce yourself",
                5, ObjectiveStateEnum.TRIGGERED, true, true, "The gods", QuestStateEnum.AVAILABLE);
        ClientPlayerQuestStateDTO q = new ClientPlayerQuestStateDTO(1, "First Quest", "Test Quest 1",
                QuestStateEnum.COMPLETED, 1, 2, true, null, false);
        q.addObjective(objective);
        objective = new ClientPlayerObjectiveStateDTO(1, "Another objective's description which should not be in the PDF", 10,
                ObjectiveStateEnum.TRIGGERED, true, false, null, QuestStateEnum.AVAILABLE);
        q.addObjective(objective);
        objective = new ClientPlayerObjectiveStateDTO(1, "Another objective's description", 10,
                ObjectiveStateEnum.TRIGGERED, true, true, "Csar", QuestStateEnum.AVAILABLE);
        q.addObjective(objective);
        cp.addQuest(q);
    }
}
