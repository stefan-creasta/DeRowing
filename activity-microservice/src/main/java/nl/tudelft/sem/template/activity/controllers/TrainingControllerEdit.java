package nl.tudelft.sem.template.activity.controllers;

import nl.tudelft.sem.template.activity.authentication.AuthInterface;
import nl.tudelft.sem.template.activity.domain.entities.Training;
import nl.tudelft.sem.template.activity.domain.services.TrainingService;
import nl.tudelft.sem.template.activity.models.JoinRequestModel;
import nl.tudelft.sem.template.activity.models.PositionEntryModel;
import nl.tudelft.sem.template.activity.models.TrainingEditModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RequestMapping("/training-edit")
@RestController
public class TrainingControllerEdit {

    private final transient AuthInterface authManager;

    private final transient TrainingService trainingService;

    /**
     * The controller of trainings.
     *
     * @param authManager      Spring Security component used to authenticate and authorize the user
     * @param trainingService  the service provider of all activities
     */
    @Autowired
    public TrainingControllerEdit(AuthInterface authManager, TrainingService trainingService) {
        this.authManager = authManager;
        this.trainingService = trainingService;
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
            return ResponseEntity.ok("Internal error when joining training.");
        }
    }

    /**
     * A method to edit the existing training.
     *
     * @param request The information that the training should have
     * @return a message containing the information about the editing
     */
    @PostMapping("/edit")
    public ResponseEntity<String> editTraining(@RequestBody TrainingEditModel request) {
        try {
            String response = trainingService.editTraining(request, authManager.getNetId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.ok("Internal error when editing training.");
        }
    }

    /**
     * The method to get all trainings.
     *
     * @param model the requestBody of the user
     * @return a list of matching trainings
     */
    @PostMapping("/find")
    public ResponseEntity<List<Training>> getTrainings(@RequestBody PositionEntryModel model) {
        try {
            List<Training> result = trainingService.getSuitableCompetition(model.getPosition());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There is no competition that you are suitable for", e);
        }
    }
}
