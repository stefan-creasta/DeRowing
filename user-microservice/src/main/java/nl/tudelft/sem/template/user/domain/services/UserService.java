package nl.tudelft.sem.template.user.domain.services;

import nl.tudelft.sem.template.user.domain.Certificate;
import nl.tudelft.sem.template.user.domain.Gender;
import nl.tudelft.sem.template.user.domain.NetId;
import nl.tudelft.sem.template.user.domain.entities.User;
import nl.tudelft.sem.template.user.domain.exceptions.NetIdAlreadyInUseException;
import nl.tudelft.sem.template.user.domain.models.UserDetailModel;
import nl.tudelft.sem.template.user.domain.repositories.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final transient UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Method parse a RequestBody.
     *
     * @param request the request body
     * @param netId the netId of the requester
     * @return a new User
     */
    public User parseRequest(UserDetailModel request, NetId netId) {
        Gender gender = request.getGender();
        Certificate certificate = request.getCertificate();
        String organization = request.getOrganization();
        boolean amateur = request.isAmateur();

        User user = new User(netId, gender, certificate, organization, amateur);
        return user;
    }

    /**
     * Method to create and persist a new User.
     *
     * @param request the request body
     * @param netId the netId of the requester
     * @return a new User
     * @throws Exception the already using the NetId exception
     */
    public User createUser(UserDetailModel request, NetId netId) throws Exception {
        try {
            User user = parseRequest(request, netId);
            userRepository.save(user);
            return user;
        } catch (DataIntegrityViolationException e) {
            throw new NetIdAlreadyInUseException(netId);
        } catch (Exception e) {
            throw new Exception("Something went wrong in createCompetition");
        }
    }

    /**
     * A method to find a user from the database.

     * @param netId the netId of the requester
     * @return the Competition of the requester
     * @throws Exception the competition not found exception
     */
    public User findUser(NetId netId) throws Exception {
        try {
            return userRepository.findByNetId(netId);
        } catch (Exception e) {
            throw new Exception("Something went wrong in findUser");
        }
    }
}
