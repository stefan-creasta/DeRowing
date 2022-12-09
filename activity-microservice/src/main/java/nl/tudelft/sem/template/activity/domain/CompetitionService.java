package nl.tudelft.sem.template.activity.domain;

import org.springframework.stereotype.Service;

@Service
public class CompetitionService {

    private final transient CompetitionRepository competitionRepository;

    /**
     * Instantiates a new CompetitionyService.
     *
     * @param competitionRepository the repository for activities
     */
    public CompetitionService(CompetitionRepository competitionRepository) {
        this.competitionRepository = competitionRepository;
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

            // Create new account
            Competition competition = new Competition(netId, competitionName, boatId, startTime,
                    allowAmateurs, genderConstraint, singleOrganization);
            competitionRepository.save(competition);

            return competition;
        }

        throw new NetIdAlreadyInUseException(netId);
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
        throw new CompetitionNotFoundException(netId);
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
