package nl.tudelft.sem.template.user.controllers;

import nl.tudelft.sem.template.user.authentication.AuthManager;
import nl.tudelft.sem.template.user.domain.entities.Message;
import nl.tudelft.sem.template.user.domain.models.UserAcceptanceUpdateModel;
import nl.tudelft.sem.template.user.domain.models.UserJoinRequestModel;
import nl.tudelft.sem.template.user.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequestMapping("/notifications")
@RestController
public class NotificationController {

    private final transient AuthManager authManager;

    private final transient UserService userService;

    /**
     * Instantiates a new controller.
     *
     * @param authManager Spring Security component used to authenticate and authorize the user
     * @param userService The user service containing method implementations
     */
    @Autowired
    public NotificationController(AuthManager authManager, UserService userService) {
        this.authManager = authManager;
        this.userService = userService;
    }

    /**
     * Gets the list of messages (notifications) for the user.
     *
     * @return a list of messages that the user received
     * @throws Exception an already used NetId exception
     */
    @GetMapping("/inbox")
    public ResponseEntity<List<Message>> getNotifications() {
        try {
            List<Message> notifications = userService.getNotifications(authManager.getNetId());
            ResponseEntity.BodyBuilder bb = ResponseEntity.status(HttpStatus.OK);
            return bb.body(notifications);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Saves the request from a participant to the activity owner to join an activity, to the message database.
     *
     * @param userJoinRequest the request body of the join request
     * @return a String that informs that the message is successfully saved in the message database
     * @throws Exception an already used NetId exception
     */
    @PostMapping("/join")
    public ResponseEntity<String> sendApplicationOfRequesterToOwner(@RequestBody UserJoinRequestModel
                                                                            userJoinRequest) {
        try {
            String content = authManager.getNetId() + " wants to join this competition/training session. "
                    + "They want to join for position " + userJoinRequest.getPosition().toString();
            Message message = new Message(
                    userJoinRequest.getOwner().getNetId(),
                    authManager.getNetId(),
                    userJoinRequest.getActivityId(),
                    content,
                    userJoinRequest.getPosition()
            );
            userService.saveMessage(message);
            ResponseEntity.BodyBuilder bb = ResponseEntity.status(HttpStatus.OK);
            return bb.body("The message is successfully saved");
        } catch (Exception e) {
            ResponseEntity.BodyBuilder bb = ResponseEntity.status(HttpStatus.OK);
            return bb.body("Something went wrong in sending the application of participant to activity owner");
        }
    }

    /**
     * Saves the decision made by the activity owner to the message database.
     *
     * @param userAcceptanceUpdateModel the request body of the decision made
     * @return a String that informs that the message is successfully saved in the message database
     */


    @PostMapping("/update")
    public ResponseEntity<String> sendDecisionOfOwnerToRequester(@RequestBody UserAcceptanceUpdateModel
                                                                         userAcceptanceUpdateModel) {
        try {
            userService.saveAcceptMessage(userAcceptanceUpdateModel, authManager.getNetId());
            return ResponseEntity.status(HttpStatus.OK).body("The message is successfully saved");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body("Something went wrong");
        }
    }

}

