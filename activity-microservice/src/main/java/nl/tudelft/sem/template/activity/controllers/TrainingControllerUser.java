package nl.tudelft.sem.template.activity.controllers;

import nl.tudelft.sem.template.activity.domain.services.TrainingService;
import nl.tudelft.sem.template.activity.models.AcceptRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/training-user")
@RestController
public class TrainingControllerUser {

    private final transient TrainingService trainingService;

    /**
     * The controller of trainings.
     *
     * @param trainingService  the service provider of all activities
     */
    @Autowired
    public TrainingControllerUser(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    /**
     * A REST-mapping designed to accept / reject users from activities.
     *
     * @param model The request body
     * @return A string informing success
     */
    @PostMapping("/inform")
    public ResponseEntity<String> informUser(@RequestBody AcceptRequestModel model) {
        try {
            String status = trainingService.informUser(model);
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            return ResponseEntity.ok("Internal error when informing user.");
        }
    }
}
