package nl.tudelft.sem.template.activity.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.sem.template.activity.domain.entities.Competition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class FindSuitableCompetitionResponseModelTest {

    private FindSuitableCompetitionResponseModel model;

    private List<Long> boatId;

    @BeforeEach
    void setUp() {
        boatId = new ArrayList<>();
        model = new FindSuitableCompetitionResponseModel();
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
        FindSuitableCompetitionResponseModel model2 = new FindSuitableCompetitionResponseModel();
        model2.setBoatId(boatId);
        assertEquals(model, model2);
    }

    @Test
    void canEqual() {
        assertFalse(model.canEqual(null));
        assertTrue(model.canEqual(new FindSuitableCompetitionResponseModel()));
    }

    @Test
    void testHashCode() {
        FindSuitableCompetitionResponseModel model2 = new FindSuitableCompetitionResponseModel();
        model2.setBoatId(boatId);
        assertEquals(model.hashCode(), model2.hashCode());
    }

    @Test
    void testToString() {
        assertEquals("FindSuitableCompetitionResponseModel(boatId=" + boatId + ")",
                model.toString());
    }
}