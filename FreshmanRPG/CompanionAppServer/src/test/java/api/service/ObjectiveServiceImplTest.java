package api.service;

import api.model.ObjectiveRequest;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.ObjectiveStateTableDataGateway;
import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;
import edu.ship.engr.shipsim.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class ObjectiveServiceImplTest
{

    @Mock
    ObjectiveStateTableDataGateway objectiveStateTableDataGatewayRDS;

    @Test
    void completeObjective() throws IllegalObjectiveChangeException, DatabaseException, IllegalQuestChangeException
    {
        ObjectiveService objectiveService = new ObjectiveServiceImpl(objectiveStateTableDataGatewayRDS);
        ObjectiveRequest objectiveRequest = new ObjectiveRequest(3, 3, 102, 2, 1);
        int expectedResponse = 0;
        int actualResponse = objectiveService.completeObjective(objectiveRequest);
        Assertions.assertEquals(expectedResponse, actualResponse);

        ArrayList<QuestState> quests =
                QuestManager.getSingleton().getQuestList(1);
        for (QuestState q : quests)
        {
            if (q.getID() == 102)
            {
                for (ObjectiveState o : q.getObjectiveList())
                {
                    if (o.getID() == 2)
                    {
                        Assertions.assertEquals(ObjectiveStateEnum.COMPLETED, o.getState());
                    }
                }
            }
        }
    }
}