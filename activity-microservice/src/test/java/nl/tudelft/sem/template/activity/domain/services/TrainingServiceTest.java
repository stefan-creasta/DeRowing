package nl.tudelft.sem.template.activity.domain.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Type;
import nl.tudelft.sem.template.activity.domain.entities.Training;
import nl.tudelft.sem.template.activity.domain.events.EventPublisher;
import nl.tudelft.sem.template.activity.domain.provider.implement.CurrentTimeProvider;
import nl.tudelft.sem.template.activity.domain.repositories.CompetitionRepository;
import nl.tudelft.sem.template.activity.domain.repositories.TrainingRepository;
import nl.tudelft.sem.template.activity.models.TrainingCreateModel;
import org.h2.engine.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.util.Optional;

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

    @Mock
    private EventPublisher eventPublisher;

    @Mock
    private UserRestService userRestService;

    @Mock
    private CurrentTimeProvider currentTimeProvider;

    @BeforeEach
    public void setup() {
        trainingCreateModel = new TrainingCreateModel("test", 123L, Type.C4);
        id = new NetId("123");
        training = new Training(id, trainingCreateModel.getTrainingName(), 123L, 123L,
                Type.C4);
        trainingRepository = mock(TrainingRepository.class);
        boatRestService = mock(BoatRestService.class);
        competitionRepository = mock(CompetitionRepository.class);
        trainingService = new TrainingService(eventPublisher, userRestService,
                trainingRepository, boatRestService,
                currentTimeProvider);
    }

    @Test
    void parseRequest() {
        Assertions.assertEquals(training, trainingService.parseRequest(trainingCreateModel, id, 123L));
    }

    @Test
    void createTraining() throws Exception {

    }

    @Test
    void findTraining() throws Exception {

    }
}