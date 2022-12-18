package nl.tudelft.sem.template.user.controllers;

import nl.tudelft.sem.template.user.authentication.AuthManager;
import nl.tudelft.sem.template.user.domain.NetId;
import nl.tudelft.sem.template.user.domain.entities.Message;import nl.tudelft.sem.template.user.domain.entities.User;
import nl.tudelft.sem.template.user.domain.models.UserDetailModel;
import nl.tudelft.sem.template.user.domain.models.UserFindModel;
import nl.tudelft.sem.template.user.domain.services.UserService;
import nl.tudelft.sem.template.user.models.UserAcceptanceUpdateModel;import nl.tudelft.sem.template.user.models.UserJoinRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;import java.util.List;

/**
 * Hello World example controller.
 * <p>
 * This controller shows how you can extract information from the JWT token.
 * </p>
 */
@RestController
public class UserController {

    private final transient AuthManager authManager;

    private final transient UserService userService;

    /**
     * Instantiates a new controller.
     *
     * @param authManager Spring Security component used to authenticate and authorize the user
     */
    @Autowired
    public UserController(AuthManager authManager, UserService userService) {
        this.authManager = authManager;
        this.userService = userService;
    }

    /**
     * Gets user by id.
     *
     * @return the user found in the database with the given id
     */
    @GetMapping("/hello")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello " + authManager.getNetId());
    }

    /**
     * the method to create a User.
     *
     * @param request   a user create model, which contains all information about the user
     * @return a user
     * @throws Exception an already used NetId exception
     */
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserDetailModel request) throws Exception {
        try {
            userService.createUser(request, new NetId(authManager.getNetId()));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok("Congratulations " + authManager.getNetId() + ", you have created your user");
    }

    /**
     * the method to find a specific user.
     *
     * @param request   the request body of the user finding
     * @return a user information
     * @throws Exception a competition not found exception
     */
    @GetMapping("/find")
    public ResponseEntity<String> findCompetitions(@RequestBody UserFindModel request) throws Exception {
        NetId netId = new NetId(authManager.getNetId());
        User target = userService.findUser(netId);
        return ResponseEntity.ok("The user created by " + authManager.getNetId()
                + " is found. Here is the user: " + target.toString());
    }

	@PostMapping("/join")
	public ResponseEntity<String> saveMessage(@RequestBody UserJoinRequestModel userJoinRequest) throws Exception {
		String content = authManager.getNetId() + " wants to join this competition/training session";
		userService.saveMessage(userJoinRequest.getOwner(),
							new NetId(authManager.getNetId()),
							userJoinRequest.getActivityId(),
							content,
							userJoinRequest.getPosition());
		return ResponseEntity.ok("The message is successfully saved");
	}

	@GetMapping("/notifications")
	public ResponseEntity<List<Message>> getNotifications() throws Exception{
		List<Message> notifications = userService.getNotifications(new NetId(authManager.getNetId()));
		return ResponseEntity.ok(notifications);
	}

	@GetMapping("/getdetails")
	public ResponseEntity<User> getUserDetails() throws Exception {
		User user = userService.getUserDetail(new NetId(authManager.getNetId()));
		return ResponseEntity.ok(user);
	}

	@PostMapping("/update")
	public ResponseEntity<String> sendDecisionOfOwnerToRequester(@RequestBody UserAcceptanceUpdateModel
											userAcceptanceUpdateModel) throws Exception {
		String content = "";
		if(userAcceptanceUpdateModel.isAccepted()) {
			content += authManager.getNetId() + " accepted your request";
		}
		else {
			content += authManager.getNetId() + " did not accept your request";
		}

		userService.saveMessage(userAcceptanceUpdateModel.getEventRequester(),
								new NetId(authManager.getNetId()),
								0,
								content,
								userAcceptanceUpdateModel.getPosition());
		return ResponseEntity.ok("The message is successfully saved");
	}
}
