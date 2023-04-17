package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.QuestInfoDTO;
import edu.ship.engr.shipsim.dataENUM.QuestCompletionActionType;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.model.reports.GetAllCrewsReport;
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
    public void testGetQuestInfo() throws DatabaseException
    {
        ReportObserver obs = mock(ReportObserver.class);
        ReportObserverConnector.getSingleton().registerObserver(obs,
                GetQuestInformationReport.class);

        QuestTableDataGateway gw = new QuestTableDataGateway.getSingleton();
        QuestTableDataGateway mockGW = new mock(QuestTableDataGateway.class);
        gw.getSingleton(mockGW);

        ArrayList<QuestCompletionActionType> actionTypes = new ArrayList<>();
        QuestInfoDTO questInfoDTO = new QuestInfoDTO();


        when(mockGW.getCompletionActionTypes()).thenReturn(actionTypes);
        questInfoDTO.setCompletionActionTypes(actionTypes);

        CommandGetQuestInformation getQuestInformation =
                new CommandGetQuestInformation();
        getQuestInformation.execute();

        verify(obs, times(1)).receiveReport(
                new GetQuestInformationReport(questInfoDTO));
    }

}
