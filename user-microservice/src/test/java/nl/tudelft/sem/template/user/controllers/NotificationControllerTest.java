package nl.tudelft.sem.template.user.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import nl.tudelft.sem.template.user.authentication.AuthManager;
import nl.tudelft.sem.template.user.domain.Certificate;
import nl.tudelft.sem.template.user.domain.Gender;
import nl.tudelft.sem.template.user.domain.NetId;
import nl.tudelft.sem.template.user.domain.Position;
import nl.tudelft.sem.template.user.domain.entities.Message;
import nl.tudelft.sem.template.user.domain.entities.User;
import nl.tudelft.sem.template.user.domain.models.UserAcceptanceUpdateModel;
import nl.tudelft.sem.template.user.domain.models.UserJoinRequestModel;
import nl.tudelft.sem.template.user.domain.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class NotificationControllerTest {

    private NotificationController notificationController;
    private AuthManager authManager;
    private UserService userService;
    private User user;

    /**
     * Setting up the test conditions.
     */
    @BeforeEach
    public void setUp() {
        authManager = mock(AuthManager.class);
        userService = mock(UserService.class);
        notificationController = new NotificationController(authManager, userService);
        when(authManager.getNetId()).thenReturn("vluong");
        user = new User(new NetId("vluong"), Gender.MALE, Certificate.PLUS4, "Delft", true);
    }

    @Test
    void getNotificationsTest() throws Exception {
        Message message = new Message("hminh", "vluong", 2L, "qwer", Position.COACH);
        Message message1 = new Message("mtan", "mkhoa", 3L, "qwer", Position.COACH);
        when(authManager.getNetId()).thenReturn("vluong");
        when(userService.getNotifications(authManager.getNetId())).thenReturn(List.of(message, message1));
        notificationController.getNotifications();
        verify(userService).getNotifications(authManager.getNetId());
    }

    @Test
    void sendApplicationOfRequesterToOwnerTest() throws Exception {
        NetId owner = new NetId("vluong");
        UserJoinRequestModel userJoinRequestModel = new UserJoinRequestModel(owner, Position.COACH, 3L);
        notificationController.sendApplicationOfRequesterToOwner(userJoinRequestModel);
        verify(userService).saveMessage(any(Message.class));
    }

    @Test
    void sendDecisionOfOwnerToRequesterTest() throws Exception {
        NetId requester = new NetId("vluong");
        UserAcceptanceUpdateModel userAcceptanceUpdateModel = new UserAcceptanceUpdateModel(true, Position.COACH, requester);
        notificationController.sendDecisionOfOwnerToRequester(userAcceptanceUpdateModel);
        verify(userService).saveMessage(any(Message.class));
    }
}
