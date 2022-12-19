package nl.tudelft.sem.template.activity.domain.services;

import nl.tudelft.sem.template.activity.domain.Gender;
import nl.tudelft.sem.template.activity.domain.GenderConstraint;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Position;import nl.tudelft.sem.template.activity.domain.entities.Competition;
import nl.tudelft.sem.template.activity.domain.events.EventPublisher;
import nl.tudelft.sem.template.activity.domain.exceptions.NetIdAlreadyInUseException;
import nl.tudelft.sem.template.activity.domain.repositories.CompetitionRepository;
import nl.tudelft.sem.template.activity.models.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;import java.util.List;

@Service
public class CompetitionService extends ActivityService {

    private final transient EventPublisher eventPublisher;
    private final transient CompetitionRepository competitionRepository;
    private final transient UserRestService userRestService;
    private final transient BoatRestService boatRestService;


    /**
     * Constructor for CompetitionService bean.
     *
     * @param eventPublisher the event publisher
     * @param competitionRepository the competition repository
     * @param userRestService the user rest service
     * @param boatRestService the boat rest service
     */
    public CompetitionService(EventPublisher eventPublisher, CompetitionRepository competitionRepository,
                              UserRestService userRestService, BoatRestService boatRestService) {
        this.eventPublisher = eventPublisher;
        this.competitionRepository = competitionRepository;
        this.userRestService = userRestService;
        this.boatRestService = boatRestService;
    }

    /**
     * Method parse a requestBody.

     * @param request the request body
     * @param netId the netId of the requester
     * @return a new Competition
     */
    public Competition parseRequest(CompetitionCreateModel request, NetId netId, long boatId) {
        String competitionName = request.getCompetitionName();
        long startTime = request.getStartTime();
        boolean allowAmateurs = request.isAllowAmateurs();
        boolean singleOrganization = request.isSingleOrganization();
        GenderConstraint genderConstraint = request.getGenderConstraint();
        int numPeople = request.getNumPeople();
        String organization = request.getOrganization();
        Competition competition = new Competition(netId, competitionName, boatId, startTime, numPeople,
                allowAmateurs, genderConstraint, singleOrganization, organization);
        return competition;
    }

    /**
     * Method to create and persist a new Competition.

     * @param request the request body
     * @param netId the netId of the requester
     * @return a new Competition
     * @throws Exception the already using this netId exception
     */
    public String createCompetition(CompetitionCreateModel request, NetId netId) throws Exception {
        try {
            long boatId = boatRestService.getBoatId(request.getType(), request.getNumPeople());
            if (boatId == -1) {
                return "Could not contact boat service";
            }
            Competition competition = parseRequest(request, netId, boatId);
            competitionRepository.save(competition);
            return "Successfully created competition";
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
     * @return if success
     */
    public String informUser(AcceptRequestModel model) {
        return informUser(model, competitionRepository, eventPublisher);
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

    /**
     * A method to request to join an activity.
     *
     * @param request the join request
     * @return status of request
     */
    public String joinCompetition(JoinRequestModel request) {
        Competition competition = competitionRepository.findById(request.getActivityId());
        if (competition == null) {
            return "this competition ID does not exist";
        }
        UserDataRequestModel userData = userRestService.getUserData();
        if (userData == null) {
            return "We could not get your user information from the user service";
        }
        if (!competition.isAllowAmateurs() && userData.isAmateur()
            || !checkGender(userData.getGender(), competition.getGenderConstraint())
            || !userData.getOrganization().equals(competition.getOrganization())) {
            return "you do not meet the constraints of this competition";
        }
        InformJoinRequestModel model = new InformJoinRequestModel();
        eventPublisher.publishJoining(model.getOwner(), model.getPosition(), model.getActivityId());
        return "Done! Your request has been processed";
    }

    private boolean checkGender(Gender gender, GenderConstraint constraint) {
        if (constraint == GenderConstraint.NO_CONSTRAINT) {
            return true;
        }
        if (constraint == GenderConstraint.ONLY_MALE && gender == Gender.MALE) {
            return true;
        }
        return constraint == GenderConstraint.ONLY_FEMALE && gender == Gender.FEMALE;
    }

	public List<Competition> getSuitableCompetition(Position position) {
		UserDataRequestModel userDataRequestModel = userRestService.getUserData();
		List<Competition> competitionsAreMetConstraints = competitionRepository
						.findSuitableCompetitions(userDataRequestModel.getGender(),
						userDataRequestModel.getOrganization(),
						userDataRequestModel.isAmateur());
		return boatRestService.checkIfPositionAvailable(competitionsAreMetConstraints,position);
	}
}
