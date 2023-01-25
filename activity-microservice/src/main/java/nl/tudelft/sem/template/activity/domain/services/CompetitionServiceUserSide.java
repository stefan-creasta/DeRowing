package nl.tudelft.sem.template.activity.domain.services;


import nl.tudelft.sem.template.activity.domain.Gender;
import nl.tudelft.sem.template.activity.domain.GenderConstraint;
import nl.tudelft.sem.template.activity.domain.Position;
import nl.tudelft.sem.template.activity.domain.entities.Activity;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import nl.tudelft.sem.template.activity.domain.entities.Training;
import nl.tudelft.sem.template.activity.domain.events.EventPublisher;
import nl.tudelft.sem.template.activity.domain.provider.implement.CurrentTimeProvider;
import nl.tudelft.sem.template.activity.domain.repositories.CompetitionRepository;
import nl.tudelft.sem.template.activity.models.AcceptRequestModel;
import nl.tudelft.sem.template.activity.models.FindSuitableActivityModel;
import nl.tudelft.sem.template.activity.models.FindSuitableActivityResponseModel;
import nl.tudelft.sem.template.activity.models.JoinRequestModel;
import nl.tudelft.sem.template.activity.models.PositionEntryModel;
import nl.tudelft.sem.template.activity.models.UserDataRequestModel;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompetitionServiceUserSide extends ActivityService {

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
    public CompetitionServiceUserSide(EventPublisher eventPublisher, CompetitionRepository competitionRepository,
                                      RestServiceFacade restServiceFacade,
                                      CurrentTimeProvider currentTimeProvider) {
        this.eventPublisher = eventPublisher;
        this.competitionRepository = competitionRepository;
        this.restServiceFacade = restServiceFacade;
        this.currentTimeProvider = currentTimeProvider;
    }

    /**
     * Changes the persisted activity to add the new user (if accepted).
     *
     * @param model The request body
     * @return if success
     */
    public String informUser(AcceptRequestModel model) throws Exception {
        return informUser(model, competitionRepository, eventPublisher);
    }


    /**
     * check whether the user data is null or not.
     *
     * @param userData user data
     * @return an information string
     */
    public String checkUserDataNull(UserDataRequestModel userData) {
        if (userData == null) {
            return "We could not get your user information from the user service";
        } else {
            return "";
        }
    }

    /**
     * check whether a requester has a specific license for that competition (in the case of COX).
     *
     * @param request request
     * @param competition competition session
     * @param userData user data
     * @return an information string
     */
    public String checkIfHaveRequiredCertificate(JoinRequestModel request,
                                                 Competition competition, UserDataRequestModel userData) {
        if (request.getPosition() == Position.COX
                && competition.getType().getValue() > userData.getCertificate().getValue()) {
            return "you do not have the required certificate to be cox";
        } else {
            return "";
        }
    }

    /**
     * checks if the user meets the constraints specified by the competition.
     *
     * @param competition the competition
     * @param userData the user
     * @return an information string
     */
    public String checkIfUserMeetsConstraints(Competition competition, UserDataRequestModel userData) {
        return (!competition.isAllowAmateurs() && userData.isAmateur()
                || !checkGender(userData.getGender(), competition.getGenderConstraint())
                || (competition.isSingleOrganization()
                && !userData.getOrganization().equals(competition.getOrganization())))
                ? "you do not meet the constraints of this competition" : "";
    }

    /**
     * check competition specific conditions.
     *
     * @param competition competition session
     * @return an information string
     */
    public String checkNullOrCheckIsOneDay(Competition competition) {
        String result = competition == null ? "this competition ID does not exist" : "";
        if (!result.equals("")) {
            return result;
        }
        long constant = 86400000;
        return ((competition.getStartTime() - currentTimeProvider.getCurrentTime().toEpochMilli()) < constant)
                ? "Sorry you can't join this competition since it will start in one day." : "";
    }

    /**
     * checks the user side conditions for joining a competition.
     *
     * @param request the join request model
     * @param competition the competition
     * @param userData the user data
     * @return an information string
     */
    public String checkUserSideConditions(JoinRequestModel request, Competition competition,
                                          UserDataRequestModel userData) {
        String res = checkUserDataNull(userData);
        if (!res.equals("")) {
            return res;
        }
        res = checkIfHaveRequiredCertificate(request, competition, userData);
        if (!res.equals("")) {
            return res;
        }
        return checkIfUserMeetsConstraints(competition, userData);
    }


    /**
     * A method to request to join an activity.
     *
     * @param request the join request
     * @return status of request
     */
    public String joinCompetition(JoinRequestModel request) throws Exception {
        Competition competition = competitionRepository.findById(request.getActivityId());
        String result = checkNullOrCheckIsOneDay(competition);
        if (!result.equals("")) {
            return result;
        }
        UserDataRequestModel userData = (UserDataRequestModel)
                restServiceFacade.performUserModel(null, "/getdetails", UserDataRequestModel.class);
        result = checkUserSideConditions(request, competition, userData);
        if (!result.equals("")) {
            return result;
        }
        eventPublisher.publishJoining(competition.getOwner(), request.getPosition(), request.getActivityId());
        return "Done! Your request has been processed";
    }

    /**
     * The method to find the competition with id.
     *
     * @param id The id of the training to be found.
     * @return A training found.
     * @throws Exception An exception to be thrown when facing failures.
     */
    public Competition findCompetition(long id) throws Exception {
        try {
            return competitionRepository.findById(id);
        } catch (Exception e) {
            throw new Exception("Something went wrong in findTraining");
        }
    }

    /**
     * Check the gender to make it satisfy the requirements.
     *
     * @param gender the gender of the attendee
     * @param constraint the constraint of the competition
     * @return a boolean value which shows that whether the attendee could be admitted
     */
    private boolean checkGender(Gender gender, GenderConstraint constraint) {
        int genderConstraint = 3;
        if (constraint.value == genderConstraint) {
            return true;
        }
        return (constraint.value == gender.value);
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
        List<Long> boatIds = suitableBoatIds(competitionRepository, userData);

        FindSuitableActivityModel model = new FindSuitableActivityModel(boatIds, position.getPosition());

        FindSuitableActivityResponseModel suitableCompetitions =
                (FindSuitableActivityResponseModel) restServiceFacade.performBoatModel(model,
                        "/boat/check", FindSuitableActivityResponseModel.class);

        return competitionRepository.findAllByBoatIdIn(suitableCompetitions.getBoatId());
    }

    /**
     * Refactoring the logical part from getSuitableCompetitions method -> easier for testing.
     *
     * @param competitionRepository competition repository
     * @param userData user data
     * @return a list of boat ids
     */
    public List<Long> suitableBoatIds(CompetitionRepository competitionRepository, UserDataRequestModel userData) {
        List<Competition> competitions = competitionRepository.findAll();
        List<Long> ids = competitions.stream()
            .filter(competition -> competition.getStartTime()
                > currentTimeProvider.getCurrentTime().toEpochMilli() + 86400000)
            .filter(competition -> checkGender(userData.getGender(), competition.getGenderConstraint()))
            .filter(competition -> checkAmateur(competition, userData))
            .filter(competition -> checkOriganization(competition, userData))
            .map(Activity::getBoatId)
            .collect(Collectors.toList());
        return ids;
    }

    /**
     * The method to check amateur.
     *
     * @param competition The competition.
     * @param userData The User details.
     * @return A boolean value representing the result.
     */
    public boolean checkAmateur(Competition competition, UserDataRequestModel userData) {
        return competition.isAllowAmateurs() || !userData.isAmateur();
    }

    /**
     * The method to check organization.
     *
     * @param competition The competition.
     * @param userData The User details.
     * @return A boolean value representing the result.
     */
    public boolean checkOriganization(Competition competition, UserDataRequestModel userData) {
        return !competition.isSingleOrganization()
                || userData.getOrganization().equals(competition.getOrganization());
    }
}
