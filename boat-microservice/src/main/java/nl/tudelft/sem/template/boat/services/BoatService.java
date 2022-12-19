package nl.tudelft.sem.template.boat.services;

import java.util.Optional;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.boat.domain.Boat;
import nl.tudelft.sem.template.boat.domain.Type;
import nl.tudelft.sem.template.boat.models.BoatCreateModel;
import nl.tudelft.sem.template.boat.repositories.BoatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class BoatService extends RestService {
    @Autowired
    private BoatRepository boatRepository;

    public BoatService(BoatRepository boatRepository) {
        this.boatRepository = boatRepository;
    }

    /**
     * Method parse a requestBody.

     * @param request the request body
     * @return a new Boat
     */
    public Boat parseRequest(BoatCreateModel request) {
        String name = request.getName();
        Type type = request.getType();
        int cox = request.getCox();
        int coach = request.getCoach();
        int port = request.getPort();
        int starboard = request.getStarboard();
        int sculling = request.getSculling();

        return new Boat(name, type, cox, coach, port, starboard, sculling);
    }


    /**
     * Method to create and persist a new Boat.

     * @param request the request body
     * @return a new Boat
     * @throws Exception the already using this netId exception
     */
    public Boat createBoat(BoatCreateModel request) throws Exception {
        try {
            Boat boat = parseRequest(request);
            boatRepository.save(boat);
            return boat;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("Something went wrong in createBoat");
        }
    }
    /**
     * A method to find a boat by ID from the database.

     * @param id the ID of the boat
     * @return the Boat with that id
     * @throws Exception the boat not found exception
     */
    public Boat findBoatById(Integer id) throws Exception {
        try {
            Optional<Boat> boatOptional = boatRepository.findById(id);

            return boatOptional.orElse(null);
        } catch (Exception e) {
            throw new Exception("Something went wrong in findCompetitions");
        }
    }


    /**
     * A method to find a boat by name from the database.

     * @param name the name of the boat
     * @return the Boat with that name
     * @throws Exception the boat not found exception
     */
    public Boat findBoatByName(String name) throws Exception {
        try {
            Optional<Boat> boatOptional = boatRepository.findByName(name);

            return boatOptional.orElse(null);
        } catch (Exception e) {
            throw new Exception("Something went wrong in findCompetitions");
        }
    }

    /**
     * A method which updates a certain boat, when a rower has been inserted or removed.

     * @param boat the updated boat
     */
    public void updateBoat(Boat boat) {
        boatRepository.save(boat);
    }
}
