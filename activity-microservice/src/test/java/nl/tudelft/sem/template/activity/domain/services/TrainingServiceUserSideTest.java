package nl.tudelft.sem.template.activity.domain.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Position;
import nl.tudelft.sem.template.activity.domain.Type;
import nl.tudelft.sem.template.activity.domain.entities.Training;
import nl.tudelft.sem.template.activity.domain.events.EventPublisher;
import nl.tudelft.sem.template.activity.domain.provider.implement.CurrentTimeProvider;
import nl.tudelft.sem.template.activity.domain.repositories.TrainingRepository;
import nl.tudelft.sem.template.activity.models.FindSuitableActivityModel;
import nl.tudelft.sem.template.activity.models.FindSuitableActivityResponseModel;
import nl.tudelft.sem.template.activity.models.UserDataRequestModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

class TrainingServiceUserSideTest {

    @Mock
    private EventPublisher eventPublisher;

    @Mock
    private RestServiceFacade restServiceFacade;

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private CurrentTimeProvider currentTimeProvider;

    private TrainingServiceUserSide trainingServiceUserSide;

    @BeforeEach
    public void setup() {
        eventPublisher = mock(EventPublisher.class);
        restServiceFacade = mock(RestServiceFacade.class);
        trainingRepository = mock(TrainingRepository.class);
        currentTimeProvider = mock(CurrentTimeProvider.class);
        trainingServiceUserSide = new TrainingServiceUserSide(eventPublisher, restServiceFacade,
                trainingRepository, currentTimeProvider);
    }

    @Test
    void getSuitableTrainingsTestException() throws Exception {
        when(restServiceFacade.performUserModel(null, "/getdetails", UserDataRequestModel.class))
                .thenReturn(null);
        Assertions.assertThrows(Exception.class, () -> {
            trainingServiceUserSide.getSuitableTrainings(Position.COX);
        });
    }

    @Test
    void getSuitableTrainingsTestNormal() throws Exception {
        UserDataRequestModel userDataRequestModel = mock(UserDataRequestModel.class);
        when(restServiceFacade.performUserModel(null, "/getdetails", UserDataRequestModel.class))
                .thenReturn(userDataRequestModel);
        List<Training> list = new ArrayList<>();
        NetId netId1 = mock(NetId.class);
        NetId netId2 = mock(NetId.class);
        NetId netId3 = mock(NetId.class);
        Training training1 = new Training(netId1, "abc1", 1L, 1L + (30 * 60 * 1000), Type.C4);
        Training training2 = new Training(netId2, "abc2", 2L, (30 * 60 * 1000) - 1L, Type.C4);
        Training training3 = new Training(netId3, "abc3", 3L, (30 * 60 * 1000), Type.C4);

        list.add(training1);
        list.add(training2);
        list.add(training3);

        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        FindSuitableActivityResponseModel suitableCompetitions = new FindSuitableActivityResponseModel();
        suitableCompetitions.setBoatId(ids);
        when(trainingRepository.findAll()).thenReturn(list);
        Instant instantEx = Instant.parse("1970-01-01T00:00:00Z");
        when(currentTimeProvider.getCurrentTime()).thenReturn(instantEx);
        FindSuitableActivityModel model = new FindSuitableActivityModel(ids, Position.COX);
        when((FindSuitableActivityResponseModel) restServiceFacade.performBoatModel(model,
                "/boat/check", FindSuitableActivityResponseModel.class)).thenReturn(suitableCompetitions);
        List<Training> sol = new ArrayList<>();
        sol.add(training1);
        when(trainingRepository.findAllByBoatIdIn(suitableCompetitions.getBoatId())).thenReturn(sol);
        Assertions.assertEquals(sol, trainingServiceUserSide.getSuitableTrainings(Position.COX));
    }
}