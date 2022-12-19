package nl.tudelft.sem.template.activity.domain.repositories;

import java.util.List;
import java.util.Optional;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.entities.Training;
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