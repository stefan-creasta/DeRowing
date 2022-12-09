package nl.tudelft.sem.template.activity.controllers;

import nl.tudelft.sem.template.activity.authentication.AuthManager;
import nl.tudelft.sem.template.activity.domain.*;
import nl.tudelft.sem.template.activity.models.CompetitionCreateModel;
import nl.tudelft.sem.template.activity.models.CompetitionFindModel;
import nl.tudelft.sem.template.activity.models.TrainingCreateModel;
import nl.tudelft.sem.template.activity.models.TrainingFindModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/training")
@RestController
public class TrainingController {

    private final transient AuthManager authManager;

    private final transient ActivityService activityService;

    /**
     * The controller of trainings.
     *
     * @param authManager      Spring Security component used to authenticate and authorize the user
     * @param activityService  the service provider of all activities
     */
    @Autowired
    public TrainingController(AuthManager authManager, ActivityService activityService) {
        this.authManager = authManager;
        this.activityService = activityService;
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
            NetId netId = new NetId(authManager.getNetId());
            String trainingName = request.getTrainingName();
            long boatId = request.getBoatId();
            long startTime = request.getStartTime();

            activityService.createTraining(netId, trainingName, boatId, startTime);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok("Done! The Training " + request.getTrainingName()
                + " is created by " + authManager.getNetId());
    }

    /**
     * the method to find a specific training.
     *
     * @param request   the request body of the training finding
     * @return a training information
     * @throws Exception a training not found exception
     */
    @GetMapping("/find")
    public ResponseEntity<String> findTraining(@RequestBody TrainingFindModel request) throws Exception {
        NetId netId = new NetId(authManager.getNetId());
        Training target = activityService.findTraining(netId);
        return ResponseEntity.ok("The training created by " + authManager.getNetId()
                + " is found. Here is the training: " + target.toString());
    }
}
