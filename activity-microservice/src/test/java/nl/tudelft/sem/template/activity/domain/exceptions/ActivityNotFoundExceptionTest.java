package nl.tudelft.sem.template.activity.domain.exceptions;

import java.util.List;
import java.util.Optional;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import nl.tudelft.sem.template.activity.domain.entities.Training;
import nl.tudelft.sem.template.activity.domain.repositories.CompetitionRepository;
import nl.tudelft.sem.template.activity.domain.repositories.TrainingRepository;
import nl.tudelft.sem.template.activity.domain.services.BoatRestService;
import nl.tudelft.sem.template.activity.domain.services.TrainingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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