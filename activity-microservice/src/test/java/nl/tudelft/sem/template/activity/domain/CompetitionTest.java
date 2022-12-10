package nl.tudelft.sem.template.activity.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompetitionTest {

    private Competition sut;
    @BeforeEach
    public void setup() {
        sut = new Competition(new NetId("123"), "testname", 123L, 123L, false, GenderConstraint.NO_CONSTRAINT, false);
    }
    @Test
    void testToString() {
        String toString = sut.toString();
        assertEquals(
                "The competition is created by: 123\n" +
                        " The name is: testname\n" +
                        " The boatId is: 123\n" +
                        " The start time is: 123\n" +
                        " Allow Amateurs: false\n" +
                        " Gender Constraint: NO_CONSTRAINT\n" +
                        " singleOrganization: false",
                toString
        );
    }

    @Test
    void isAllowAmateurs() {
        assertFalse(sut.isAllowAmateurs());
    }

    @Test
    void setAllowAmateurs() {
        sut.setAllowAmateurs(true);
        assertTrue(sut.isAllowAmateurs());
    }

    @Test
    void getGenderConstraint() {
        assertEquals(GenderConstraint.NO_CONSTRAINT, sut.getGenderConstraint());
    }

    @Test
    void setGenderConstraint() {
        sut.setGenderConstraint(GenderConstraint.ONLY_MALE);
        assertEquals(GenderConstraint.ONLY_MALE, sut.getGenderConstraint());
    }

    @Test
    void isSingleOrganization() {
        assertFalse(sut.isSingleOrganization());
    }

    @Test
    void setSingleOrganization() {
        sut.setSingleOrganization(true);
        assertTrue(sut.isSingleOrganization());
    }
}