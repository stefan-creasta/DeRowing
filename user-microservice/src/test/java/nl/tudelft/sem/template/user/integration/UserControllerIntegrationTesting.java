package nl.tudelft.sem.template.user.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.tudelft.sem.template.user.authentication.AuthManager;
import nl.tudelft.sem.template.user.authentication.JwtTokenVerifier;
import nl.tudelft.sem.template.user.domain.Certificate;
import nl.tudelft.sem.template.user.domain.Gender;
import nl.tudelft.sem.template.user.domain.NetId;
import nl.tudelft.sem.template.user.domain.Position;
import nl.tudelft.sem.template.user.domain.entities.Message;
import nl.tudelft.sem.template.user.domain.entities.User;
import nl.tudelft.sem.template.user.domain.models.UserAcceptanceUpdateModel;
import nl.tudelft.sem.template.user.domain.models.UserDetailModel;
import nl.tudelft.sem.template.user.domain.models.UserJoinRequestModel;
import nl.tudelft.sem.template.user.domain.repositories.MessageRepository;
import nl.tudelft.sem.template.user.domain.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
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
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles({"mockTokenVerifier", "mockAuthenticationManager"})
@AutoConfigureMockMvc
public class UserControllerIntegrationTesting {

    @Autowired
    private AuthManager mockAuthenticationManager;
    @Autowired
    private JwtTokenVerifier mockJwtTokenVerifier;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;

    /**
     * Set up before testing.
     */
    @BeforeEach
    public void setup() {
	// Authorization mocking
	when(mockAuthenticationManager.getNetId()).thenReturn("ExampleUser");
	when(mockJwtTokenVerifier.validateToken(anyString())).thenReturn(true);
	when(mockJwtTokenVerifier.getNetIdFromToken(anyString())).thenReturn("ExampleUser");
	// Reset database state
	userRepository.deleteAll();
    }

    /**
     * performing post request.
     *
     * @param body model
     * @param path path
     * @return the response
     * @throws Exception exception
     */
    public ResultActions performPost(Object body, String path) throws Exception {
	return mockMvc.perform(post(path)
	    .contentType(MediaType.APPLICATION_JSON)
	    .content(serialize(body))
	    .header("Authorization", "Bearer MockedToken"));
    }

    /**
     * performing post request.
     *
     * @param path path
     * @return the response
     * @throws Exception exception
     */
    public ResultActions performGet(String path) throws Exception {
	return mockMvc.perform(get(path)
	    .contentType(MediaType.APPLICATION_JSON)
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
    public void createUserTest() throws Exception {
	UserDetailModel body = new UserDetailModel(Gender.MALE, "Delft", true, Certificate.PLUS4);
	User expected = new User(new NetId("ExampleUser"), Gender.MALE, Certificate.PLUS4, "Delft", true);
	ResultActions res = performPost(body, "/create");
	String response = res.andReturn().getResponse().getContentAsString();

	res.andExpect(status().isOk());
	assertEquals("Congratulations ExampleUser, you have created your user", response);
	User user = userRepository.findByNetId(new NetId("ExampleUser"));
	assertEquals(expected, user);
    }

    @Test
    public void getDetailTest() throws Exception {
	UserDetailModel body = new UserDetailModel(Gender.MALE, "Delft", true, Certificate.PLUS4);
	performPost(body, "/create");
	ResultActions res = performPost("", "/getdetails");
	String json = res.andReturn().getResponse().getContentAsString();
	UserDetailModel result = new ObjectMapper().readValue(json, UserDetailModel.class);
	assertEquals(body, result);
    }

    @Test
    public void sendApplicationOfRequesterToOwnerTest() throws Exception {
	UserJoinRequestModel body = new UserJoinRequestModel(new NetId("hminh"), Position.COACH, 2L);
	ResultActions res = performPost(body, "/join");
	String response = res.andReturn().getResponse().getContentAsString();
	assertEquals("The message is successfully saved", response);
	Message message = new Message("hminh", "ExampleUser", 2L,
	    "ExampleUser wants to join this competition/training session. "
	    + "They want to join for position COACH",
	    Position.COACH);
	Message result = messageRepository.findById(2L).get();
	assertEquals(message, result);
    }

    @Test
    public void sendDecisionOfOwnerToRequesterTest() throws Exception {
	UserAcceptanceUpdateModel body = new UserAcceptanceUpdateModel(true, Position.COACH, new NetId("hminh"));
	Message expected = new Message("hminh", "ExampleUser", 0L,
	    "ExampleUser accepted your request. You have been selected for position COACH",
	    Position.COACH);
	ResultActions res = performPost(body, "/update");
	String content = res.andReturn().getResponse().getContentAsString();
	assertEquals("The message is successfully saved", content);
	Message result = messageRepository.findById(1L).get();
	assertEquals(expected, result);
    }

    @Test
    public void getNotificationsTest() throws Exception {
	UserAcceptanceUpdateModel body1 = new UserAcceptanceUpdateModel(false,
	    Position.COACH, new NetId("ExampleUser"));
	UserAcceptanceUpdateModel body2 = new UserAcceptanceUpdateModel(false,
	    Position.SCULLING, new NetId("ExampleUser"));
	performPost(body1, "/update");
	performPost(body2, "/update");

	ResultActions res = performGet("/notifications");
	String json = res.andReturn().getResponse().getContentAsString();
	ObjectMapper mapper = new ObjectMapper();
	List<Message> messages = mapper.readValue(json,
	    mapper.getTypeFactory().constructCollectionType(List.class, Message.class));
	Message message = new Message("ExampleUser", "ExampleUser",
	    0L, "ExampleUser did not accept your request. Consider joining another activity!", Position.COACH);
	Message message1 = new Message("ExampleUser", "ExampleUser",
	    0L, "ExampleUser did not accept your request. Consider joining another activity!", Position.SCULLING);
	assertEquals(message, messages.get(0));
	assertEquals(message1, messages.get(1));
    }
}
