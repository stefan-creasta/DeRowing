package nl.tudelft.sem.template.boat.repositories;

import java.util.Optional;
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
    Optional<Boat> findById(Integer id);

    /**
     * Find a Boat given a name.
     *
     * @param name the name of the Boat
     * @return the boat that has this name
     */
    Optional<Boat> findByName(String name);
}
