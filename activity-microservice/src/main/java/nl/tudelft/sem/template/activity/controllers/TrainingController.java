package nl.tudelft.sem.template.activity.controllers;

import nl.tudelft.sem.template.activity.authentication.AuthManager;
import nl.tudelft.sem.template.activity.models.TrainingCreateModel;
import nl.tudelft.sem.template.activity.models.TrainingFindModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/training")
@RestController
public class TrainingController {

    private final transient AuthManager authManager;

    @Autowired
    public TrainingController(AuthManager authManager) {
        this.authManager = authManager;
    }

    @GetMapping("/create")
    void createTraining(@RequestBody TrainingCreateModel request) {
        //To be implemented
        return;
    }

    @PostMapping("/find")
    void createTraining(@RequestBody TrainingFindModel request) {
        //To be implemented
        return;
    }
}
