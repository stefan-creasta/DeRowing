package nl.tudelft.sem.template.activity.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.jsonwebtoken.lang.Assert;
import nl.tudelft.sem.template.activity.authentication.AuthManager;
import nl.tudelft.sem.template.activity.domain.GenderConstraint;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Type;
import nl.tudelft.sem.template.activity.domain.entities.Competition;
import nl.tudelft.sem.template.activity.domain.events.EventPublisher;
import nl.tudelft.sem.template.activity.domain.provider.implement.CurrentTimeProvider;
import nl.tudelft.sem.template.activity.domain.repositories.CompetitionRepository;
import nl.tudelft.sem.template.activity.domain.services.BoatRestService;
import nl.tudelft.sem.template.activity.domain.services.CompetitionService;
import nl.tudelft.sem.template.activity.domain.services.UserRestService;
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

    @Mock
    private EventPublisher eventPublisher;

    private CompetitionCreateModel competitionCreateModel;

    private Competition competition;

    private CompetitionFindModel competitionFindModel;

    @Mock
    private UserRestService userRestService;

    @Mock
    private BoatRestService boatRestService;

    @Mock
    private CurrentTimeProvider currentTimeProvider;


    @BeforeEach
    public void setup() {
        this.authManager = mock(AuthManager.class);
        competitionRepository = mock(CompetitionRepository.class);
        currentTimeProvider = mock(CurrentTimeProvider.class);
        boatRestService = mock(BoatRestService.class);
        userRestService = mock(UserRestService.class);
        competitionService = new CompetitionService(eventPublisher, competitionRepository,
                userRestService, boatRestService, currentTimeProvider);
        competitionController = new CompetitionController(authManager, competitionService);
        competitionFindModel = new CompetitionFindModel(new NetId("123"));
        competitionCreateModel = new CompetitionCreateModel("name", GenderConstraint.ONLY_MALE, false,
        false, "TUDELFT", 123L,
        Type.C4, 1);
        competition = new Competition(new NetId("123"), competitionCreateModel.getCompetitionName(),
                123L, 123L, 1, competitionCreateModel.isAllowAmateurs(),
                competitionCreateModel.getGenderConstraint(), competitionCreateModel.isSingleOrganization(),
                competitionCreateModel.getOrganization(), competitionCreateModel.getType());
    }

    @Test
    void helloWorld() {
        when(authManager.getNetId()).thenReturn("123");
        Assertions.assertEquals(new ResponseEntity<>("Hello 123", HttpStatus.valueOf(200)),
                competitionController.helloWorld());
    }

    @Test
    void createCompetition() {

    }


}