package api.controller;

import api.model.LoginRequest;
import api.service.LoginService;
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

@ContextConfiguration(classes = LoginController.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(LoginController.class)
class LoginControllerTest
{

    //ObjectiveController is dependent on objectiveService, so we need to mock that class
    @MockBean
    private LoginService loginService;

    @Autowired
    private MockMvc mvc;

    /**
     * Test for controller receiving a POST containing LoginRequest as the content
     *
     * @throws Exception if database access fails
     */
    @Test
    void LoginObjective() throws Exception
    {
        LoginRequest loginRequest = new LoginRequest("john", "pw");

        String loginRequestJSON = new ObjectMapper().writeValueAsString(loginRequest);

        Mockito.when(loginService.loginWithCredentials(loginRequest.getUsername(), loginRequest.getPassword())).thenReturn(1);
        RequestBuilder request = MockMvcRequestBuilders.post("/login")
                .accept(MediaType.APPLICATION_JSON)
                .content(loginRequestJSON)
                .contentType(MediaType.APPLICATION_JSON);
        mvc.perform(request).andExpect(status().isOk())
                .andExpect((jsonPath("$.playerID", Matchers.is(1))));

    }
}
