package nl.tudelft.sem.template.activity.domain.services;

import nl.tudelft.sem.template.activity.domain.GenderConstraint;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import nl.tudelft.sem.template.activity.domain.events.EventPublisher;
import nl.tudelft.sem.template.activity.domain.exceptions.NetIdAlreadyInUseException;
import nl.tudelft.sem.template.activity.domain.repositories.CompetitionRepository;
import nl.tudelft.sem.template.activity.models.AcceptRequestModel;
import nl.tudelft.sem.template.activity.models.CompetitionCreateModel;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CompetitionService extends ActivityService {

    private final transient EventPublisher eventPublisher;

    private final transient CompetitionRepository competitionRepository;

    public CompetitionService(EventPublisher eventPublisher, CompetitionRepository competitionRepository) {
        this.eventPublisher = eventPublisher;
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
     * Changes the persisted activity to add the new user (if accepted).
     *
     * @param model The request body
     * @param owner The request sender / owner of the activity
     * @return if success
     */
    public boolean informUser(AcceptRequestModel model, String owner) {
        boolean success = persistNewCompetition(model, competitionRepository);
        eventPublisher.publishAcceptance(model.isAccepted(), new NetId(owner), model.getRequestee());
        return success;
    }

    /**
     * A method to find a competition from the database.

     * @param id the netId of the requester
     * @return the Competition of the requester
     * @throws Exception the competition not found exception
     */
    public Competition findCompetitions(long id) throws Exception {
        try {
            return competitionRepository.findById(id);
        } catch (Exception e) {
            throw new Exception("Something went wrong in findCompetitions");
        }
    }

}
