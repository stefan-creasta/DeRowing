package nl.tudelft.sem.template.activity.controllers;

import nl.tudelft.sem.template.activity.authentication.AuthManager;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.services.CompetitionService;
import nl.tudelft.sem.template.activity.models.AcceptRequestModel;
import nl.tudelft.sem.template.activity.models.CompetitionCreateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Hello World example controller.
 * <p>
 * This controller shows how you can extract information from the JWT token.
 * </p>
 */
@RequestMapping("/competition")
@RestController
public class CompetitionController {

    private final transient AuthManager authManager;

    private final transient CompetitionService competitionService;

    /**
     * Instantiates a new controller.
     *
     * @param authManager     Spring Security component used to authenticate and authorize the user
     * @param competitionService the service provider of all activities
     */
    @Autowired
    public CompetitionController(AuthManager authManager, CompetitionService competitionService) {
        this.authManager = authManager;
        this.competitionService = competitionService;
    }

    /**
     * Gets example by id.
     *
     * @return the example found in the database with the given id
     */
    @GetMapping("/hello")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello " + authManager.getNetId());
    }

    /**
     * the method to create a competition.
     *
     * @param request   a competition create model, which contains all information about the competition
     * @return a competition
     * @throws Exception an already used NetId exception
     */
    @PostMapping("/create")
    public ResponseEntity<String> createCompetition(@RequestBody CompetitionCreateModel request) throws Exception {
        try {
            competitionService.createCompetition(request, new NetId(authManager.getNetId()));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok("Done! The competition " + request.getCompetitionName()
                + " is created by " + authManager.getNetId());
    }


    /**
     * A REST-mapping designed to accept / reject users from activities.
     *
     * @param model The request body
     * @return A string informing success
     */
    @PostMapping("/inform")
    public ResponseEntity<String> informUser(@RequestBody AcceptRequestModel model) {
        boolean success = competitionService.informUser(model, authManager.getNetId());
        if (success) {
            return ResponseEntity.ok("User is informed");
        } else {
            return null;
        }
    }
}
