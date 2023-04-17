package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.dataDTO.QuestInfoDTO;
import edu.ship.engr.shipsim.model.Report;

import java.util.ArrayList;

public class GetQuestInformationReport implements Report
{
    private final QuestInfoDTO questInfoDTO;

    public GetQuestInformationReport(QuestInfoDTO questInfoDTO)
    {
        this.questInfoDTO = questInfoDTO;
    }
}
