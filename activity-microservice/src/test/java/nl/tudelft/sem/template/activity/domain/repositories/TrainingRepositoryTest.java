package nl.tudelft.sem.template.activity.domain.repositories;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.entities.Training;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class TrainingRepositoryTest {

    @Mock
    private TrainingRepository trainingRepository;

    private Training training;

    @BeforeEach
    public void setup() {
        training = new Training(new NetId("123"), "testname", 123L, 123L);
        trainingRepository = mock(TrainingRepository.class);
    }

    @Test
    void findByNetId() {
        when(trainingRepository.findByNetId(any())).thenReturn(training);
        Assertions.assertEquals(training, trainingRepository.findByNetId(new NetId("123")));
    }

    @Test
    void existsByNetId() {
        when(trainingRepository.existsByNetId(any())).thenReturn(false);
        Assertions.assertFalse(trainingRepository.existsByNetId(new NetId("123")));
    }
}