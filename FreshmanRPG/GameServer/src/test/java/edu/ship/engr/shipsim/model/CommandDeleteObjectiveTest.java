package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.ObjectiveRowDataGateway;
import edu.ship.engr.shipsim.datasource.ObjectiveTableDataGateway;
import edu.ship.engr.shipsim.model.reports.ObjectiveDeletedReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@GameTest("GameServer")
public class CommandDeleteObjectiveTest
{
    @Test
    public void testCommandDeleteObject() throws DatabaseException
    {
        ReportObserver obs = mock(ReportObserver.class);
        ReportObserverConnector.getSingleton().registerObserver(obs,
                ObjectiveDeletedReport.class);

        ObjectiveRowDataGateway testQuest = new ObjectiveRowDataGateway(1,5);

        ObjectiveDeletedReport testReport = new ObjectiveDeletedReport(true, "");

        CommandDeleteObjective deleteObjective = new CommandDeleteObjective(1,5);
        deleteObjective.execute();

        ObjectiveTableDataGateway.createRow(testQuest.getObjectiveId(),
                testQuest.getObjectiveDescription(),
                testQuest.getQuestId(),
                testQuest.getExperiencePointsEarned(),
                testQuest.getCompletionType(),
                testQuest.getCompletionCriteria());

        verify(obs, times(1)).receiveReport(testReport);

    }
}
