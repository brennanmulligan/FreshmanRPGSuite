package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.dataDTO.QuestEditingInfoDTO;
import edu.ship.engr.shipsim.model.Report;
import lombok.Data;

import java.util.Objects;

@Data
public class GetQuestInformationReport implements Report
{
    private final QuestEditingInfoDTO questEditingInfoDTO;

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        GetQuestInformationReport that = (GetQuestInformationReport) o;
        return Objects.equals(questEditingInfoDTO,
                that.questEditingInfoDTO);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(questEditingInfoDTO);
    }

    public GetQuestInformationReport(QuestEditingInfoDTO questEditingInfoDTO)
    {
        this.questEditingInfoDTO = questEditingInfoDTO;
    }
}