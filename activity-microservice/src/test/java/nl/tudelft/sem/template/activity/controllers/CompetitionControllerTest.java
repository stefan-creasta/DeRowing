package nl.tudelft.sem.template.activity.controllers;

import nl.tudelft.sem.template.activity.authentication.AuthManager;
import nl.tudelft.sem.template.activity.domain.services.CompetitionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CompetitionControllerTest {

    private CompetitionController competitionController;

    private AuthManager authManager;

    private CompetitionService competitionService;

    @BeforeEach
    public void setup() {
        
    }

    @Test
    void helloWorld() {
    }

    @Test
    void createCompetition() {
    }

    @Test
    void findCompetitions() {
    }
}