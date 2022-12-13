package nl.tudelft.sem.template.boat.controllers;

import nl.tudelft.sem.template.boat.authentication.AuthManager;
import nl.tudelft.sem.template.boat.domain.Boat;
import nl.tudelft.sem.template.boat.models.BoatCreateModel;
import nl.tudelft.sem.template.boat.models.BoatFindModel;
import nl.tudelft.sem.template.boat.services.BoatService;
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
@RequestMapping("/boat")
@RestController
public class BoatController {

    private final transient AuthManager authManager;

    private final transient BoatService boatService;

    /**
     * Instantiates a new controller.
     *
     * @param authManager Spring Security component used to authenticate and authorize the user
     * @param boatService the service provider of all boats
     */
    @Autowired
    public BoatController(AuthManager authManager, BoatService boatService) {
        this.authManager = authManager;
        this.boatService = boatService;
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
    public ResponseEntity<String> createCompetition(@RequestBody BoatCreateModel request) throws Exception {
        try {
            boatService.createBoat(request);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok("Done! The boat of type " + request.getType()
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
    public ResponseEntity<String> findBoatByName(@RequestBody BoatFindModel request) throws Exception {
        String name = request.getName();
        Boat target = boatService.findBoatByName(name);
        return ResponseEntity.ok("The competition created by " + authManager.getNetId()
                + " is found. Here is the competition: " + target.toString());
    }
}
