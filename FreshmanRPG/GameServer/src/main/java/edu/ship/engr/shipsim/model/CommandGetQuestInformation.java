package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.ActionTypeDTO;
import edu.ship.engr.shipsim.dataDTO.QuestEditingInfoDTO;
import edu.ship.engr.shipsim.dataDTO.QuestRecordDTO;
import edu.ship.engr.shipsim.dataENUM.QuestCompletionActionType;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.DuplicateNameException;
import edu.ship.engr.shipsim.datasource.MapTableDataGateway;
import edu.ship.engr.shipsim.datasource.QuestTableDataGateway;
import edu.ship.engr.shipsim.model.reports.GetQuestInformationReport;

import java.util.ArrayList;

public class CommandGetQuestInformation extends Command
{
    public CommandGetQuestInformation()
    {

    }

    @Override
    void execute() throws DuplicateNameException
    {
        QuestTableDataGateway gw = QuestTableDataGateway.getSingleton();

        try
        {
            QuestEditingInfoDTO questEditingInfoDTO = new QuestEditingInfoDTO();

            //Getting the Action Types from the Enum
            ArrayList<ActionTypeDTO> actionTypeDTOs = new ArrayList<>();
            for(QuestCompletionActionType q: QuestCompletionActionType.values())
            {
                actionTypeDTOs.add(new ActionTypeDTO(q.getID(), q.toString()));
            }
            questEditingInfoDTO.setCompletionActionTypes(actionTypeDTOs);

            //Getting the Quest Records from the DB
            ArrayList<QuestRecord> questRecordsFromMapper = QuestMapper.getAllQuests();
            ArrayList<QuestRecordDTO> questRecordDTOs = new ArrayList<QuestRecordDTO>();
            for(QuestRecord q: questRecordsFromMapper)
            {
                questRecordDTOs.add(new QuestRecordDTO(q.getTitle(),
                        q.getDescription(), q.getObjectives(), q.getQuestID(),
                        q.getMapName(), q.getPosition(),
                        q.getExperiencePointsGained(),
                        q.getObjectivesForFulfillment(),
                        new ActionTypeDTO(q.getCompletionActionType().getID(),
                                q.getCompletionActionType().toString()),
                        q.getStartDate(), q.getEndDate(), q.isEasterEgg()));
            }
            questEditingInfoDTO.setQuestRecords(questRecordDTOs);

            //Getting the Map Names from the DB
            questEditingInfoDTO.setMapNames(MapTableDataGateway.getInstance().getMapNames());

            GetQuestInformationReport report = new GetQuestInformationReport(questEditingInfoDTO);
            ReportObserverConnector.getSingleton().sendReport(report);
        }
        catch(DatabaseException e)
        {
            throw new RuntimeException(e);
        }

    }
}