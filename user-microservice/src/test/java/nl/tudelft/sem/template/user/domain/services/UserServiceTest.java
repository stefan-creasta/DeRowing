package nl.tudelft.sem.template.user.domain.services;

import nl.tudelft.sem.template.user.domain.Certificate;
import nl.tudelft.sem.template.user.domain.Gender;
import nl.tudelft.sem.template.user.domain.NetId;
import nl.tudelft.sem.template.user.domain.Position;
import nl.tudelft.sem.template.user.domain.entities.Message;
import nl.tudelft.sem.template.user.domain.entities.User;
import nl.tudelft.sem.template.user.domain.exceptions.NetIdAlreadyInUseException;
import nl.tudelft.sem.template.user.domain.models.UserDetailModel;
import nl.tudelft.sem.template.user.domain.repositories.MessageRepository;
import nl.tudelft.sem.template.user.domain.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private transient UserRepository userRepository;
    private transient MessageRepository messageRepository;
    private transient  UserService userService;
    UserDetailModel userDetailModel;
    NetId netId;
    User result;

    @BeforeEach
    public void setup() {
        userRepository = mock(UserRepository.class);
        messageRepository = mock(MessageRepository.class);
        userService = new UserService(userRepository, messageRepository);
        userDetailModel = new UserDetailModel(Gender.FEMALE, "Delft", true, Certificate.PLUS4);
        netId = new NetId("vluong");
        result = new User(netId, Gender.FEMALE, Certificate.PLUS4, "Delft", true);
    }

    @Test
    public void testParseRequest() {
        assertEquals(result, userService.parseRequest(userDetailModel, netId));
    }

    @Test
    public void createUserTest() throws Exception {
        User user = userService.parseRequest(userDetailModel, netId);
        String result = userService.createUser(user);
        verify(userRepository).save(user);
        assertEquals("Information of user is successfully saved in database", result);
    }

    @Test
    public void createUserTestException1() throws Exception {
        User user = userService.parseRequest(userDetailModel, netId);
        when(userRepository.save(user)).thenThrow(DataIntegrityViolationException.class);
        assertThatThrownBy(() -> {
            userService.createUser(user);
        }
        ).isInstanceOf(NetIdAlreadyInUseException.class);
    }

    @Test
    public void createUserTestException2() throws Exception {
        User user = userService.parseRequest(userDetailModel, netId);
        when(userRepository.save(user)).thenThrow(IllegalArgumentException.class);
        assertThatThrownBy(() -> {
                userService.createUser(user);
            }
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findUserTest() throws Exception {
        when(userRepository.findByNetId(netId)).thenReturn(result);
        User temp_result = userService.findUser(netId);
        verify(userRepository).findByNetId(netId);
        assertEquals(temp_result, result);
    }

    @Test
    public void findUserExceptionTest() throws Exception {
        when(userRepository.findByNetId(netId)).thenThrow(IllegalArgumentException.class);
        assertThatThrownBy(() -> {
            userService.findUser(netId);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void saveMessageTest() throws Exception {
        Message message = new Message("hminh", "vluong", 2L, "qwer", Position.COACH);
        when(messageRepository.save(message)).thenReturn(message);
        String result = userService.saveMessage(message);
        assertEquals("The message is successfully saved", result);
        verify(messageRepository).save(message);
    }

    @Test
    public void getNotifications() throws Exception {
        Message message = new Message("hminh", "vluong", 2L, "qwer", Position.COACH);
        Message message1 = new Message("mtan", "mkhoa", 3L, "qwer", Position.COACH);
        when(messageRepository.findMessagesByNetId(netId.getNetId())).thenReturn(List.of(message, message1));
        List<Message> result = userService.getNotifications(netId);
        verify(messageRepository).findMessagesByNetId(netId.getNetId());
        assertEquals(result, List.of(message, message1));
    }
}
