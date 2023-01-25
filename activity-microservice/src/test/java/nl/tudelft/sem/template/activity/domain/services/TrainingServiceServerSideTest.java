package nl.tudelft.sem.template.activity.domain.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import nl.tudelft.sem.template.activity.authentication.AuthManager;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Position;
import nl.tudelft.sem.template.activity.domain.Type;
import nl.tudelft.sem.template.activity.domain.entities.Training;
import nl.tudelft.sem.template.activity.domain.events.EventPublisher;
import nl.tudelft.sem.template.activity.domain.provider.implement.CurrentTimeProvider;
import nl.tudelft.sem.template.activity.domain.repositories.TrainingRepository;
import nl.tudelft.sem.template.activity.models.AcceptRequestModel;
import nl.tudelft.sem.template.activity.models.BoatDeleteModel;
import nl.tudelft.sem.template.activity.models.CreateBoatResponseModel;
import nl.tudelft.sem.template.activity.models.JoinRequestModel;
import nl.tudelft.sem.template.activity.models.TrainingCreateModel;
import nl.tudelft.sem.template.activity.models.TrainingEditModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.time.Instant;

class TrainingServiceServerSideTest {

    private TrainingServiceServerSide trainingServiceServerSide;

    private TrainingServiceUserSide trainingServiceUserSide;

    private TrainingCreateModel trainingCreateModel;

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private BoatRestService boatRestService;

    private Training training;

    private NetId id;

    @Mock
    private EventPublisher eventPublisher;

    @Mock
    private UserRestService userRestService;

    @Mock
    private RestServiceFacade restServiceFacade;

    @Mock
    private CurrentTimeProvider currentTimeProvider;

    @Mock
    private AuthManager authManager;

    private JoinRequestModel joinRequestModel;

    private AcceptRequestModel acceptRequestModel;

    @BeforeEach
    public void setup() {
        trainingCreateModel = new TrainingCreateModel("test", 123L, Type.C4);
        eventPublisher = mock(EventPublisher.class);
        trainingRepository = mock(TrainingRepository.class);
        this.restServiceFacade = mock(RestServiceFacade.class);
        currentTimeProvider = mock(CurrentTimeProvider.class);
        authManager = mock(AuthManager.class);
        joinRequestModel = new JoinRequestModel();
        joinRequestModel.setActivityId(123L);
        joinRequestModel.setPosition(Position.COACH);
        acceptRequestModel = new AcceptRequestModel();
        trainingServiceServerSide = new TrainingServiceServerSide(restServiceFacade,
                trainingRepository);
        trainingServiceUserSide = new TrainingServiceUserSide(eventPublisher, restServiceFacade,
                trainingRepository, currentTimeProvider);
        id = new NetId("123");
        training = new Training(id, trainingCreateModel.getTrainingName(), 123L, 123L,
                Type.C4);

    }

    @Test
    void parseRequest() {
        assertEquals(training, trainingServiceServerSide
                .parseRequest(trainingCreateModel, id, 123L));
    }

    @Test
    void createTraining() throws Exception {
        when(restServiceFacade.performBoatModel(any(), any(), any())).thenReturn(new CreateBoatResponseModel(123L));
        when(trainingRepository.save(training)).thenReturn(training);
        assertEquals("Successfully created training",
                trainingServiceServerSide.createTraining(trainingCreateModel, new NetId("123")));
    }

    @Test
    void joinTraining() throws Exception {
        when(trainingRepository.findById(123L)).thenReturn(training);
        when(currentTimeProvider.getCurrentTime()).thenReturn(Instant.ofEpochSecond(123L));
        assertEquals("Sorry you can't join this training "
                + "since it will start in one day.", trainingServiceUserSide.joinTraining(joinRequestModel));
    }

    @Test
    void testFindTraining() throws Exception {
        when(trainingRepository.findById(123L)).thenReturn(training);
        assertEquals(training, trainingServiceUserSide.findTraining(123L));
    }

    @Test
    void deleteTraining() throws Exception {
        when(authManager.getNetId()).thenReturn("123");
        BoatDeleteModel boatDeleteModel = new BoatDeleteModel(123L);
        when(trainingServiceUserSide.findTraining(123L)).thenReturn(training);
        assertEquals("Successfully deleted training",
                trainingServiceServerSide.deleteTraining(123L, authManager.getNetId()));
    }

    @Test
    void update() {
        Training temp = new Training(id, "newName", 123L, 0,
                Type.C4);
        TrainingEditModel trainingEditModel = new TrainingEditModel();
        trainingEditModel.setTrainingName("newName");
        training = trainingServiceServerSide.update(training, trainingEditModel);
        assertEquals(temp, training);
    }

    @Test
    void editTraining() throws Exception {
        when(trainingRepository.findById(123L)).thenReturn(training);
        Training temp = new Training(id, "newName", 123L, 0,
                Type.C4);
        TrainingEditModel trainingEditModel = new TrainingEditModel();
        trainingEditModel.setTrainingName("newName");
        Assertions.assertThrows(Exception.class, () -> {
            trainingServiceServerSide.editTraining(trainingEditModel, authManager.getNetId());
        });
    }

    @Test
    void editTrainingNotOwnerTest() throws Exception {
        // Create input
        TrainingEditModel model = new TrainingEditModel();
        model.setId(1);
        // We return a training with a different netid as owner
        Training training = new Training();
        training.setOwner(new NetId("notmaarten"));
        when(trainingRepository.findById(1)).thenReturn(training);

        String response = trainingServiceServerSide.editTraining(model, "maarten");
        assertEquals("You are not the owner of this competition", response);
        verify(trainingRepository, times(1)).findById(1);
    }

    @Test
    void deleteTrainingNotOwnerTest() throws Exception {
        // Create input
        TrainingEditModel model = new TrainingEditModel();
        model.setId(1);
        // We return a training with a different netid as owner
        Training training = new Training();
        training.setOwner(new NetId("notmaarten"));
        when(trainingRepository.findById(1)).thenReturn(training);


        String response = trainingServiceServerSide.deleteTraining(1, "maarten");

        assertEquals("You are not the owner of this competition", response);
        verify(trainingRepository, times(1)).findById(1);

    }
}