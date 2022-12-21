package nl.tudelft.sem.template.boat.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.tudelft.sem.template.boat.authentication.AuthManager;
import nl.tudelft.sem.template.boat.authentication.JwtTokenVerifier;
import nl.tudelft.sem.template.boat.models.BoatCreateModel;
import nl.tudelft.sem.template.boat.repositories.BoatRepository;
import nl.tudelft.sem.template.boat.services.BoatService;
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
@ActiveProfiles({"test", "authManager", "jwtTokenVerifier", "boatService"})
@AutoConfigureMockMvc
public class BoatIntegrationTest {
    @Autowired
    private AuthManager authManager;
    @Autowired
    private JwtTokenVerifier jwtTokenVerifier;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private BoatRepository boatRepository;
    @Autowired
    private BoatService boatService;

    @BeforeEach
    public void setup() {
        when(authManager.getNetId()).thenReturn("ExampleUser");
        when(jwtTokenVerifier.validateToken(anyString())).thenReturn(true);
        when(jwtTokenVerifier.getNetIdFromToken(anyString())).thenReturn("ExampleUser");
        reset(boatRepository);
        reset(boatService);
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
        assertEquals("Done! The boat of type C4 is created by ExampleUser", response);
    }
}
