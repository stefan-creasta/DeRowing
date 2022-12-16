package nl.tudelft.sem.template.activity.controllers;

import nl.tudelft.sem.template.activity.authentication.AuthManager;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.services.TrainingService;
import nl.tudelft.sem.template.activity.models.AcceptRequestModel;
import nl.tudelft.sem.template.activity.models.JoinRequestModel;
import nl.tudelft.sem.template.activity.models.TrainingCreateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/training")
@RestController
public class TrainingController {

    private final transient AuthManager authManager;

    private final transient TrainingService trainingService;

    /**
     * The controller of trainings.
     *
     * @param authManager      Spring Security component used to authenticate and authorize the user
     * @param trainingService  the service provider of all activities
     */
    @Autowired
    public TrainingController(AuthManager authManager, TrainingService trainingService) {
        this.authManager = authManager;
        this.trainingService = trainingService;
    }

    /**
     * the method to create a training.
     *
     * @param request   a training create model, which contains all information about the competition
     * @return a training
     * @throws Exception an already used NetId exception
     */
    @PostMapping("/create")
    public ResponseEntity<String> createTraining(@RequestBody TrainingCreateModel request) throws Exception {
        try {
            String response = trainingService.createTraining(request, new NetId(authManager.getNetId()));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * A REST-mapping designed to accept / reject users from activities.
     *
     * @param model The request body
     * @return A string informing success
     */
    @PostMapping("/inform")
    public ResponseEntity<String> informUser(@RequestBody AcceptRequestModel model) {
        boolean success = trainingService.informUser(model, authManager.getNetId());
        if (success) {
            return ResponseEntity.ok("User is informed");
        } else {
            return null;
        }
    }

    /**
     * A REST-mapping designed to join users to activities.
     *
     * @param request the join request
     * @return status of request
     */
    @PostMapping("/join")
    public ResponseEntity<String> joinTraining(@RequestBody JoinRequestModel request) {
        try {
            String response = trainingService.joinTraining(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
