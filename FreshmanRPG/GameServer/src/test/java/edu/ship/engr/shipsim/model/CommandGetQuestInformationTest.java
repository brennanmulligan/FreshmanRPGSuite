package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.criteria.QuestCompletionActionParameter;
import edu.ship.engr.shipsim.dataDTO.ActionTypeDTO;
import edu.ship.engr.shipsim.dataDTO.QuestEditingInfoDTO;
import edu.ship.engr.shipsim.dataDTO.QuestRecordDTO;
import edu.ship.engr.shipsim.dataENUM.QuestCompletionActionType;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.DuplicateNameException;
import edu.ship.engr.shipsim.datasource.MapTableDataGateway;
import edu.ship.engr.shipsim.datasource.QuestTableDataGateway;
import edu.ship.engr.shipsim.datatypes.QuestsForTest;
import edu.ship.engr.shipsim.model.reports.GetQuestInformationReport;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

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

        MapTableDataGateway mapGateway = MapTableDataGateway.getInstance();
        MapTableDataGateway mockMapGateway = mock(MapTableDataGateway.class);
        mapGateway.setInstance(mockMapGateway);

        QuestEditingInfoDTO questEditingInfoDTO = new QuestEditingInfoDTO();
        QuestCompletionActionParameter parameter;
        QuestRecord record = new QuestRecord(QuestsForTest.ONE_BIG_QUEST.getQuestID(),
                QuestsForTest.ONE_BIG_QUEST.getQuestTitle(),
                QuestsForTest.ONE_BIG_QUEST.getQuestDescription(),
                QuestsForTest.ONE_BIG_QUEST.getMapName(),
                QuestsForTest.ONE_BIG_QUEST.getPosition(),
                new ArrayList<ObjectiveRecord>(), QuestsForTest.ONE_BIG_QUEST.getExperienceGained(),
                QuestsForTest.ONE_BIG_QUEST.getObjectiveForFulfillment(),
                QuestsForTest.ONE_BIG_QUEST.getCompletionActionType(), QuestsForTest.ONE_BIG_QUEST.getCompletionActionParameter(),
                QuestsForTest.ONE_BIG_QUEST.getStartDate(), QuestsForTest.ONE_BIG_QUEST.getEndDate(), false);
        ArrayList<QuestRecord> questRecords = new ArrayList<>()
        {{
            add(record);
        }};

        when(mockQuestGateway.getAllQuests()).thenReturn(questRecords);
        ArrayList<QuestRecordDTO> questRecordDTOs = new ArrayList<QuestRecordDTO>();
        for(QuestRecord q: questRecords)
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

        ArrayList<ActionTypeDTO> actionTypeDTOs = new ArrayList<>();
        for(QuestCompletionActionType q: QuestCompletionActionType.values())
        {
            actionTypeDTOs.add(new ActionTypeDTO(q.getID(), q.toString()));
        }
        questEditingInfoDTO.setCompletionActionTypes(actionTypeDTOs);

        ArrayList<String> mapNames = new ArrayList<String>();
        mapNames.add("de_dust");
        when(mockMapGateway.getMapNames()).thenReturn(mapNames);
        questEditingInfoDTO.setMapNames(mapNames);

        CommandGetQuestInformation command = new CommandGetQuestInformation();
        command.execute();

        verify(obs, times(1)).receiveReport(new GetQuestInformationReport(questEditingInfoDTO));
    }
}