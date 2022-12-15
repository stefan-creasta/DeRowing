package nl.tudelft.sem.template.activity.domain.services;

import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.entities.Training;
import nl.tudelft.sem.template.activity.domain.events.EventPublisher;
import nl.tudelft.sem.template.activity.domain.exceptions.NetIdAlreadyInUseException;
import nl.tudelft.sem.template.activity.domain.repositories.CompetitionRepository;
import nl.tudelft.sem.template.activity.domain.repositories.TrainingRepository;
import nl.tudelft.sem.template.activity.models.AcceptRequestModel;
import nl.tudelft.sem.template.activity.models.TrainingCreateModel;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class TrainingService extends ActivityService {

    private final transient EventPublisher eventPublisher;

    private final transient CompetitionRepository competitionRepository;

    private final transient TrainingRepository trainingRepository;

    /**
     * Instantiates a new CompetitionService.
     *
     * @param eventPublisher the event publisher for user acceptance
     * @param competitionRepository the repository for competitions
     * @param trainingRepository the repository for trainings
     */
    public TrainingService(EventPublisher eventPublisher, CompetitionRepository competitionRepository,
                           TrainingRepository trainingRepository) {
        this.eventPublisher = eventPublisher;
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
     * Changes the persisted activity to add the new user (if accepted).
     *
     * @param model The request body
     * @param owner The request sender / owner of the activity
     * @return if success
     */
    public boolean informUser(AcceptRequestModel model, String owner) {
        boolean success = persistNewCompetition(model, competitionRepository);
        eventPublisher.publishAcceptance(model.isAccepted(), model.getPosition(), model.getRequestee());
        long boatId = competitionRepository.findById(model.getActivityId()).getBoatId();
        eventPublisher.publishBoatChange(boatId, model.getPosition());
        return success;
    }

}
