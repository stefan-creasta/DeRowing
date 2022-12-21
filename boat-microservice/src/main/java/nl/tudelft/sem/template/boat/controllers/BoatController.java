package nl.tudelft.sem.template.boat.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.tudelft.sem.template.boat.authentication.AuthManager;
import nl.tudelft.sem.template.boat.domain.Boat;
import nl.tudelft.sem.template.boat.domain.NetId;
import nl.tudelft.sem.template.boat.domain.Position;
import nl.tudelft.sem.template.boat.models.BoatCreateModel;
import nl.tudelft.sem.template.boat.models.BoatDeleteModel;
import nl.tudelft.sem.template.boat.models.BoatEmptyPositionsModel;
import nl.tudelft.sem.template.boat.models.BoatRowerEditModel;
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
     * the method to create a boat.
     *
     * @param request a boat create model, which contains all information about the boat
     * @return a boat
     * @throws Exception the boat cannot be created
     */
    @PostMapping("/create")
    public ResponseEntity<BoatDeleteModel> createBoat(@RequestBody BoatCreateModel request) throws Exception {
        int id = -1;
        ResponseEntity.BodyBuilder bb;
        try {
            Boat returnedBoat = boatService.createBoat(request);
            id = returnedBoat.getId();
            bb = ResponseEntity.status(HttpStatus.OK);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return bb.body(new BoatDeleteModel(id));
    }

    /**
     * the method to insert a certain user in a boat for a specific position.
     *
     * @param request the request body of the insertion
     * @return information regarding the insertion
     * @throws Exception the user's netId cannot be inserted
     */
    @PostMapping("/insert")
    public ResponseEntity<String> insertRower(@RequestBody BoatRowerEditModel request) throws Exception {
        try {
            int id = (int) request.getBoatId();
            Boat target = boatService.findBoatById(id);
            NetId netId = request.getNetId();
            target.addRowerToPosition(request.getPosition(), netId);
            boatService.updateBoat(target);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok("Done! A rower has been inserted in the boat with id " + request.getBoatId()
                + " by " + authManager.getNetId());
    }

    /**
     * the method to remove a certain user in a boat for a specific position.
     *
     * @param request the request body of the removal
     * @return information regarding the removal
     * @throws Exception the user's netId cannot be removed
     */
    @PostMapping("/remove")
    public ResponseEntity<String> removeRower(@RequestBody BoatRowerEditModel request) throws Exception {
        try {
            int id = (int) request.getBoatId();
            Boat target = boatService.findBoatById(id);
            NetId netId = request.getNetId();
            target.removeRower(netId);
            boatService.updateBoat(target);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok("Done! A rower has been removed in the boat with id " + request.getBoatId()
                + " by " + authManager.getNetId());
    }

    /**
     * the method to delete a certain boat.
     *
     * @param request the request containing the boat's id
     * @return a string which contains information regarding the deletion of the boat
     * @throws Exception the boat could not be deleted
     */
    @PostMapping("/delete")
    public ResponseEntity<String> deleteBoat(@RequestBody BoatDeleteModel request) throws Exception {
        try {
            int id = (int) request.getBoatId();
            Boat target = boatService.findBoatById(id);
            boatService.deleteBoat(target);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok("Done! A boat has been removed by " + authManager.getNetId());
    }

    /**
     * Method for finding the boats that have certain positions still available.
     *
     * @param request the request containing the boat's id
     * @return a string which contains information regarding the deletion of the boat
     * @throws Exception the boat could not be deleted
     */
    @PostMapping("/findEmptyPositions")
    public ResponseEntity<String> findBoatsByEmptyPositions(@RequestBody BoatEmptyPositionsModel request) throws Exception {
        // retrieve the list of required positions from the request
        List<Position> positionList = request.getPositionList();

        // transform the list into a frequency map so that it can be processed by boatService
        Map<Position, Integer> requiredPositions = new HashMap<>();
        for (Position position : positionList) {
            if (requiredPositions.get(position) == null) {
                requiredPositions.put(position, 0);
            } else {
                requiredPositions.put(position, requiredPositions.get(position) + 1);
            }
        }

        List<Boat> boats = boatService.findBoatsByEmptyPositions(requiredPositions);
        return ResponseEntity.ok("The list of boats requested by " + authManager.getNetId()
                + " is found. Here is the list of boats: " + boats.toString());
    }
}
