package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.ActionTypeDTO;
import edu.ship.engr.shipsim.dataDTO.ObjectiveCompletionTypeDTO;
import edu.ship.engr.shipsim.dataDTO.QuestEditingInfoDTO;
import edu.ship.engr.shipsim.dataDTO.QuestRecordDTO;
import edu.ship.engr.shipsim.dataENUM.ObjectiveCompletionType;
import edu.ship.engr.shipsim.dataDTO.ObjectiveRecordDTO;
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

            //Getting the Map Names from the DB and putting them into a DTO
            questEditingInfoDTO.setMapNames(MapTableDataGateway.getSingleton().getMapNames());

            //Getting the Quest Completion Action Types from the ENUM and putting them into a DTO
            ArrayList<ActionTypeDTO> actionTypeDTOs = new ArrayList<>();
            for(QuestCompletionActionType q: QuestCompletionActionType.values())
            {
                actionTypeDTOs.add(new ActionTypeDTO(q.getID(), q.toString()));
            }
            questEditingInfoDTO.setCompletionActionTypes(actionTypeDTOs);

            //Getting the Quest Records from the DB and putting them into a DTO
            ArrayList<QuestRecord> questRecordsFromMapper = QuestMapper.getAllQuests();
            ArrayList<QuestRecordDTO> questRecordDTOs = new ArrayList<>();

            for(QuestRecord q: questRecordsFromMapper)
            {
                // Pack list of objectives into DTOs
                ArrayList<ObjectiveRecordDTO> objectiveDTOs = new ArrayList<>();
                for (ObjectiveRecord o : q.getObjectives())
                {
                    objectiveDTOs.add(new ObjectiveRecordDTO(o.getObjectiveID(),
                            o.getObjectiveDescription(),
                            o.getExperiencePointsGained(), o.getQuestID(),
                            o.getCompletionType().getID()));
                }

                questRecordDTOs.add(new QuestRecordDTO(q.getTitle(),
                        q.getDescription(), objectiveDTOs, q.getQuestID(),
                        q.getTriggerMapName(), q.getPosition(),
                        q.getExperiencePointsGained(),
                        q.getObjectivesForFulfillment(),
                        new ActionTypeDTO(q.getCompletionActionType().getID(),
                                q.getCompletionActionType().toString()),
                        q.getStartDate(), q.getEndDate(), q.isEasterEgg()));
            }
            questEditingInfoDTO.setQuestRecords(questRecordDTOs);

            //Getting the Objective Completion Types from the ENUM and putting them into a DTO
            ArrayList<ObjectiveCompletionTypeDTO> objCompletionTypeDTOs = new ArrayList<>();
            for(ObjectiveCompletionType o: ObjectiveCompletionType.values())
            {
                objCompletionTypeDTOs.add(new ObjectiveCompletionTypeDTO(o.getID(), o.toString()));
            }
            questEditingInfoDTO.setObjCompletionTypes(objCompletionTypeDTOs);

            GetQuestInformationReport report = new GetQuestInformationReport(questEditingInfoDTO);
            ReportObserverConnector.getSingleton().sendReport(report);
        }
        catch(DatabaseException e)
        {
            throw new RuntimeException(e);
        }

    }
}