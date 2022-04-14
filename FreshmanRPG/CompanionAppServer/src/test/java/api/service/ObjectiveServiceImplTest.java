package api.service;

import api.model.ObjectiveRequest;
import datasource.DatabaseException;
import datasource.ObjectiveStateTableDataGatewayRDS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ObjectiveServiceImplTest {

    @Mock
    ObjectiveStateTableDataGatewayRDS objectiveStateTableDataGatewayRDS;

    @Test
    void completeObjective() {
        ObjectiveService objectiveService = new ObjectiveServiceImpl(objectiveStateTableDataGatewayRDS);
        ObjectiveRequest objectiveRequest = new ObjectiveRequest(3, 3, 2,3, 1);
        int expectedResponse = 0;
        int actualResponse = objectiveService.completeObjective(objectiveRequest);
        Assertions.assertEquals(actualResponse, expectedResponse);
    }
}