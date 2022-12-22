package nl.tudelft.sem.template.activity.models;

import nl.tudelft.sem.template.activity.domain.GenderConstraint;
import nl.tudelft.sem.template.activity.domain.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompetitionEditModelTest {

    private CompetitionEditModel model;

    @BeforeEach
    void setup() {
        model = new CompetitionEditModel();
        model.setOrganization("TUDELFT");
        model.setAllowAmateurs(true);
        model.setSingleOrganization(true);
        model.setId(123L);
        model.setStartTime(123L);
        model.setCompetitionName("name");
        model.setGenderConstraint(GenderConstraint.NO_CONSTRAINT);
        model.setNumPeople(1);
        model.setType(Type.C4);
    }

    @Test
    void getCompetitionName() {
        assertEquals("name", model.getCompetitionName());
    }

    @Test
    void getGenderConstraint() {
        assertEquals(GenderConstraint.NO_CONSTRAINT, model.getGenderConstraint());
    }

    @Test
    void isAllowAmateurs() {
        assertTrue(model.isAllowAmateurs());
    }

    @Test
    void isSingleOrganization() {
        assertTrue(model.isSingleOrganization());
    }

    @Test
    void getOrganization() {
        assertEquals("TUDELFT", model.getOrganization());
    }

    @Test
    void getStartTime() {
        assertEquals(123L, model.getStartTime());
    }

    @Test
    void getType() {
        assertEquals(Type.C4, model.getType());
    }

    @Test
    void setCompetitionName() {
        model.setCompetitionName("newName");
        assertEquals("newName", model.getCompetitionName());
    }

    @Test
    void setGenderConstraint() {
        model.setGenderConstraint(GenderConstraint.NO_CONSTRAINT);
        assertEquals(GenderConstraint.NO_CONSTRAINT, model.getGenderConstraint());
    }

    @Test
    void setAllowAmateurs() {
        model.setAllowAmateurs(false);
        assertFalse(model.isAllowAmateurs());
    }

    @Test
    void setSingleOrganization() {
        model.setSingleOrganization(false);
        assertFalse(model.isSingleOrganization());
    }

    @Test
    void setOrganization() {
        model.setOrganization("Something");
        assertEquals("Something", model.getOrganization());
    }

    @Test
    void setStartTime() {
        model.setStartTime(321L);
        assertEquals(321L, model.getStartTime());
    }

    @Test
    void setType() {
        model.setType(Type.PLUS4);
        assertEquals(Type.PLUS4, model.getType());
    }


    @Test
    void setNumPeople() {
        model.setNumPeople(2);
        assertEquals(2, model.getNumPeople());
    }

    @Test
    void setId() {
        model.setId(312L);
        assertEquals(312L, model.getId());
    }

    @Test
    void testEquals() {
        CompetitionEditModel model1 = new CompetitionEditModel();
        CompetitionEditModel model2 = new CompetitionEditModel();
        assertEquals(model1, model2);
    }

    @Test
    void canEqual() {
        CompetitionEditModel model1 = new CompetitionEditModel();
        CompetitionEditModel model2 = new CompetitionEditModel();
        assertEquals(model1, model2);
    }

    @Test
    void testHashCode() {
        CompetitionEditModel model1 = new CompetitionEditModel();
        CompetitionEditModel model2 = new CompetitionEditModel();
        assertEquals(model1.hashCode(), model2.hashCode());
    }

    @Test
    void testToString() {
        CompetitionEditModel model1 = new CompetitionEditModel();
        CompetitionEditModel model2 = new CompetitionEditModel();
        assertEquals(model1.toString(), model2.toString());
    }
}