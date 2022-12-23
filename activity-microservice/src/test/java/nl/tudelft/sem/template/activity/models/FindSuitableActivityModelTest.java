package nl.tudelft.sem.template.activity.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.sem.template.activity.domain.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class FindSuitableActivityModelTest {

    private FindSuitableActivityModel model;

    private List<Long> competitions;

    @BeforeEach
    void setUp() {
        competitions = new ArrayList<>();
        model = new FindSuitableActivityModel(competitions, Position.COACH);
    }


    @Test
    void setPosition() {
        model.setPosition(Position.COX);
        assertEquals(Position.COX, model.getPosition());
    }

    @Test
    void testEquals() {
        FindSuitableActivityModel model = new FindSuitableActivityModel(competitions, Position.COACH);
        FindSuitableActivityModel model2 = new FindSuitableActivityModel(competitions, Position.COACH);
        FindSuitableActivityModel model3 = new FindSuitableActivityModel(competitions, Position.COX);
        assertTrue(model.equals(model2));
        assertFalse(model.equals(model3));
    }

    @Test
    void canEqual() {
        FindSuitableActivityModel model = new FindSuitableActivityModel(competitions, Position.COACH);
        assertTrue(model.canEqual(new FindSuitableActivityModel(competitions, Position.COACH)));
    }

    @Test
    void testHashCode() {
        FindSuitableActivityModel model = new FindSuitableActivityModel(competitions, Position.COACH);
        FindSuitableActivityModel model2 = new FindSuitableActivityModel(competitions, Position.COACH);
        FindSuitableActivityModel model3 = new FindSuitableActivityModel(competitions, Position.COX);
        assertEquals(model.hashCode(), model2.hashCode());
        assertNotEquals(model.hashCode(), model3.hashCode());
    }

    @Test
    void testToString() {
        FindSuitableActivityModel model = new FindSuitableActivityModel(competitions, Position.COACH);
        assertEquals("FindSuitableCompetitionModel(competitions=" + competitions
                + ", position=" + Position.COACH + ")", model.toString());
    }
}