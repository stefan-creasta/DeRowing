package nl.tudelft.sem.template.activity.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.sem.template.activity.domain.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainingEditModelTest {

    private TrainingEditModel trainingEditModel;

    private String trainingName;

    private long startTime;

    private Type type;

    private int numPeople;

    private long id;

    @BeforeEach
    void setUp() {
        trainingEditModel = new TrainingEditModel();
        trainingName = "trainingName";
        startTime = 1L;
        type = Type.C4;
        numPeople = 1;
        id = 1L;
        trainingEditModel.setTrainingName(trainingName);
        trainingEditModel.setStartTime(startTime);
        trainingEditModel.setType(type);
        trainingEditModel.setNumPeople(numPeople);
        trainingEditModel.setId(id);
    }

    @Test
    void getTrainingName() {
        assertEquals(trainingName, trainingEditModel.getTrainingName());
    }

    @Test
    void getStartTime() {
        assertEquals(startTime, trainingEditModel.getStartTime());
    }

    @Test
    void getType() {
        assertEquals(type, trainingEditModel.getType());
    }

    @Test
    void getNumPeople() {
        assertEquals(numPeople, trainingEditModel.getNumPeople());
    }

    @Test
    void getId() {
        assertEquals(id, trainingEditModel.getId());
    }

    @Test
    void setTrainingName() {
        trainingEditModel.setTrainingName("newTrainingName");
        assertEquals("newTrainingName", trainingEditModel.getTrainingName());
    }

    @Test
    void setStartTime() {
        trainingEditModel.setStartTime(2L);
        assertEquals(2L, trainingEditModel.getStartTime());
    }

    @Test
    void setType() {
        trainingEditModel.setType(Type.PLUS4);
        assertEquals(Type.PLUS4, trainingEditModel.getType());
    }

    @Test
    void setNumPeople() {
        trainingEditModel.setNumPeople(2);
        assertEquals(2, trainingEditModel.getNumPeople());
    }

    @Test
    void setId() {
        trainingEditModel.setId(2L);
        assertEquals(2L, trainingEditModel.getId());
    }

    @Test
    void testEquals() {
        TrainingEditModel trainingEditModel2 = new TrainingEditModel();
        trainingEditModel2.setTrainingName(trainingName);
        trainingEditModel2.setStartTime(startTime);
        trainingEditModel2.setType(type);
        trainingEditModel2.setNumPeople(numPeople);
        trainingEditModel2.setId(id);
        assertEquals(trainingEditModel, trainingEditModel2);
    }

    @Test
    void canEqual() {
        assertFalse(trainingEditModel.canEqual(null));
        assertFalse(trainingEditModel.canEqual(new Object()));
        assertTrue(trainingEditModel.canEqual(new TrainingEditModel()));
    }

    @Test
    void testHashCode() {
        TrainingEditModel trainingEditModel2 = new TrainingEditModel();
        trainingEditModel2.setTrainingName(trainingName);
        trainingEditModel2.setStartTime(startTime);
        trainingEditModel2.setType(type);
        trainingEditModel2.setNumPeople(numPeople);
        trainingEditModel2.setId(id);
        assertEquals(trainingEditModel.hashCode(), trainingEditModel2.hashCode());
    }

    @Test
    void testToString() {
        assertEquals("TrainingEditModel(trainingName=trainingName, startTime=1, type=C4, numPeople=1, id=1)",
                trainingEditModel.toString());
    }
}