package nl.tudelft.sem.template.activity.controllers;

import nl.tudelft.sem.template.activity.authentication.AuthManager;
import nl.tudelft.sem.template.activity.domain.ActivityService;
import nl.tudelft.sem.template.activity.domain.Competition;
import nl.tudelft.sem.template.activity.domain.GenderConstraint;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.models.CompetitionCreateModel;
import nl.tudelft.sem.template.activity.models.CompetitionFindModel;
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

    private final transient ActivityService activityService;

    /**
     * Instantiates a new controller.
     *
     * @param authManager     Spring Security component used to authenticate and authorize the user
     * @param activityService the service provider of all activities
     */
    @Autowired
    public CompetitionController(AuthManager authManager, ActivityService activityService) {
        this.authManager = authManager;
        this.activityService = activityService;
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
            NetId netId = new NetId(authManager.getNetId());
            String competitionName = request.getCompetitionName();
            long boatId = request.getBoatId();
            long startTime = request.getStartTime();
            boolean allowAmateurs = request.isAllowAmateurs();
            boolean singleOrganization = request.isSingleOrganization();
            GenderConstraint genderConstraint = request.getGenderConstraint();
            activityService.createCompetition(netId, competitionName, boatId, startTime,
                    allowAmateurs, genderConstraint, singleOrganization);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok("Done! The competition " + request.getCompetitionName()
                + " is created by " + authManager.getNetId());
    }

    /**
     * the method to find a specific competition.
     *
     * @param request   the request body of the competition finding
     * @return a competition information
     * @throws Exception a competition not found exception
     */
    @GetMapping("/find")
     public ResponseEntity<String> findCompetitions(@RequestBody CompetitionFindModel request) throws Exception {
        NetId netId = new NetId(authManager.getNetId());
        Competition target = activityService.findCompetition(netId);
        return ResponseEntity.ok("The competition created by " + authManager.getNetId()
                + " is found. Here is the competition: " + target.toString());
    }
}
