package api.controller;

import api.model.ObjectiveRequest;
import api.service.ObjectiveService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests Objective Authentication Controller
 *
 * @author Joel
 */


@ContextConfiguration(classes = ObjectiveController.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(ObjectiveController.class)
class ObjectiveControllerTest
{


    //ObjectiveController is dependent on objectiveService, so we need to mock that class
    @MockBean
    private ObjectiveService objectiveService;

    @Autowired
    private MockMvc mvc;

    /**
     * Test for controller receiving a POST containing ObjectiveAuthentication as the content
     *
     * @throws Exception if database access fails
     */
    @Test
    void authenticateObjective() throws Exception
    {
        ObjectiveRequest objectiveRequest = new ObjectiveRequest(3, 3, 2, 3, 1);

        String objectiveRequestJSON = new ObjectMapper().writeValueAsString(objectiveRequest);

        Mockito.when(objectiveService.completeObjective(objectiveRequest)).thenReturn(0);

        RequestBuilder request = MockMvcRequestBuilders.post("/objective-complete")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectiveRequestJSON)
                .contentType(MediaType.APPLICATION_JSON);
        mvc.perform(request).andExpect(status().isOk())
                .andExpect((jsonPath("$.responseType", Matchers.is(0))));

    }
}