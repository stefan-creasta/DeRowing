package nl.tudelft.sem.template.activity.domain.repositories;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.tudelft.sem.template.activity.domain.GenderConstraint;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CompetitionRepositoryTest {

    @Mock
    private CompetitionRepository competitionRepository;

    private Competition competition;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        competition = new Competition(new NetId("123"), "testname", 123L, 123L,
                false, GenderConstraint.NO_CONSTRAINT, false);
        competitionRepository = mock(CompetitionRepository.class);
    }

    @Test
    void findByNetId() {
        when(this.competitionRepository.findByNetId(any())).thenReturn(competition);
        Assertions.assertEquals(competition, this.competitionRepository.findByNetId(new NetId("123")));
    }

    @Test
    void existsByNetId() {
        when(this.competitionRepository.existsByNetId(any())).thenReturn(false);
        Assertions.assertFalse(competitionRepository.existsByNetId(new NetId("123")));
    }
}