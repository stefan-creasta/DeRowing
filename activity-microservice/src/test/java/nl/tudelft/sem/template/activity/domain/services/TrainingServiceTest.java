package nl.tudelft.sem.template.activity.domain.services;

import java.util.List;
import java.util.Optional;
import nl.tudelft.sem.template.activity.domain.GenderConstraint;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import nl.tudelft.sem.template.activity.domain.entities.Training;
import nl.tudelft.sem.template.activity.domain.repositories.CompetitionRepository;
import nl.tudelft.sem.template.activity.domain.repositories.TrainingRepository;
import nl.tudelft.sem.template.activity.models.CompetitionCreateModel;
import nl.tudelft.sem.template.activity.models.TrainingCreateModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TrainingServiceTest {

    private TrainingService trainingService;

    private TrainingCreateModel trainingCreateModel;

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private CompetitionRepository competitionRepository;

    @Mock
    private BoatRestService boatRestService;

    private Training training;

    private NetId id;

    @BeforeEach
    public void setup() {
        trainingCreateModel = new TrainingCreateModel("test", 123L, 123L);
        id = new NetId("123");
        training = new Training(id, trainingCreateModel.getTrainingName(),
                trainingCreateModel.getBoatId(), trainingCreateModel.getStartTime());
        trainingRepository = mock(TrainingRepository.class);
        boatRestService = mock(BoatRestService.class);
        competitionRepository = mock(CompetitionRepository.class);
        trainingService = new TrainingService(boatRestService, competitionRepository, trainingRepository);
    }

    @Test
    void parseRequest() {
        Assertions.assertEquals(training, trainingService.parseRequest(trainingCreateModel, id));
    }

    @Test
    void createTraining() throws Exception {
        Assertions.assertEquals(training, trainingService.createTraining(trainingCreateModel, id));
    }

    @Test
    void findTraining() throws Exception {
        when(trainingRepository.existsByNetId(any())).thenReturn(true);
        when(trainingRepository.findByNetId(any())).thenReturn(training);
        Assertions.assertEquals(training, trainingService.findTraining(id));
    }
}