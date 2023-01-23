package nl.tudelft.sem.template.activity.controllers;

import nl.tudelft.sem.template.activity.authentication.AuthManager;
import nl.tudelft.sem.template.activity.domain.entities.Training;
import nl.tudelft.sem.template.activity.domain.services.TrainingServiceServerSide;
import nl.tudelft.sem.template.activity.domain.services.TrainingServiceUserSide;
import nl.tudelft.sem.template.activity.models.JoinRequestModel;
import nl.tudelft.sem.template.activity.models.PositionEntryModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TrainingControllerEditTest {

    @Mock
    private AuthManager authManager;

    @Mock
    private TrainingServiceUserSide trainingServiceUserSide;

    private TrainingControllerEdit trainingControllerEdit;

    private JoinRequestModel joinRequestModel;

    private PositionEntryModel positionEntryModel;

    @BeforeEach
    public void setup() {
        authManager = mock(AuthManager.class);
        joinRequestModel = new JoinRequestModel();
        positionEntryModel = new PositionEntryModel();
        trainingServiceUserSide = mock(TrainingServiceUserSide.class);
        trainingControllerEdit = new TrainingControllerEdit(authManager, trainingServiceUserSide);
    }

    @Test
    void joinTrainingTestException() throws Exception {
        when(trainingServiceUserSide.joinTraining(joinRequestModel)).thenThrow(Exception.class);
        Assertions.assertEquals(new ResponseEntity<>("Internal error when joining training.", HttpStatus.valueOf(200)),
                trainingControllerEdit.joinTraining(joinRequestModel));
    }

    @Test
    void getTrainingsTestException() throws Exception {
        Exception e = new Exception();
        when(trainingServiceUserSide.getSuitableCompetition(positionEntryModel.getPosition())).thenThrow(e);
        Assertions.assertThrows(ResponseStatusException.class, () -> {trainingControllerEdit.getTrainings(positionEntryModel);});
    }

    @Test
    void getTrainingsTestNormal() throws Exception {
        List<Training> sol = new ArrayList<>();
        when(trainingServiceUserSide.getSuitableCompetition(positionEntryModel.getPosition())).thenReturn(sol);
        Assertions.assertEquals(new ResponseEntity<>(sol, HttpStatus.valueOf(200)),
                trainingControllerEdit.getTrainings(positionEntryModel));
    }
}