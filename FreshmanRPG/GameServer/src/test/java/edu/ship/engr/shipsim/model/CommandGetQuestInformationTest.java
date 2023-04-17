package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.QuestEditingInfoDTO;
import edu.ship.engr.shipsim.dataENUM.QuestCompletionActionType;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.DuplicateNameException;
import edu.ship.engr.shipsim.datasource.QuestTableDataGateway;
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

        QuestTableDataGateway gw = QuestTableDataGateway.getSingleton();
        QuestTableDataGateway mockGW = mock(QuestTableDataGateway.class);
        gw.setSingleton(mockGW);

        ArrayList<QuestCompletionActionType> actionTypes = new ArrayList<>();
        QuestEditingInfoDTO questEditingInfoDTO = new QuestEditingInfoDTO();


        when(mockGW.getCompletionActionTypes()).thenReturn(actionTypes);
        questEditingInfoDTO.setCompletionActionTypes(actionTypes);

        CommandGetQuestInformation getQuestInformation =
                new CommandGetQuestInformation();
        getQuestInformation.execute();

        verify(obs, times(1)).receiveReport(
                new GetQuestInformationReport(questEditingInfoDTO));
    }

}
