package nl.tudelft.sem.template.user.domain.services;

import nl.tudelft.sem.template.user.domain.Certificate;
import nl.tudelft.sem.template.user.domain.Gender;
import nl.tudelft.sem.template.user.domain.NetId;
import nl.tudelft.sem.template.user.domain.entities.User;
import nl.tudelft.sem.template.user.domain.exceptions.NetIdAlreadyInUseException;
import nl.tudelft.sem.template.user.domain.models.UserDetailModel;
import nl.tudelft.sem.template.user.domain.repositories.MessageRepository;
import nl.tudelft.sem.template.user.domain.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.mockito.internal.util.MockUtil.isMock;

public class UserServiceTest {
    private transient UserRepository userRepository;
    private transient MessageRepository messageRepository;
    private transient  UserService userService;
    UserDetailModel userDetailModel;
    NetId netId;
    User result = new User(netId, Gender.FEMALE, Certificate.PLUS4, "Delft", true);

    @BeforeEach
    public void setup() {
        userRepository = mock(UserRepository.class);
        messageRepository = mock(MessageRepository.class);
        userService = new UserService(userRepository, messageRepository);
        userDetailModel = new UserDetailModel(Gender.FEMALE, "Delft", true, Certificate.PLUS4);
        netId = new NetId("vluong");

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
    public void createUserTestExceptions() throws Exception {
        User user = userService.parseRequest(userDetailModel, netId);
        when(userService.createUser(user)).thenThrow(DataAccessException.class);
        assertThatThrownBy(() -> {
            userService.createUser(user);
        }
        ).isInstanceOf(NetIdAlreadyInUseException.class);

//        when(userRepository.save(user)).thenThrow(Exception.class);
//        assertThatThrownBy(() -> {
//            userService.createUser(user);
//        }).isInstanceOf(Exception.class);
    }
}
