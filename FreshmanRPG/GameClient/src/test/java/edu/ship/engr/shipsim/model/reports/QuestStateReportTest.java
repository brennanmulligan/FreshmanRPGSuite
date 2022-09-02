package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.dataDTO.ClientPlayerQuestStateDTO;
import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Test the QuestStateReport
 *
 * @author Merlin
 */
public class QuestStateReportTest
{

    /**
     * Test that a QuestStateReport is initialized correctly
     */
    @Test
    public void test()
    {
        ArrayList<ClientPlayerQuestStateDTO> data = new ArrayList<>();
        ClientPlayerQuestStateDTO q = new ClientPlayerQuestStateDTO(4, "title", "silly", QuestStateEnum.TRIGGERED, 42, 13, true, null);
        data.add(q);
        QuestStateReport report = new QuestStateReport(data);
        assertEquals(data, report.getClientPlayerQuestList());

    }

}
