package nl.tudelft.sem.template.activity.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.tudelft.sem.template.activity.authentication.AuthManager;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import nl.tudelft.sem.template.activity.domain.entities.Training;
import nl.tudelft.sem.template.activity.domain.repositories.CompetitionRepository;
import nl.tudelft.sem.template.activity.domain.repositories.TrainingRepository;
import nl.tudelft.sem.template.activity.domain.services.BoatRestService;
import nl.tudelft.sem.template.activity.domain.services.CompetitionService;
import nl.tudelft.sem.template.activity.domain.services.TrainingService;
import nl.tudelft.sem.template.activity.models.CompetitionCreateModel;
import nl.tudelft.sem.template.activity.models.CompetitionFindModel;
import nl.tudelft.sem.template.activity.models.TrainingCreateModel;
import nl.tudelft.sem.template.activity.models.TrainingFindModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class TrainingControllerTest {

    @Mock
    private AuthManager authManager;

    @Mock
    private TrainingRepository trainingRepository;

    private TrainingService trainingService;

    private TrainingCreateModel trainingCreateModel;

    private TrainingFindModel trainingFindModel;

    private Training training;

    private TrainingController trainingController;

    @Mock
    private BoatRestService boatRestService;

    @Mock
    private CompetitionRepository competitionRepository;

    private NetId id;

    @BeforeEach
    public void setup() {
        this.authManager = mock(AuthManager.class);
        trainingRepository = mock(TrainingRepository.class);
        competitionRepository = mock(CompetitionRepository.class);
        boatRestService = mock(BoatRestService.class);
        id = new NetId("123");
        trainingService = new TrainingService(boatRestService, competitionRepository, trainingRepository);
        trainingController = new TrainingController(authManager, trainingService);
        trainingCreateModel = new TrainingCreateModel("test", 123L, 123L);
        trainingFindModel = new TrainingFindModel("test", null);
        training = new Training(id, trainingCreateModel.getTrainingName(),
                trainingCreateModel.getBoatId(), trainingCreateModel.getStartTime());
    }

    @Test
    void createTraining() throws Exception {
        when(authManager.getNetId()).thenReturn("123");
        when(trainingService.createTraining(trainingCreateModel, any())).thenReturn(training);
        Assertions.assertEquals(new ResponseEntity<>("Done! The Training test is created by 123",
                        HttpStatus.valueOf(200)),
                trainingController.createTraining(trainingCreateModel));
    }
}