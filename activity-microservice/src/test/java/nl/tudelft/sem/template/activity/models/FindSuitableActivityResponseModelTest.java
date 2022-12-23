package nl.tudelft.sem.template.activity.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class FindSuitableActivityResponseModelTest {

    private FindSuitableActivityResponseModel model;

    private List<Long> boatId;

    @BeforeEach
    void setUp() {
        boatId = new ArrayList<>();
        model = new FindSuitableActivityResponseModel();
        model.setBoatId(boatId);
    }

    @Test
    void getCompetitions() {
        assertEquals(boatId, model.getBoatId());
    }

    @Test
    void setCompetitions() {
        List<Long> boatId2 = new ArrayList<>();
        model.setBoatId(boatId2);
        assertEquals(boatId2, model.getBoatId());
    }

    @Test
    void testEquals() {
        FindSuitableActivityResponseModel model2 = new FindSuitableActivityResponseModel();
        model2.setBoatId(boatId);
        assertEquals(model, model2);
    }

    @Test
    void canEqual() {
        assertFalse(model.canEqual(null));
        assertTrue(model.canEqual(new FindSuitableActivityResponseModel()));
    }

    @Test
    void testHashCode() {
        FindSuitableActivityResponseModel model2 = new FindSuitableActivityResponseModel();
        model2.setBoatId(boatId);
        assertEquals(model.hashCode(), model2.hashCode());
    }

    @Test
    void testToString() {
        assertEquals("FindSuitableCompetitionResponseModel(boatId=" + boatId + ")",
                model.toString());
    }
}