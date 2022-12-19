package nl.tudelft.sem.template.activity.controllers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.tudelft.sem.template.activity.authentication.AuthManager;
import nl.tudelft.sem.template.activity.domain.repositories.CompetitionRepository;
import nl.tudelft.sem.template.activity.domain.services.CompetitionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
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

    @BeforeEach
    public void setup() {
        authManager = mock(AuthManager.class);
        competitionRepository = mock(CompetitionRepository.class);
        competitionService = new CompetitionService(competitionRepository);
        competitionController = new CompetitionController(authManager, competitionService);
    }

    @Test
    void helloWorld() {
        when(authManager.getNetId()).thenReturn("123");
        when(competitionController.helloWorld()).thenReturn(ResponseEntity.ok("Hello 123"));
        Assertions.assertEquals(ResponseEntity.ok("Hello 123"), competitionController.helloWorld());
    }

    @Test
    void createCompetition() {
    }

    @Test
    void findCompetitions() {
    }
}