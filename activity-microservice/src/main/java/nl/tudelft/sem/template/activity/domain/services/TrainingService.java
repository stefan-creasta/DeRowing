package nl.tudelft.sem.template.activity.domain.services;

import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Position;
import nl.tudelft.sem.template.activity.domain.entities.Training;
import nl.tudelft.sem.template.activity.domain.events.EventPublisher;
import nl.tudelft.sem.template.activity.domain.exceptions.NetIdAlreadyInUseException;
import nl.tudelft.sem.template.activity.domain.repositories.TrainingRepository;
import nl.tudelft.sem.template.activity.models.AcceptRequestModel;
import nl.tudelft.sem.template.activity.models.JoinRequestModel;
import nl.tudelft.sem.template.activity.models.TrainingCreateModel;
import nl.tudelft.sem.template.activity.models.UserDataRequestModel;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class TrainingService extends ActivityService {

    private final transient EventPublisher eventPublisher;
    private final transient UserRestService userRestService;
    private final transient TrainingRepository trainingRepository;
    private final transient BoatRestService boatRestService;

    /**
     * Instantiates a new CompetitionService.
     *
     * @param eventPublisher the event publisher for user acceptance
     * @param userRestService the user rest service
     * @param trainingRepository the repository for trainings
     * @param boatRestService the boat rest service
     */
    public TrainingService(EventPublisher eventPublisher, UserRestService userRestService,
                           TrainingRepository trainingRepository, BoatRestService boatRestService) {
        this.eventPublisher = eventPublisher;
        this.userRestService = userRestService;
        this.trainingRepository = trainingRepository;
        this.boatRestService = boatRestService;
    }

    /**
     * Method to parse the TrainingCreateModel.
     *
     * @param request the TrainingCreateModel aka the request body
     * @param netId the netId of the requester
     * @return a new Training
     */
    public Training parseRequest(TrainingCreateModel request, NetId netId, long boatId) {
        return new Training(netId, request.getTrainingName(), boatId,
                request.getStartTime(), request.getNumPeople(), request.getType());
    }


    /**
     * Create a new training.
     *
     * @param netId the id of the owner
     * @param request the request body
     * @return a new training
     * @throws Exception the already using this netId exception
     */
    public String createTraining(TrainingCreateModel request, NetId netId) throws Exception {
        try {
            long boatId = boatRestService.getBoatId(request.getType(), request.getNumPeople());
            if (boatId == -1) {
                return "Could not contact boat service";
            }
            Training training = parseRequest(request, netId, boatId);
            trainingRepository.save(training);
            return "Training successfully created";
        } catch (DataIntegrityViolationException e) {
            throw new NetIdAlreadyInUseException(netId);
        } catch (Exception e) {
            throw new Exception("Something went wrong in createTraining");
        }
    }

    /**
     * Changes the persisted activity to add the new user (if accepted).
     *
     * @param model The request body
     * @return if success
     */
    public String informUser(AcceptRequestModel model) {
        return informUser(model, trainingRepository, eventPublisher);
    }

    /**
     * A method to request to join an activity.
     *
     * @param request the join request
     * @return status of request
     */
    public String joinTraining(JoinRequestModel request) {
        Training training = trainingRepository.findById(request.getActivityId());
        if (training == null) {
            return "this competition ID does not exist";
        }
        UserDataRequestModel userData = userRestService.getUserData();
        if (userData == null) {
            return "We could not get your user information from the user service";
        }
        if (request.getPosition() == Position.COX
                && training.getType().getValue() > userData.getCertificate().getValue()) {
            return "you do not have the required certificate to be cox";
        }
        eventPublisher.publishJoining(training.getOwner(), request.getPosition(), request.getActivityId());
        return "Done! Your request has been processed";
    }
}
