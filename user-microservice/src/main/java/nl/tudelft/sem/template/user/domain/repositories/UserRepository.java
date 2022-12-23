package nl.tudelft.sem.template.user.domain.repositories;

import nl.tudelft.sem.template.user.domain.NetId;
import nl.tudelft.sem.template.user.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    /**
     * Find a user by using their NetId.
     *
     * @param netId the netId of the User
     * @return the user represented by the NetId
     */
    User findByNetId(String netId);

    /**
     * Check the usage of the provided netId.
     *
     * @param netId the netId to be searched
     * @return a boolean value which shows whether the netId is used or not
     */
    boolean existsByNetId(NetId netId);
}
