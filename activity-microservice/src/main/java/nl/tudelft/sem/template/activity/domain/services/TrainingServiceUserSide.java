package nl.tudelft.sem.template.activity.domain.services;

import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Position;
import nl.tudelft.sem.template.activity.domain.entities.Activity;
import nl.tudelft.sem.template.activity.domain.entities.Training;
import nl.tudelft.sem.template.activity.domain.events.EventPublisher;
import nl.tudelft.sem.template.activity.domain.provider.implement.CurrentTimeProvider;
import nl.tudelft.sem.template.activity.domain.repositories.TrainingRepository;
import nl.tudelft.sem.template.activity.models.AcceptRequestModel;
import nl.tudelft.sem.template.activity.models.BoatDeleteModel;
import nl.tudelft.sem.template.activity.models.CreateBoatModel;
import nl.tudelft.sem.template.activity.models.CreateBoatResponseModel;
import nl.tudelft.sem.template.activity.models.FindSuitableActivityModel;
import nl.tudelft.sem.template.activity.models.FindSuitableActivityResponseModel;
import nl.tudelft.sem.template.activity.models.JoinRequestModel;
import nl.tudelft.sem.template.activity.models.TrainingCreateModel;
import nl.tudelft.sem.template.activity.models.TrainingEditModel;
import nl.tudelft.sem.template.activity.models.UserDataRequestModel;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainingServiceUserSide extends ActivityService {

    private final transient EventPublisher eventPublisher;
    private final transient RestServiceFacade restServiceFacade;
    private final transient TrainingRepository trainingRepository;
    private final transient CurrentTimeProvider currentTimeProvider;

    /**
     * Instantiates a new CompetitionService.
     *
     * @param eventPublisher      the event publisher for user acceptance
     * @param restServiceFacade   the rest service facade
     * @param trainingRepository  the repository for trainings
     * @param currentTimeProvider the current time provider
     */
    public TrainingServiceUserSide(EventPublisher eventPublisher, RestServiceFacade restServiceFacade,
                                   TrainingRepository trainingRepository,
                                   CurrentTimeProvider currentTimeProvider) {
        this.eventPublisher = eventPublisher;
        this.restServiceFacade = restServiceFacade;
        this.trainingRepository = trainingRepository;
        this.currentTimeProvider = currentTimeProvider;
    }

    /**
     * Checking whether training is null or not.
     *
     * @param training the input of parameter
     * @return an information string
     */
    public String checkNull(Training training) {
        if (training == null) {
            return "this competition ID does not exist";
        } else {
            return "";
        }
    }

    /**
     * check whether the training will start in one day or not.
     *
     * @param startTime input start time of the training
     * @return an information string
     */
    public String checkIsOneDay(long startTime) {
        long constant = 1800000;
        if (startTime - currentTimeProvider.getCurrentTime().toEpochMilli() < constant) {
            return "Sorry you can't join this training since it will start in one day.";
        } else {
            return "";
        }
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
     * check whether a requester has a specific license for that training session.
     *
     * @param request request
     * @param training training session
     * @param userData user data
     * @return an information string
     */
    public String checkIfHaveRequiredCertificate(JoinRequestModel request,
                                                 Training training, UserDataRequestModel userData) {
        if (request.getPosition() == Position.COX
            && training.getType().getValue() > userData.getCertificate().getValue()) {
            return "you do not have the required certificate to be cox";
        } else {
            return "";
        }
    }

    /**
     * check conditions.
     *
     * @param training training session
     * @return an information string
     */
    public String checkNullOrCheckIsOneDay(Training training) {
        String result = checkNull(training);
        if (!result.equals("")) {
            return result;
        }
        result = checkIsOneDay(training.getStartTime());
        if (!result.equals("")) {
            return result;
        } else {
            return "";
        }
    }

    /**
     * check conditions.
     *
     * @param request request
     * @param training training
     * @param userData user data
     * @return an information string
     */
    public String checkUserDataNullOrHavingRequiredCertificate(JoinRequestModel request,
                                                               Training training, UserDataRequestModel userData) {
        String result = checkUserDataNull(userData);
        if (!result.equals("")) {
            return result;
        }
        result = checkIfHaveRequiredCertificate(request, training, userData);
        if (!result.equals("")) {
            return result;
        } else {
            return "";
        }
    }

    /**
     * A method to request to join an activity.
     *
     * @param request the join request
     * @return status of request
     */
    public String joinTraining(JoinRequestModel request) throws Exception {
        Training training = trainingRepository.findById(request.getActivityId());
        String result = checkNullOrCheckIsOneDay(training);
        if (!result.equals("")) {
            return result;
        }
        UserDataRequestModel userData = (UserDataRequestModel)
            restServiceFacade.performUserModel(null, "/getdetails", UserDataRequestModel.class);
        String result1 = checkUserDataNullOrHavingRequiredCertificate(request, training, userData);
        if (!result1.equals("")) {
            return result1;
        }
        eventPublisher.publishJoining(training.getOwner(), request.getPosition(), request.getActivityId());
        return "Done! Your request has been processed";
    }

    /**
     * The method to find the training with id.
     *
     * @param id The id of the training to be found.
     * @return A training found.
     * @throws Exception An exception to be thrown when facing failures.
     */
    public Training findTraining(long id) throws Exception {
        try {
            return trainingRepository.findById(id);
        } catch (Exception e) {
            throw new Exception("Something went wrong in findTraining");
        }
    }

    /**
     * The method to get all trainings.
     *
     * @param position the preferred position of the user
     * @return the list of matching trainings
     * @throws Exception the exception
     */
    public List<Training> getSuitableCompetition(Position position) throws Exception {
        UserDataRequestModel userData = (UserDataRequestModel)
                restServiceFacade.performUserModel(null, "/getdetails", UserDataRequestModel.class);
        if (userData == null) {
            throw new Exception("We could not get your user information from the user service");
        }

        List<Long> boatIds = trainingRepository.findAll().stream()
                .filter(competition -> competition.getStartTime()
                        > currentTimeProvider.getCurrentTime().toEpochMilli() + (30 * 60 * 1000))
                .map(Activity::getBoatId)
                .collect(Collectors.toList());
        FindSuitableActivityModel model = new FindSuitableActivityModel(boatIds, position);
        FindSuitableActivityResponseModel suitableCompetitions =
                (FindSuitableActivityResponseModel) restServiceFacade.performBoatModel(model,
                        "/boat/check", FindSuitableActivityResponseModel.class);
        return trainingRepository.findAllByBoatIdIn(suitableCompetitions.getBoatId());
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
}