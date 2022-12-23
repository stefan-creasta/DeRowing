package nl.tudelft.sem.template.activity.domain.services;

import nl.tudelft.sem.template.activity.domain.Gender;
import nl.tudelft.sem.template.activity.domain.GenderConstraint;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Position;
import nl.tudelft.sem.template.activity.domain.Type;
import nl.tudelft.sem.template.activity.domain.entities.Activity;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import nl.tudelft.sem.template.activity.domain.events.EventPublisher;
import nl.tudelft.sem.template.activity.domain.provider.implement.CurrentTimeProvider;
import nl.tudelft.sem.template.activity.domain.repositories.CompetitionRepository;
import nl.tudelft.sem.template.activity.models.AcceptRequestModel;
import nl.tudelft.sem.template.activity.models.BoatDeleteModel;
import nl.tudelft.sem.template.activity.models.CompetitionCreateModel;
import nl.tudelft.sem.template.activity.models.CompetitionEditModel;
import nl.tudelft.sem.template.activity.models.CreateBoatModel;
import nl.tudelft.sem.template.activity.models.CreateBoatResponseModel;
import nl.tudelft.sem.template.activity.models.FindSuitableActivityModel;
import nl.tudelft.sem.template.activity.models.JoinRequestModel;
import nl.tudelft.sem.template.activity.models.UserDataRequestModel;
import nl.tudelft.sem.template.activity.models.PositionEntryModel;
import nl.tudelft.sem.template.activity.models.FindSuitableActivityResponseModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompetitionService extends ActivityService {

    private final transient EventPublisher eventPublisher;
    private final transient CompetitionRepository competitionRepository;
    private final transient RestServiceFacade restServiceFacade;
    private final transient CurrentTimeProvider currentTimeProvider;

    /**
     * Constructor for CompetitionService bean.
     *
     * @param eventPublisher        the event publisher
     * @param competitionRepository the competition repository
     * @param restServiceFacade     the rest service facade
     * @param currentTimeProvider   the current time provider
     */
    public CompetitionService(EventPublisher eventPublisher, CompetitionRepository competitionRepository,
                              RestServiceFacade restServiceFacade,
                              CurrentTimeProvider currentTimeProvider) {
        this.eventPublisher = eventPublisher;
        this.competitionRepository = competitionRepository;
        this.restServiceFacade = restServiceFacade;
        this.currentTimeProvider = currentTimeProvider;
    }

    /**
     * Method parse a requestBody.
     *
     * @param request the request body
     * @param netId   the netId of the requester
     * @return a new Competition
     */
    public Competition parseRequest(CompetitionCreateModel request, NetId netId, long boatId) {
        String competitionName = request.getCompetitionName();
        long startTime = request.getStartTime();
        boolean allowAmateurs = request.isAllowAmateurs();
        boolean singleOrganization = request.isSingleOrganization();
        GenderConstraint genderConstraint = request.getGenderConstraint();
        Type boatType = request.getType();
        String organization = request.getOrganization();
        Competition competition = new Competition(netId, competitionName, boatId, startTime,
                allowAmateurs, genderConstraint, singleOrganization, organization, boatType);
        return competition;
    }

    /**
     * Method to create and persist a new Competition.
     *
     * @param request the request body
     * @param netId   the netId of the requester
     * @return a new Competition
     * @throws Exception the already using this netId exception
     */
    public String createCompetition(CompetitionCreateModel request, NetId netId) throws Exception {
        CreateBoatModel createBoatModel = new CreateBoatModel();
        createBoatModel.setType(request.getType());
        CreateBoatResponseModel response = (CreateBoatResponseModel) restServiceFacade.performBoatModel(createBoatModel,
                "/boat/create", CreateBoatResponseModel.class);
        if (response == null) {
            throw new Exception("Could not create boat");
        }
        long boatId = response.getBoatId();
        Competition competition = parseRequest(request, netId, boatId);
        competitionRepository.save(competition);
        return "Successfully created competition";
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
     * A method to request to join an activity.
     *
     * @param request the join request
     * @return status of request
     */
    public String joinCompetition(JoinRequestModel request) throws Exception {
        Competition competition = competitionRepository.findById(request.getActivityId());
        if (competition == null) {
            return "this competition ID does not exist";
        }
        long startTime = competition.getStartTime();
        boolean isInOneDay = (startTime - currentTimeProvider.getCurrentTime().toEpochMilli()) < 86400000;
        if (isInOneDay) {
            return "Sorry you can't join this competition since it will start in one day.";
        }
        UserDataRequestModel userData = (UserDataRequestModel)
                restServiceFacade.performUserModel(null, "/getdetails", UserDataRequestModel.class);
        if (userData == null) {
            return "We could not get your user information from the user service";
        }
        if (!competition.isAllowAmateurs() && userData.isAmateur()
                || !checkGender(userData.getGender(), competition.getGenderConstraint())
                || (competition.isSingleOrganization()
                && !userData.getOrganization().equals(competition.getOrganization()))) {
            return "you do not meet the constraints of this competition";
        }
        if (request.getPosition() == Position.COX
                && competition.getType().getValue() > userData.getCertificate().getValue()) {
            return "you do not have the required certificate to be cox";
        }
        eventPublisher.publishJoining(competition.getOwner(), request.getPosition(), request.getActivityId());
        return "Done! Your request has been processed";
    }

    /**
     * Check the gender to make it satisfy the requirements.
     *
     * @param gender the gender of the attendee
     * @param constraint the constraint of the competition
     * @return a boolean value which shows that whether the attendee could be admitted
     */
    private boolean checkGender(Gender gender, GenderConstraint constraint) {
        if (constraint == GenderConstraint.NO_CONSTRAINT) {
            return true;
        }
        if (constraint == GenderConstraint.ONLY_MALE && gender == Gender.MALE) {
            return true;
        }
        return constraint == GenderConstraint.ONLY_FEMALE && gender == Gender.FEMALE;
    }

    /**
     * The method to delete a specified competition.
     *
     * @param competitionId The id of the specified competition
     * @return A String which shows whether the competition is deleted successfully.
     * @throws Exception An exception to show that there's something wrong during the deleting process.
     */
    public String deleteCompetition(long competitionId) throws Exception {
        String netId = SecurityContextHolder.getContext().getAuthentication().getName();
        Competition competition = competitionRepository.findById(competitionId);
        if (competition == null) {
            return "Competition not found";
        }
        if (!competition.getOwner().toString().equals(netId)) {
            return "You are not the owner of this competition";
        }
        long boatId = competition.getBoatId();
        BoatDeleteModel boatDeleteModel = new BoatDeleteModel(boatId);
        restServiceFacade.performBoatModel(boatDeleteModel, "/boat/delete", null);
        competitionRepository.delete(competition);
        return "Successfully deleted competition";
    }


    /**
     * The method to edit a competition.
     *
     * @param request The competition editing model
     * @param netId  The netId of the requester
     * @return A string shows the status after the edition
     * @throws Exception An exception to be shown when facing failures.
     */
    public String editCompetition(CompetitionEditModel request, String netId) throws Exception {
        try {
            Competition competition = competitionRepository.findById(request.getId());
            if (!netId.equals(competition.getOwner().toString())) {
                return "You are not the owner of this competition";
            }
            competition = update(competition, request);
            competitionRepository.save(competition);
            return "Successfully edited competition";
        } catch (Exception e) {
            throw new Exception("Something went wrong in editing competition");
        }
    }

    /**
     * The method to update a competition.
     *
     * @param competition The competition to be updated.
     * @param request The edit model which contains all information about the updating.
     * @return A competition which is updated.
     */
    public Competition update(Competition competition, CompetitionEditModel request) {
        competition.setActivityName(request.getCompetitionName());
        competition.setGenderConstraint(request.getGenderConstraint());
        competition.setAllowAmateurs(request.isAllowAmateurs());
        competition.setSingleOrganization(request.isSingleOrganization());
        competition.setOrganization(request.getOrganization());
        competition.setStartTime(request.getStartTime());
        return competition;
    }

    /**
     * Gets suitable conpetitions for the specified position.
     *
     * @param position The position to filter from
     * @return a list of competitions
     */
    public List<Competition> getSuitableCompetition(PositionEntryModel position) throws Exception {
        UserDataRequestModel userData = (UserDataRequestModel)
                restServiceFacade.performUserModel(null, "/getdetails", UserDataRequestModel.class);
        if (userData == null) {
            throw new Exception("We could not get your user information from the user service");
        }
        List<Long> boatIds = competitionRepository.findAll().stream()
                .filter(competition -> competition.getStartTime()
                        > currentTimeProvider.getCurrentTime().toEpochMilli() + 86400000)
                .filter(competition -> checkGender(userData.getGender(), competition.getGenderConstraint()))
                .filter(competition -> competition.isAllowAmateurs() || !userData.isAmateur())
                .filter(competition -> !competition.isSingleOrganization()
                        || userData.getOrganization().equals(competition.getOrganization()))
                .map(Activity::getBoatId)
                .collect(Collectors.toList());

        FindSuitableActivityModel model = new FindSuitableActivityModel(boatIds, position.getPosition());
        FindSuitableActivityResponseModel suitableCompetitions =
                (FindSuitableActivityResponseModel) restServiceFacade.performBoatModel(model,
                        "/boat/check", FindSuitableActivityResponseModel.class);
        return competitionRepository.findAllByBoatIdIn(suitableCompetitions.getBoatId());
    }
}
