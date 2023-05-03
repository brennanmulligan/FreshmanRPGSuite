package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.ActionTypeDTO;
import edu.ship.engr.shipsim.dataDTO.ObjectiveCompletionTypeDTO;
import edu.ship.engr.shipsim.dataDTO.ObjectiveRecordDTO;
import edu.ship.engr.shipsim.dataDTO.QuestEditingInfoDTO;
import edu.ship.engr.shipsim.dataDTO.QuestRecordDTO;
import edu.ship.engr.shipsim.dataENUM.ObjectiveCompletionType;
import edu.ship.engr.shipsim.dataENUM.QuestCompletionActionType;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.DuplicateNameException;
import edu.ship.engr.shipsim.datasource.MapTableDataGateway;
import edu.ship.engr.shipsim.datasource.QuestTableDataGateway;
import edu.ship.engr.shipsim.datatypes.QuestsForTest;
import edu.ship.engr.shipsim.model.reports.GetQuestInformationReport;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CommandGetQuestInformationTest
{
    @Test
    public void testGetQuestInfo()
            throws DatabaseException, DuplicateNameException
    {
        ReportObserver obs = mock(ReportObserver.class);
        ReportObserverConnector.getSingleton().registerObserver(obs,
                GetQuestInformationReport.class);

        QuestTableDataGateway questGateway = QuestTableDataGateway.getSingleton();
        QuestTableDataGateway mockQuestGateway = mock(QuestTableDataGateway.class);
        questGateway.setSingleton(mockQuestGateway);

        MapTableDataGateway mapGateway = MapTableDataGateway.getSingleton();
        MapTableDataGateway mockMapGateway = mock(MapTableDataGateway.class);
        mapGateway.setSingleton(mockMapGateway);

        QuestEditingInfoDTO questEditingInfoDTO = new QuestEditingInfoDTO();

        QuestMapper mapper = new QuestMapper(QuestsForTest.ONE_BIG_QUEST.getQuestID());
        ArrayList<QuestRecord> questRecords = new ArrayList<>()
        {{
            add(mapper.questRecord);
        }};

        when(mockQuestGateway.getAllQuests()).thenReturn(questRecords);
        ArrayList<QuestRecordDTO> questRecordDTOs = new ArrayList<>();
        for(QuestRecord q: questRecords)
        {
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

        ArrayList<ActionTypeDTO> actionTypeDTOs = new ArrayList<>();
        for(QuestCompletionActionType q: QuestCompletionActionType.values())
        {
            actionTypeDTOs.add(new ActionTypeDTO(q.getID(), q.toString()));
        }
        questEditingInfoDTO.setCompletionActionTypes(actionTypeDTOs);

        ArrayList<ObjectiveCompletionTypeDTO> objCompletionTypeDTOs = new ArrayList<>();
        for(ObjectiveCompletionType q: ObjectiveCompletionType.values())
        {
           objCompletionTypeDTOs.add(new ObjectiveCompletionTypeDTO(q.getID(), q.toString()));
        }
        questEditingInfoDTO.setObjCompletionTypes(objCompletionTypeDTOs);

        ArrayList<String> mapNames = new ArrayList<>();
        mapNames.add("de_dust");
        when(mockMapGateway.getMapNames()).thenReturn(mapNames);
        questEditingInfoDTO.setMapNames(mapNames);

        CommandGetQuestInformation command = new CommandGetQuestInformation();
        command.execute();

        verify(obs, times(1)).receiveReport(new GetQuestInformationReport(questEditingInfoDTO));
    }
}