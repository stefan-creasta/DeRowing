package nl.tudelft.sem.template.activity.controllers;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.tudelft.sem.template.activity.domain.services.TrainingServiceUserSide;
import nl.tudelft.sem.template.activity.models.AcceptRequestModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class TrainingControllerUserTest {

    @Mock
    private TrainingServiceUserSide trainingServiceUserSide;

    private TrainingControllerUser trainingControllerUser;

    private AcceptRequestModel acceptRequestModel;

    @BeforeEach
    public void setup() {
        acceptRequestModel = new AcceptRequestModel();
        trainingServiceUserSide = mock(TrainingServiceUserSide.class);
        trainingControllerUser = new TrainingControllerUser(trainingServiceUserSide);
    }

    @Test
    void informUserTestNormal() {
        when(trainingServiceUserSide.informUser(acceptRequestModel)).thenReturn("abc");
        Assertions.assertEquals(new ResponseEntity<>("abc", HttpStatus.valueOf(200)),
                trainingControllerUser.informUser(acceptRequestModel));
    }
}