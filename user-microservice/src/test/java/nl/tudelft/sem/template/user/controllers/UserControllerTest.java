package nl.tudelft.sem.template.user.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;

import nl.tudelft.sem.template.user.authentication.AuthManager;
import nl.tudelft.sem.template.user.domain.Certificate;
import nl.tudelft.sem.template.user.domain.Gender;
import nl.tudelft.sem.template.user.domain.NetId;
import nl.tudelft.sem.template.user.domain.Position;
import nl.tudelft.sem.template.user.domain.entities.Message;
import nl.tudelft.sem.template.user.domain.entities.User;
import nl.tudelft.sem.template.user.domain.models.UserAcceptanceUpdateModel;
import nl.tudelft.sem.template.user.domain.models.UserDetailModel;
import nl.tudelft.sem.template.user.domain.models.UserJoinRequestModel;
import nl.tudelft.sem.template.user.domain.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserControllerTest {

    private UserController userController;
    private AuthManager authManager;
    private UserService userService;
    private User user;

    @BeforeEach
    public void setUp() {
        authManager = mock(AuthManager.class);
        userService = mock(UserService.class);
        userController = new UserController(authManager, userService);
        when(authManager.getNetId()).thenReturn("vluong");
        user = new User(new NetId("vluong"), Gender.MALE, Certificate.PLUS4, "Delft", true);
    }

    @Test
    void createUserTest() throws Exception {
        UserDetailModel userDetailModel = new UserDetailModel(Gender.FEMALE, "Delft", true, Certificate.PLUS4);
        ResponseEntity responseEntity = userController.createUser(userDetailModel);
        verify(userService).createUser(userService.parseRequest(userDetailModel, new NetId(authManager.getNetId())));
        assertEquals("Congratulations vluong, you have created your user", responseEntity.getBody());
    }

    @Test
    void getDetailsOfUser() throws Exception {
        when(authManager.getNetId()).thenReturn("vluong");
        when(userService.findUser("vluong")).thenReturn(user);
        ResponseEntity responseEntity = userController.getDetailsOfUser();
        verify(userService).findUser("vluong");
        UserDetailModel userDetailModel = new UserDetailModel(Gender.MALE, "Delft", true, Certificate.PLUS4);
        assertEquals(userDetailModel, responseEntity.getBody());
    }

    @Test
    void getNotificationsTest() throws Exception {
        Message message = new Message("hminh", "vluong", 2L, "qwer", Position.COACH);
        Message message1 = new Message("mtan", "mkhoa", 3L, "qwer", Position.COACH);
        when(authManager.getNetId()).thenReturn("vluong");
        when(userService.getNotifications(authManager.getNetId())).thenReturn(List.of(message, message1));
        userController.getNotifications();
        verify(userService).getNotifications(authManager.getNetId());
    }

    @Test
    void sendApplicationOfRequesterToOwnerTest() throws Exception {
        NetId owner = new NetId("vluong");
        UserJoinRequestModel userJoinRequestModel = new UserJoinRequestModel(owner, Position.COACH, 3L);
        userController.sendApplicationOfRequesterToOwner(userJoinRequestModel);
        verify(userService).saveMessage(any(Message.class));
    }

    @Test
    void sendDecisionOfOwnerToRequesterTest() throws Exception {
        NetId requester = new NetId("vluong");
        UserAcceptanceUpdateModel userAcceptanceUpdateModel = new UserAcceptanceUpdateModel(true, Position.COACH, requester);
        userController.sendDecisionOfOwnerToRequester(userAcceptanceUpdateModel);
        verify(userService).saveMessage(any(Message.class));
    }
}
