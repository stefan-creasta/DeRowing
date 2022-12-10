package nl.tudelft.sem.template.activity.domain;

import nl.tudelft.sem.template.activity.models.CompetitionCreateModel;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CompetitionService {

    private final transient CompetitionRepository competitionRepository;

    public CompetitionService(CompetitionRepository competitionRepository) {
        this.competitionRepository = competitionRepository;
    }

    /**
     * Method parse a requestBody.

     * @param request the request body
     * @param netId the netId of the requester
     * @return a new Competition
     */
    public Competition parseRequest(CompetitionCreateModel request, NetId netId) {
        String competitionName = request.getCompetitionName();
        long boatId = request.getBoatId();
        long startTime = request.getStartTime();
        boolean allowAmateurs = request.isAllowAmateurs();
        boolean singleOrganization = request.isSingleOrganization();
        GenderConstraint genderConstraint = request.getGenderConstraint();

        Competition competition = new Competition(netId, competitionName, boatId, startTime,
                allowAmateurs, genderConstraint, singleOrganization);
        return competition;
    }


    /**
     * Method to create and persist a new Competition.

     * @param request the request body
     * @param netId the netId of the requester
     * @return a new Competition
     * @throws Exception the already using this netId exception
     */
    public Competition createCompetition(CompetitionCreateModel request, NetId netId) throws Exception {
        try {
            Competition competition = parseRequest(request, netId);
            competitionRepository.save(competition);
            return competition;
        } catch (DataIntegrityViolationException e) {
            throw new NetIdAlreadyInUseException(netId);
        } catch (Exception e) {
            throw new Exception("Something went wrong in createCompetition");
        }
    }


    /**
     * A method to find a competition from the database.

     * @param netId the netId of the requester
     * @return the Competition of the requester
     * @throws Exception the competition not found exception
     */
    public Competition findCompetitions(NetId netId) throws Exception {
        try {
            return competitionRepository.findByNetId(netId);
        } catch (Exception e) {
            throw new Exception("Something went wrong in findCompetitions");
        }
    }

}
