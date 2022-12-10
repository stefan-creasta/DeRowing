package nl.tudelft.sem.template.activity.domain;

import nl.tudelft.sem.template.activity.models.TrainingCreateModel;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class TrainingService {

    private final transient BoatRestService boatRestService;

    private final transient CompetitionRepository competitionRepository;

    private final transient TrainingRepository trainingRepository;

    /**
     * Instantiates a new CompetitionService.
     *
     * @param boatRestService the boat rest service
     * @param competitionRepository the repository for competitions
     * @param trainingRepository the repository for trainings
     */
    public TrainingService(BoatRestService boatRestService, CompetitionRepository competitionRepository,
                           TrainingRepository trainingRepository) {
        this.boatRestService = boatRestService;
        this.competitionRepository = competitionRepository;
        this.trainingRepository = trainingRepository;
    }

    /**
     * Method to parse the TrainingCreateModel.
     *
     * @param request the TrainingCreateModel aka the request body
     * @param netId the netId of the requester
     * @return a new Training
     */
    public Training parseRequest(TrainingCreateModel request, NetId netId) {
        return new Training(netId, request.getTrainingName(), request.getBoatId(), request.getStartTime());
    }


    /**
     * Create a new training.
     *
     * @param netId the id of the owner
     * @param request the request body
     * @return a new training
     * @throws Exception the already using this netId exception
     */
    public Training createTraining(TrainingCreateModel request, NetId netId) throws Exception {
        try {
            Training training = parseRequest(request, netId);
            trainingRepository.save(training);
            return training;
        } catch (DataIntegrityViolationException e) {
            throw new NetIdAlreadyInUseException(netId);
        } catch (Exception e) {
            throw new Exception("Something went wrong in createTraining");
        }
    }

    /**
     * The method to find a training.
     *
     * @param netId the netId of the owner
     * @return the training found
     * @throws Exception an activityNotFoundException
     */
    public Training findTraining(NetId netId) throws Exception {
        if (trainingRepository.existsByNetId(netId)) {
            Training training = trainingRepository.findByNetId(netId);
            return training;
        }
        throw new ActivityNotFoundException(netId);
    }

}
