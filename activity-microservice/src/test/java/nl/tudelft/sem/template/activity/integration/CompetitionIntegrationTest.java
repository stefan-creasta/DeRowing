package nl.tudelft.sem.template.activity.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.tudelft.sem.template.activity.authentication.AuthManager;
import nl.tudelft.sem.template.activity.authentication.JwtTokenVerifier;
import nl.tudelft.sem.template.activity.domain.GenderConstraint;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Position;
import nl.tudelft.sem.template.activity.domain.Type;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import nl.tudelft.sem.template.activity.domain.events.BoatChangeEvent;
import nl.tudelft.sem.template.activity.domain.events.UserAcceptanceEvent;
import nl.tudelft.sem.template.activity.domain.repositories.CompetitionRepository;
import nl.tudelft.sem.template.activity.domain.services.BoatRestService;
import nl.tudelft.sem.template.activity.domain.services.UserRestService;
import nl.tudelft.sem.template.activity.models.AcceptRequestModel;
import nl.tudelft.sem.template.activity.models.CompetitionCreateModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles({"test", "mockTokenVerifier", "mockAuthenticationManager",
        "mockBoatRestService", "mockUserRestService", "mockApplicationEventPublisher"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class CompetitionIntegrationTest {

    // Rest services are mocked to avoid having to run the other microservices
    @Autowired
    private BoatRestService mockBoatRestService;
    @Autowired
    private ApplicationEventPublisher mockApplcationEventPublisher;
    @Autowired
    private UserRestService mockUserRestService;
    @Autowired
    private AuthManager mockAuthenticationManager;
    @Autowired
    private JwtTokenVerifier mockJwtTokenVerifier;
    @Autowired
    private MockMvc mockMvc;
    // This is used to assert db state
    @Autowired
    private CompetitionRepository competitionRepository;

    @BeforeEach
    public void setup() {
        // Authorization mocking
        when(mockAuthenticationManager.getNetId()).thenReturn("ExampleUser");
        when(mockJwtTokenVerifier.validateToken(anyString())).thenReturn(true);
        when(mockJwtTokenVerifier.getNetIdFromToken(anyString())).thenReturn("ExampleUser");
    }

    /**
     * Generates a competition object.
     *
     * @param boatId id of the boat
     * @param type Type of boat
     * @return A new competition
     */
    public Competition fabricateCompetition(long boatId, Type type) {
        return new Competition(new NetId("maarten"), "test", boatId, 1000, true,
                GenderConstraint.NO_CONSTRAINT, false, "TUDELFT", type);
    }

    /**
     * Serializes an object.
     *
     * @param obj An object
     * @return JSON of the input object
     */
    public String serialize(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            System.out.println("serialization error");
        }
        return null;
    }

    @Test
    public void createCompetitionTest() throws Exception {
        // Mocking
        when(mockBoatRestService.getBoatId(Type.C4)).thenReturn(1L);

        // Create input body
        CompetitionCreateModel body =
                new CompetitionCreateModel("testName", GenderConstraint.NO_CONSTRAINT,
                        true, false, "TUDELFT", 10000, Type.C4);

        ResultActions res = mockMvc.perform(post("/competition/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(serialize(body))
                .header("Authorization", "Bearer MockedToken"));

        String response = res.andReturn().getResponse().getContentAsString();

        // Assertions
        res.andExpect(status().isOk());
        assertEquals("Successfully created competition", response);
        verify(mockBoatRestService, times(1)).getBoatId(Type.C4);
        // Database assertions
        Competition stored = competitionRepository.findById(1L);
        assertEquals("testName", stored.getActivityName());
    }

    @Test
    public void noBoatMicroserviceCreateTest() throws Exception {
        // Mocking
        when(mockBoatRestService.getBoatId(Type.C4)).thenReturn(-1L);

        // Create input body
        CompetitionCreateModel body =
                new CompetitionCreateModel("testName", GenderConstraint.NO_CONSTRAINT,
                        true, false, "TUDELFT", 10000, Type.C4);

        ResultActions res = mockMvc.perform(post("/competition/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(serialize(body))
                .header("Authorization", "Bearer MockedToken"));

        String response = res.andReturn().getResponse().getContentAsString();

        // Assertions
        res.andExpect(status().isOk());
        assertEquals("Could not contact boat service", response);
        verify(mockBoatRestService, times(1)).getBoatId(Type.C4);
        // Database assertions
        Competition stored = competitionRepository.findById(1L);
        assertNull(stored);
    }

    @Test
    public void informUserAcceptedTest() throws Exception {
        // Set database state
        Competition c = fabricateCompetition(1L, Type.C4);
        competitionRepository.save(c);

        // Create input model
        AcceptRequestModel body = new AcceptRequestModel(true, 1L, new NetId("maarten"), Position.COX);

        ResultActions res = mockMvc.perform(post("/competition/inform")
                .contentType(MediaType.APPLICATION_JSON)
                .content(serialize(body))
                .header("Authorization", "Bearer MockedToken"));

        // Assert return value
        String response = res.andReturn().getResponse().getContentAsString();
        assertEquals("The user is now part of the activity", response);

        // Assert 2 informing events where published
        UserAcceptanceEvent e = new UserAcceptanceEvent(body.isAccepted(), body.getPosition(), body.getRequestee());
        BoatChangeEvent b = new BoatChangeEvent(1L, Position.COX, body.getRequestee());
        verify(mockApplcationEventPublisher, times(1)).publishEvent(ArgumentMatchers.refEq(e));
        verify(mockApplcationEventPublisher, times(1)).publishEvent(ArgumentMatchers.refEq(b));

        // Assert new DB state
        c = competitionRepository.findById(1L);
        assertEquals(1, c.getAttendees().size());
    }

}