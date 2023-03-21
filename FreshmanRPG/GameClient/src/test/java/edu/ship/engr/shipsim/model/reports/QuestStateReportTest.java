package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.dataDTO.ClientPlayerQuestStateDTO;
import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test the QuestStateReport
 *
 * @author Merlin
 */
@GameTest("GameClient")
public class QuestStateReportTest
{

    /**
     * Test that a QuestStateReport is initialized correctly
     */
    @Test
    public void test()
    {
        ArrayList<ClientPlayerQuestStateDTO> data = new ArrayList<>();
        ClientPlayerQuestStateDTO q = new ClientPlayerQuestStateDTO(4, "title", "silly", QuestStateEnum.TRIGGERED, 42, 13, true, null, false);
        data.add(q);
        QuestStateReport report = new QuestStateReport(data);
        assertEquals(data, report.getClientPlayerQuestList());

    }

}
