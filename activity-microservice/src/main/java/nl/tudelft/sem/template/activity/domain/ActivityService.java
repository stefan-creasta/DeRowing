package nl.tudelft.sem.template.activity.domain;

import org.springframework.stereotype.Service;

@Service
public class ActivityService {

    private final transient CompetitionRepository competitionRepository;

    private final transient TrainingRepository trainingRepository;

    /**
     * Instantiates a new CompetitionyService.
     *
     * @param competitionRepository the repository for competitions
     * @param trainingRepository the repository for trainings
     */
    public ActivityService(CompetitionRepository competitionRepository, TrainingRepository trainingRepository) {
        this.competitionRepository = competitionRepository;
        this.trainingRepository = trainingRepository;
    }

    /**
     * Create a new competition and put it in the repository.
     *
     * @param netId the netId of the user
     * @param competitionName the name of the competition
     * @param boatId the id of the boat
     * @param startTime the startTime of the competition
     * @param allowAmateurs whether the competition allow amateurs
     * @param genderConstraint whether this is gender constraint
     * @param singleOrganization whether only accept single organization attendees
     * @return a competition
     * @throws Exception the exception for competition creation
     */
    public Competition createCompetition(NetId netId, String competitionName, long boatId, long startTime,
                                      boolean allowAmateurs, GenderConstraint genderConstraint,
                                      boolean singleOrganization) throws Exception {

        if (checkNetIdIsUnique(netId)) {
            Competition competition = new Competition(netId, competitionName, boatId, startTime,
                    allowAmateurs, genderConstraint, singleOrganization);
            competitionRepository.save(competition);

            return competition;
        }

        throw new NetIdAlreadyInUseException(netId);
    }

    /**
     * Create a new training.
     *
     * @param netId the id of the owner
     * @param trainingName the name of the training
     * @param boatId the id of the boat
     * @param startTime the start time
     * @return a new training
     * @throws Exception the already using this netId exception
     */
    public Training createTraining(NetId netId, String trainingName, long boatId, long startTime) throws Exception {
        if (checkNetIdIsUnique(netId)) {
            Training training = new Training(netId, trainingName, boatId, startTime);
            trainingRepository.save(training);
        }
        throw new NetIdAlreadyInUseException(netId);
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

    /**
     * Find a competition with the given netId.
     *
     * @param netId           the netId of the competition owner
     * @return                a competition to be found
     * @throws Exception      the exception for not found competition
     */
    public Competition findCompetition(NetId netId) throws Exception {
        if (competitionRepository.existsByNetId(netId)) {
            Competition target = competitionRepository.findByNetId(netId);
            return target;
        }
        throw new ActivityNotFoundException(netId);
    }

    /**
     * the method to check whether the given netId is used to create a competition.
     *
     * @param netId             the netId to be checked
     * @return                  a boolean value representing the result
     */
    public boolean checkNetIdIsUnique(NetId netId) {
        return !competitionRepository.existsByNetId(netId);
    }
}
