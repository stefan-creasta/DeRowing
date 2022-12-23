package nl.tudelft.sem.template.activity.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
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
import nl.tudelft.sem.template.activity.domain.Certificate;
import nl.tudelft.sem.template.activity.domain.Gender;
import nl.tudelft.sem.template.activity.domain.GenderConstraint;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Position;
import nl.tudelft.sem.template.activity.domain.Type;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import nl.tudelft.sem.template.activity.domain.events.BoatChangeEvent;
import nl.tudelft.sem.template.activity.domain.events.UserAcceptanceEvent;
import nl.tudelft.sem.template.activity.domain.exceptions.UnsuccessfulRequestException;
import nl.tudelft.sem.template.activity.domain.provider.implement.CurrentTimeProvider;
import nl.tudelft.sem.template.activity.domain.repositories.CompetitionRepository;
import nl.tudelft.sem.template.activity.domain.services.RestServiceFacade;
import nl.tudelft.sem.template.activity.models.AcceptRequestModel;
import nl.tudelft.sem.template.activity.models.ActivityCancelModel;
import nl.tudelft.sem.template.activity.models.BoatDeleteModel;
import nl.tudelft.sem.template.activity.models.CompetitionCreateModel;
import nl.tudelft.sem.template.activity.models.CompetitionEditModel;
import nl.tudelft.sem.template.activity.models.CreateBoatResponseModel;
import nl.tudelft.sem.template.activity.models.JoinRequestModel;
import nl.tudelft.sem.template.activity.models.UserDataRequestModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles({"test", "mockTokenVerifier", "mockAuthenticationManager",
        "mockApplicationEventPublisher", "mockRestServiceFacade"})
@AutoConfigureMockMvc
class CompetitionIntegrationTest {

    private static final long activityId = 1L;
    // Rest services are mocked to avoid having to run the other microservices
    @Autowired
    private RestServiceFacade mockRestServiceFacade;
    @Autowired
    private ApplicationEventPublisher mockApplicationEventPublisher;
    @Autowired
    private AuthManager mockAuthenticationManager;
    @Autowired
    private JwtTokenVerifier mockJwtTokenVerifier;
    @Autowired
    private CurrentTimeProvider currentTimeProvider;
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
        // Reset mock counters
        reset(mockApplicationEventPublisher);
        reset(mockRestServiceFacade);
        // Reset database state
        competitionRepository.deleteAll();
        competitionRepository.resetSequence();
    }

    /**
     * Generates a competition object.
     *
     * @param boatId id of the boat
     * @param type Type of boat
     * @return A new competition
     */
    public Competition fabricateCompetition(long boatId, Type type) {
        long curr = currentTimeProvider.getCurrentTime().toEpochMilli();
        curr += 2 * (24 * 60 * 60 * 1000);
        return new Competition(new NetId("maarten"), "test", boatId, curr, true,
                GenderConstraint.NO_CONSTRAINT, false, "TUDELFT", type);
    }

    public ResultActions performPost(Object body, String path) throws Exception {
        return mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(serialize(body))
                .header("Authorization", "Bearer MockedToken"));
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
        when(mockRestServiceFacade.performBoatModel(any(), any(), any())).thenReturn(new CreateBoatResponseModel(1L));

        // Create input body
        CompetitionCreateModel body =
                new CompetitionCreateModel("testName", GenderConstraint.NO_CONSTRAINT,
                        true, false, "TUDELFT", 10000, Type.C4);

        ResultActions res = performPost(body, "/competition/create");

        String response = res.andReturn().getResponse().getContentAsString();

        // Assertions
        res.andExpect(status().isOk());
        assertEquals("Successfully created competition", response);
        verify(mockRestServiceFacade, times(1)).performBoatModel(any(), any(), any());
        // Database assertions
        Competition stored = competitionRepository.findById(1L);
        assertEquals("testName", stored.getActivityName());
    }

    @Test
    public void noBoatMicroserviceCreateTest() throws Exception {
        // Mocking
        when(mockRestServiceFacade.performBoatModel(any(), any(), any())).thenReturn(null);

        // Create input body
        CompetitionCreateModel body =
                new CompetitionCreateModel("testName", GenderConstraint.NO_CONSTRAINT,
                        true, false, "TUDELFT", 10000, Type.C4);

        ResultActions res = performPost(body, "/competition/create");

        String response = res.andReturn().getResponse().getContentAsString();

        // Assertions
        res.andExpect(status().isOk());
        assertEquals("Could not create boat", response);
        verify(mockRestServiceFacade, times(1)).performBoatModel(any(), any(), any());
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

        ResultActions res = performPost(body, "/competition/inform");

        // Assert return value
        String response = res.andReturn().getResponse().getContentAsString();
        assertEquals("The user is informed of your decision", response);

        // Assert 2 informing events where published
        UserAcceptanceEvent e = new UserAcceptanceEvent(body.isAccepted(), body.getPosition(),
                body.getRequestee(), activityId);
        BoatChangeEvent b = new BoatChangeEvent(1L, Position.COX, body.getRequestee());
        verify(mockApplicationEventPublisher, times(1)).publishEvent(ArgumentMatchers.refEq(e));
        verify(mockApplicationEventPublisher, times(1)).publishEvent(ArgumentMatchers.refEq(b));

        // Assert new DB state
        c = competitionRepository.findById(1L);
        assertEquals(1, c.getAttendees().size());
    }

    @Test
    public void informUserRejectedTest() throws Exception {
        // Set database state
        Competition c = fabricateCompetition(1L, Type.C4);
        competitionRepository.save(c);

        // Create input model
        AcceptRequestModel body = new AcceptRequestModel(false, 1L, new NetId("maarten"), Position.COX);

        ResultActions res = performPost(body, "/competition/inform");

        // Assert return value
        String response = res.andReturn().getResponse().getContentAsString();
        assertEquals("The user is informed of your decision", response);

        // Assert 2 informing events where published
        UserAcceptanceEvent e = new UserAcceptanceEvent(body.isAccepted(), body.getPosition(),
                body.getRequestee(), activityId);
        BoatChangeEvent b = new BoatChangeEvent(1L, Position.COX, body.getRequestee());
        verify(mockApplicationEventPublisher, times(1)).publishEvent(ArgumentMatchers.refEq(e));
        verify(mockApplicationEventPublisher, times(0)).publishEvent(ArgumentMatchers.refEq(b));

        // Assert new DB state
        c = competitionRepository.findById(1L);
        assertEquals(0, c.getAttendees().size());
    }

    @Test
    public void informUserWrongIdTest() throws Exception {
        // Set database state
        Competition c = fabricateCompetition(1L, Type.C4);
        competitionRepository.save(c);

        // Create input model
        AcceptRequestModel body = new AcceptRequestModel(false, 2L, new NetId("maarten"), Position.COX);

        ResultActions res = performPost(body, "/competition/inform");

        // Assert return value
        String response = res.andReturn().getResponse().getContentAsString();
        assertEquals("Could not find activity", response);

        // Assert 2 informing events where published
        UserAcceptanceEvent e = new UserAcceptanceEvent(body.isAccepted(), body.getPosition(),
                body.getRequestee(), activityId);
        BoatChangeEvent b = new BoatChangeEvent(1L, Position.COX, body.getRequestee());
        verify(mockApplicationEventPublisher, times(0)).publishEvent(ArgumentMatchers.refEq(e));
        verify(mockApplicationEventPublisher, times(0)).publishEvent(ArgumentMatchers.refEq(b));

        // Assert new DB state
        c = competitionRepository.findById(1L);
        assertEquals(0, c.getAttendees().size());
    }

    @Test
    public void joinActivityPassConstraintsTest() throws Exception {
        // Configure mocks
        UserDataRequestModel model = new UserDataRequestModel(Gender.MALE, "TUDELFT", true, Certificate.C4);
        when(mockRestServiceFacade.performUserModel(null, "/getdetails", UserDataRequestModel.class)).thenReturn(model);

        // Set database state
        Competition c = fabricateCompetition(1L, Type.C4);
        competitionRepository.save(c);

        // Create body
        JoinRequestModel body = new JoinRequestModel();
        body.setPosition(Position.COX);
        body.setActivityId(1L);

        ResultActions res = performPost(body, "/competition/join");

        // Assert return value
        String response = res.andReturn().getResponse().getContentAsString();
        assertEquals("Done! Your request has been processed", response);

        // Verify mocks
        verify(mockRestServiceFacade, times(1)).performUserModel(null, "/getdetails", UserDataRequestModel.class);
    }

    @Test
    public void joinActivityNotPassConstraintsTest() throws Exception {
        // Configure mocks
        UserDataRequestModel model = new UserDataRequestModel(Gender.MALE, "UVA", true, Certificate.C4);
        when(mockRestServiceFacade.performUserModel(null, "/getdetails", UserDataRequestModel.class)).thenReturn(model);

        // Set database state
        Competition c = fabricateCompetition(1L, Type.C4);
        c.setGenderConstraint(GenderConstraint.ONLY_FEMALE);
        competitionRepository.save(c);

        // Create body
        JoinRequestModel body = new JoinRequestModel();
        body.setPosition(Position.COX);
        body.setActivityId(1L);

        // Does not pass gender constraint

        ResultActions res = performPost(body, "/competition/join");

        // Assert return value
        String response = res.andReturn().getResponse().getContentAsString();
        assertEquals("you do not meet the constraints of this competition", response);


        // Does not pass Organization constraint

        c.setGenderConstraint(GenderConstraint.NO_CONSTRAINT);
        c.setSingleOrganization(true);
        competitionRepository.deleteById(1L);
        competitionRepository.resetSequence();
        competitionRepository.save(c);

        res = performPost(body, "/competition/join");

        // Assert return value
        response = res.andReturn().getResponse().getContentAsString();
        assertEquals("you do not meet the constraints of this competition", response);

        // Does not pass the Amateur constraint

        c.setSingleOrganization(false);
        c.setAllowAmateurs(false);
        competitionRepository.deleteById(1L);
        competitionRepository.resetSequence();
        competitionRepository.save(c);

        res = performPost(body, "/competition/join");

        // Assert return value
        response = res.andReturn().getResponse().getContentAsString();
        assertEquals("you do not meet the constraints of this competition", response);

        // Verify mocks
        verify(mockRestServiceFacade, times(3)).performUserModel(null, "/getdetails", UserDataRequestModel.class);
    }

    @Test
    public void joinGenderErrorTest() throws Exception {
        JoinRequestModel body = new JoinRequestModel();
        body.setPosition(Position.COX);
        body.setActivityId(1L);

        // Set database state
        Competition c = fabricateCompetition(1L, Type.C4);
        c.setGenderConstraint(GenderConstraint.ONLY_MALE);
        competitionRepository.save(c);

        // Configure mocks
        UserDataRequestModel model = new UserDataRequestModel(Gender.FEMALE, "TUDELFT", true, Certificate.C4);
        when(mockRestServiceFacade.performUserModel(null, "/getdetails", UserDataRequestModel.class)).thenReturn(model);

        ResultActions res = performPost(body, "/competition/join");

        // Assert return value
        String response = res.andReturn().getResponse().getContentAsString();
        assertEquals("you do not meet the constraints of this competition", response);
    }

    @Test
    public void joinActivityAllErrorsTest() throws Exception {
        // Create body
        JoinRequestModel body = new JoinRequestModel();
        body.setPosition(Position.COX);
        body.setActivityId(1L);

        // Activity ID does not exist

        ResultActions res = performPost(body, "/competition/join");

        // Assert return value
        String response = res.andReturn().getResponse().getContentAsString();
        assertEquals("this competition ID does not exist", response);

        // Set database state
        Competition c = fabricateCompetition(1L, Type.C4);
        competitionRepository.save(c);

        // UserData request doesnt work

        res = performPost(body, "/competition/join");

        // Assert return value
        response = res.andReturn().getResponse().getContentAsString();
        assertEquals("We could not get your user information from the user service", response);

        // Change db state
        c.setType(Type.PLUS4);
        competitionRepository.save(c);
        competitionRepository.deleteById(1L);
        competitionRepository.resetSequence();
        competitionRepository.save(c);

        // Configure mocks
        UserDataRequestModel model = new UserDataRequestModel(Gender.MALE, "TUDELFT", true, Certificate.C4);
        when(mockRestServiceFacade.performUserModel(null, "/getdetails", UserDataRequestModel.class)).thenReturn(model);

        // Invalid certificate test
        res = performPost(body, "/competition/join");

        // Assert return value
        response = res.andReturn().getResponse().getContentAsString();
        assertEquals("you do not have the required certificate to be cox", response);

        // Change db state
        c.setType(Type.C4);
        c.setStartTime(currentTimeProvider.getCurrentTime().toEpochMilli());
        competitionRepository.save(c);
        competitionRepository.deleteById(1L);
        competitionRepository.resetSequence();
        competitionRepository.save(c);

        // Invalid time constraint test
        res = performPost(body, "/competition/join");

        // Assert return value
        response = res.andReturn().getResponse().getContentAsString();
        assertEquals("Sorry you can't join this competition since it will start in one day.", response);
    }

    @Test
    public void cancelTest() throws Exception {
        when(mockAuthenticationManager.getNetId()).thenReturn("maarten");
        // Set database state
        Competition c = fabricateCompetition(1L, Type.C4);
        competitionRepository.save(c);

        // Configure mocks
        BoatDeleteModel model = new BoatDeleteModel(1L);

        ActivityCancelModel body = new ActivityCancelModel(1L);

        ResultActions res = performPost(body, "/competition/cancel");
        String response = res.andReturn().getResponse().getContentAsString();

        // Assertions
        assertEquals("Successfully deleted competition", response);
        verify(mockRestServiceFacade, times(1)).performBoatModel(any(), any(), any());
    }

    @Test
    public void cancelErrorTest() throws Exception {
        ActivityCancelModel body = new ActivityCancelModel(1L);

        // Activity does not exist
        ResultActions res = performPost(body, "/competition/cancel");
        String response = res.andReturn().getResponse().getContentAsString();
        assertEquals("Competition not found", response);

        // boatRestService fails
        when(mockAuthenticationManager.getNetId()).thenReturn("maarten");
        when(mockRestServiceFacade.performBoatModel(any(), any(), any())).thenThrow(new UnsuccessfulRequestException());
        Competition c = fabricateCompetition(1L, Type.C4);
        competitionRepository.save(c);

        res = performPost(body, "/competition/cancel");
        response = res.andReturn().getResponse().getContentAsString();
        assertEquals("Internal error when canceling the competition.", response);

    }

    @Test
    public void editTest() throws Exception {
        when(mockAuthenticationManager.getNetId()).thenReturn("maarten");
        // Set database state
        Competition c = fabricateCompetition(1L, Type.C4);
        competitionRepository.save(c);

        Competition expected = new Competition(new NetId("maarten"), "newName", 1L, -1L, false, GenderConstraint.ONLY_MALE,
                true, "TUBERLIN", Type.PLUS4);
        expected.setId(1L);

        CompetitionEditModel body = new CompetitionEditModel("newName", GenderConstraint.ONLY_MALE,
                false, true, "TUBERLIN", -1L, Type.PLUS4, 1L);

        // Pre assertion
        c = competitionRepository.findById(1L);
        assertNotEquals(expected, c);

        ResultActions res = performPost(body, "/competition/edit");
        String response = res.andReturn().getResponse().getContentAsString();

        // Post assertion
        assertEquals("Successfully edited competition", response);
        c = competitionRepository.findById(1L);
        assertEquals(expected, c);
    }

    @Test
    public void editActivityNotExistTest() throws Exception {
        CompetitionEditModel body = new CompetitionEditModel("newName", GenderConstraint.ONLY_MALE,
                false, true, "TUBERLIN", -1L, Type.PLUS4, 1L);

        ResultActions res = performPost(body, "/competition/edit");
        String response = res.andReturn().getResponse().getContentAsString();

        // Post assertion
        assertEquals("Internal error when editing competition.", response);
    }

}