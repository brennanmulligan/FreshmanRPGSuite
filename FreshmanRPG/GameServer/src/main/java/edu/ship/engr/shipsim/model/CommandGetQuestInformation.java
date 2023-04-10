package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.QuestInfoDTO;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.DuplicateNameException;
import edu.ship.engr.shipsim.model.reports.GetQuestInformationReport;

import java.util.ArrayList;

public class CommandGetQuestInformation extends Command
{
    public CommandGetQuestInformation() {

    }

    @Override
    void execute() throws DuplicateNameException
    {
        QuestTableDataGateway gw = QuestTableDataGateway.getSingleton();

        try
        {
            QuestInfoDTO questInfoDTO = new QuestInfoDTO();
            questInfoDTO.setCompletionActionTypes(gw.getCompletionActionTypes);
            GetQuestInformationReport report = new GetQuestInformationReport(questInfoDTO);
            ReportObserverConnector.getSingleton().sendReport(report);
        }
        catch(DatabaseException e)
        {
            throw new RuntimeException(e);
        }

    }
}
