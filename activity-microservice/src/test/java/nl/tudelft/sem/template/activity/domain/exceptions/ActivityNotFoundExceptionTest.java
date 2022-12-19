package nl.tudelft.sem.template.activity.domain.exceptions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.repositories.CompetitionRepository;
import nl.tudelft.sem.template.activity.domain.repositories.TrainingRepository;
import nl.tudelft.sem.template.activity.domain.services.BoatRestService;
import nl.tudelft.sem.template.activity.domain.services.TrainingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ActivityNotFoundExceptionTest {

    private TrainingRepository trainingRepository;

    private CompetitionRepository competitionRepository;

    @Test
    void testActivityNotFoundException() {
        trainingRepository = mock(TrainingRepository.class);
        competitionRepository = mock(CompetitionRepository.class);
        TrainingService ts = new TrainingService(new BoatRestService(), competitionRepository, trainingRepository);
        when(trainingRepository.existsByNetId(any())).thenReturn(false);
        Assertions.assertThrows(ActivityNotFoundException.class, () -> {
            ts.findTraining(new NetId("123"));
        });
    }

}