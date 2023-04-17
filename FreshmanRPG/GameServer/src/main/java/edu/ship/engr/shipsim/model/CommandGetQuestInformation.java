package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.ActionTypeDTO;
import edu.ship.engr.shipsim.dataDTO.QuestEditingInfoDTO;
import edu.ship.engr.shipsim.dataENUM.QuestCompletionActionType;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.DuplicateNameException;
import edu.ship.engr.shipsim.datasource.QuestTableDataGateway;
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
            QuestEditingInfoDTO questEditingInfoDTO = new QuestEditingInfoDTO();
            ArrayList<QuestCompletionActionType> actionTypeEnums = gw.getCompletionActionTypes();
            ArrayList<ActionTypeDTO> actionTypeDTOS = new ArrayList<ActionTypeDTO>();
            for(QuestCompletionActionType q: actionTypeEnums)
            {
                actionTypeDTOS.add(new ActionTypeDTO(q.getCompletionActionParameterType(), q.getID()));
            }

            //questEditingInfoDTO.setCompletionActionTypes(gw.getCompletionActionTypes());
            GetQuestInformationReport report = new GetQuestInformationReport(
                    questEditingInfoDTO);
            ReportObserverConnector.getSingleton().sendReport(report);
        }
        catch(DatabaseException e)
        {
            throw new RuntimeException(e);
        }

    }
}
