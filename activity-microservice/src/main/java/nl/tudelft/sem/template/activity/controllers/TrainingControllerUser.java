package nl.tudelft.sem.template.activity.controllers;

import nl.tudelft.sem.template.activity.domain.services.TrainingServiceUserSide;
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

    private final transient TrainingServiceUserSide trainingService;

    /**
     * The controller of trainings.
     *
     * @param trainingService  the service provider of all activities
     */
    @Autowired
    public TrainingControllerUser(TrainingServiceUserSide trainingService) {
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
        String status = trainingService.informUser(model);
        return ResponseEntity.ok(status);
    }
}
