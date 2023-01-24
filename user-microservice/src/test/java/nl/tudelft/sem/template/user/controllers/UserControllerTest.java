package nl.tudelft.sem.template.user.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;

import nl.tudelft.sem.template.user.authentication.AuthManager;
import nl.tudelft.sem.template.user.domain.Certificate;
import nl.tudelft.sem.template.user.domain.Gender;
import nl.tudelft.sem.template.user.domain.NetId;
import nl.tudelft.sem.template.user.domain.entities.User;
import nl.tudelft.sem.template.user.domain.models.UserDetailModel;
import nl.tudelft.sem.template.user.domain.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
        when(userService.findUser(new NetId("vluong"))).thenReturn(user);
        ResponseEntity responseEntity = userController.getDetailsOfUser();
        verify(userService).findUser(new NetId("vluong"));
        UserDetailModel userDetailModel = new UserDetailModel(Gender.MALE, "Delft", true, Certificate.PLUS4);
        assertEquals(userDetailModel, responseEntity.getBody());
    }

    @Test
    void createUserTestMutation() throws Exception {
        UserDetailModel userDetailModel = new UserDetailModel(Gender.FEMALE, "Delft", true, Certificate.PLUS4);
        when(userService.createUser(any())).thenThrow(Exception.class);
        Assertions.assertEquals(new ResponseEntity<>("Something went wrong in creating the user", HttpStatus.valueOf(200)),
                userController.createUser(userDetailModel));
    }


}
