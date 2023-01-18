package nl.tudelft.sem.template.activity.controllers;

import nl.tudelft.sem.template.activity.authentication.AuthInterface;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.services.TrainingService;
import nl.tudelft.sem.template.activity.models.ActivityCancelModel;
import nl.tudelft.sem.template.activity.models.TrainingCreateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/training-create")
@RestController
public class TrainingControllerCreate {

    private final transient AuthInterface authManager;

    private final transient TrainingService trainingService;

    /**
     * The controller of trainings.
     *
     * @param authManager      Spring Security component used to authenticate and authorize the user
     * @param trainingService  the service provider of all activities
     */
    @Autowired
    public TrainingControllerCreate(AuthInterface authManager, TrainingService trainingService) {
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
            return ResponseEntity.ok("Internal error when creating training.");
        }
    }

    /**
     * The method to delete a specified training.
     *
     * @param activityCancelModel the id of the training to be deleted.
     * @return a message containing the information about the canceling.
     */
    @PostMapping("/cancel")
    public ResponseEntity<String> cancelTraining(@RequestBody ActivityCancelModel activityCancelModel) {
        try {
            String response = trainingService.deleteTraining(activityCancelModel.getId(), authManager.getNetId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.ok("Internal error when canceling training.");
        }

    }
}
