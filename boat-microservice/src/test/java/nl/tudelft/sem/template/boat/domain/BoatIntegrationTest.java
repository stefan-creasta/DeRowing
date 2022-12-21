package nl.tudelft.sem.template.boat.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.tudelft.sem.template.boat.authentication.AuthManager;
import nl.tudelft.sem.template.boat.authentication.JwtTokenVerifier;
import nl.tudelft.sem.template.boat.models.BoatCreateModel;
import nl.tudelft.sem.template.boat.repositories.BoatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles({"test", "mockTokenVerifier", "mockAuthenticationManager"})
@AutoConfigureMockMvc
public class BoatIntegrationTest {
    @Autowired
    private AuthManager mockAuthenticationManager;
    @Autowired
    private JwtTokenVerifier mockTokenVerifier;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private BoatRepository boatRepository;

    @BeforeEach
    public void setup() {
        when(mockAuthenticationManager.getNetId()).thenReturn("ExampleUser");
        when(mockTokenVerifier.validateToken(anyString())).thenReturn(true);
        when(mockTokenVerifier.getNetIdFromToken(anyString())).thenReturn("ExampleUser");
    }

    public String serialize(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
        }
        catch(JsonProcessingException e) {
            System.out.println("Serialization error");
        }
        return null;
    }

    @Test
    public void createBoatTest() throws Exception{
        BoatCreateModel body = new BoatCreateModel(Type.C4);
        ResultActions res = mvc.perform(post("/boat/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(serialize(body))
                .header("Authorization", "Bearer MockedToken"));
        String response = res.andReturn().getResponse().getContentAsString();
        res.andExpect(status().isOk());
        Integer sol = new ObjectMapper().readValue(response, Integer.class);
        assertEquals(1, sol);
    }
}
