package nl.tudelft.sem.template.activity.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.jsonwebtoken.lang.Assert;
import nl.tudelft.sem.template.activity.authentication.AuthManager;
import nl.tudelft.sem.template.activity.domain.GenderConstraint;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import nl.tudelft.sem.template.activity.domain.repositories.CompetitionRepository;
import nl.tudelft.sem.template.activity.domain.services.CompetitionService;
import nl.tudelft.sem.template.activity.models.CompetitionCreateModel;
import nl.tudelft.sem.template.activity.models.CompetitionFindModel;
import nl.tudelft.sem.template.activity.profiles.MockAuthenticationManagerProfile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class CompetitionControllerTest {

    private CompetitionController competitionController;

    @Mock
    private AuthManager authManager;

    private CompetitionService competitionService;

    @Mock
    private CompetitionRepository competitionRepository;

    private CompetitionCreateModel competitionCreateModel;

    private Competition competition;

    private CompetitionFindModel competitionFindModel;

    @BeforeEach
    public void setup() {
        this.authManager = mock(AuthManager.class);
        competitionRepository = mock(CompetitionRepository.class);
        competitionService = new CompetitionService(competitionRepository);
        competitionController = new CompetitionController(authManager, competitionService);
        competitionCreateModel = new CompetitionCreateModel("test", GenderConstraint.ONLY_MALE,
                123L, false, false, 123L);
        competitionFindModel = new CompetitionFindModel(new NetId("123"));
        competition = new Competition(new NetId("123"), competitionCreateModel.getCompetitionName(),
                competitionCreateModel.getBoatId(), competitionCreateModel.getStartTime(),
                competitionCreateModel.isAllowAmateurs(), competitionCreateModel.getGenderConstraint(),
                competitionCreateModel.isSingleOrganization());
    }

    @Test
    void helloWorld() {
        when(authManager.getNetId()).thenReturn("123");
        Assertions.assertEquals(new ResponseEntity<>("Hello 123", HttpStatus.valueOf(200))
                , competitionController.helloWorld());
    }

    @Test
    void createCompetition() throws Exception {

        when(authManager.getNetId()).thenReturn("123");
        when(competitionService.createCompetition(competitionCreateModel, any())).thenReturn(competition);

        Assertions.assertEquals(new ResponseEntity<>("Done! The competition test is created by 123"
                        , HttpStatus.valueOf(200))
                , competitionController.createCompetition(competitionCreateModel));
    }

    @Test
    void findCompetitions() throws Exception {
        when(authManager.getNetId()).thenReturn("123");
        when(competitionService.findCompetitions(any())).thenReturn(competition);
        Assertions.assertEquals(new ResponseEntity<>("The competition created by 123 is found. " +
                "Here is the competition: " + competition.toString(), HttpStatus.valueOf(200))
                , competitionController.findCompetitions(competitionFindModel));
    }
}