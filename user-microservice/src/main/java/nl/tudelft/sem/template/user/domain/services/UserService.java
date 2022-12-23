package nl.tudelft.sem.template.user.domain.services;

import nl.tudelft.sem.template.user.domain.Certificate;
import nl.tudelft.sem.template.user.domain.Gender;
import nl.tudelft.sem.template.user.domain.NetId;
import nl.tudelft.sem.template.user.domain.entities.Message;
import nl.tudelft.sem.template.user.domain.entities.User;
import nl.tudelft.sem.template.user.domain.exceptions.NetIdAlreadyInUseException;
import nl.tudelft.sem.template.user.domain.models.UserDetailModel;
import nl.tudelft.sem.template.user.domain.repositories.MessageRepository;
import nl.tudelft.sem.template.user.domain.repositories.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final transient UserRepository userRepository;
    private final transient MessageRepository messageRepository;

    public UserService(UserRepository userRepository, MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    /**
     * Method parse a RequestBody.
     *
     * @param request the request body
     * @param netId   the netId of the requester
     * @return a new User
     */
    public User parseRequest(UserDetailModel request, NetId netId) {
        Gender gender = request.getGender();
        Certificate certificate = request.getCertificate();
        String organization = request.getOrganization();
        boolean amateur = request.isAmateur();

        return new User(netId, gender, certificate, organization, amateur);
    }

    /**
     * Method to create and persist a new User.
     *
     * @return a new User
     * @throws Exception the already using the NetId exception
     */
    public String createUser(User user) throws Exception {
        try {
            userRepository.save(user);
            return "Information of user is successfully saved in database";
        } catch (DataIntegrityViolationException e) {
            throw new NetIdAlreadyInUseException(user.getNetId());
        } catch (Exception e) {
            throw new Exception("Something went wrong in createUser");
        }
    }

    /**
     * A method to find a user from the database.
     *
     * @param netId the netId of the user
     * @return the user to be found
     * @throws Exception the NetId not found exception
     */
    public User findUser(String netId) throws Exception {
        try {
            return userRepository.findByNetId(netId);
        } catch (Exception e) {
            throw new Exception("Something went wrong in findUser");
        }
    }

    /**
     * Saves a message in the message database.
     *
     * @param message the incoming message
     * @return a String indicating whether the message is saved or not
     * @throws Exception a NetId already in use exception
     */
    public String saveMessage(Message message) throws Exception {
        try {
            messageRepository.save(message);
            return "The message is successfully saved";
        } catch (Exception e) {
            throw new Exception("Something went wrong when saving this message");
        }
    }

    /**
     * Gets the messages addressed to a particular NetId (like an inbox).
     *
     * @param netId NetId to get messages for
     * @return the list of messages (inbox)
     * @throws Exception the NetId already in
     */
    public List<Message> getNotifications(String netId) throws Exception {
        try {
            return messageRepository.findMessagesByNetId(netId);
        }
        catch (Exception e) {
            throw new Exception("Can not retrieve the user's messages");
        }
    }
}
