package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.dataDTO.QuestEditingInfoDTO;
import edu.ship.engr.shipsim.model.Report;

public class GetQuestInformationReport implements Report
{
    private final QuestEditingInfoDTO questEditingInfoDTO;

    public GetQuestInformationReport(QuestEditingInfoDTO questEditingInfoDTO)
    {
        this.questEditingInfoDTO = questEditingInfoDTO;
    }
}
