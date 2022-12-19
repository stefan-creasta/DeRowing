package nl.tudelft.sem.template.activity.domain.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.tudelft.sem.template.activity.domain.GenderConstraint;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import nl.tudelft.sem.template.activity.domain.repositories.CompetitionRepository;
import nl.tudelft.sem.template.activity.models.CompetitionCreateModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class CompetitionServiceTest {

    private CompetitionService competitionService;

    private CompetitionCreateModel competitionCreateModel;

    @Mock
    private CompetitionRepository competitionRepository;

    private Competition competition;

    private NetId id;

    @BeforeEach
    public void setup() {
        competitionCreateModel = new CompetitionCreateModel("test", GenderConstraint.ONLY_MALE,
                123L, false, false, 123L);
        id = new NetId("123");
        competition = new Competition(id, competitionCreateModel.getCompetitionName(),
                competitionCreateModel.getBoatId(), competitionCreateModel.getStartTime(),
                competitionCreateModel.isAllowAmateurs(), competitionCreateModel.getGenderConstraint(),
                competitionCreateModel.isSingleOrganization());
        competitionRepository = mock(CompetitionRepository.class);
        competitionService = new CompetitionService(competitionRepository);
    }

    @Test
    void parseRequest() {
        Assertions.assertEquals(competition, competitionService.parseRequest(competitionCreateModel, id));
    }

    @Test
    void createCompetition() throws Exception {
        Assertions.assertEquals(competition, competitionService.createCompetition(competitionCreateModel, id));
    }

    @Test
    void findCompetitions() throws Exception {
        when(competitionRepository.existsByNetId(any())).thenReturn(true);
        when(competitionRepository.findByNetId(any())).thenReturn(competition);
        Assertions.assertEquals(competition, competitionService.findCompetitions(id));
    }
}