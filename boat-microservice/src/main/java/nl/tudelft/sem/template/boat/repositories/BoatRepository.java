package nl.tudelft.sem.template.boat.repositories;

import nl.tudelft.sem.template.boat.domain.Boat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoatRepository extends JpaRepository<Boat, Integer> {
    /**
     * Find a Boat given an ID.
     *
     * @param id the ID of the Boat
     * @return the boat that has this ID
     */
    Boat findByBoatId(Integer id);

    /**
     * Find a Boat given a name.
     *
     * @param name the name of the Boat
     * @return the boat that has this name
     */
    Boat findByBoatName(String name);
}
