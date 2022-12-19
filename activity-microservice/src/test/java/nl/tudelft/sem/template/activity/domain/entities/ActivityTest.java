package nl.tudelft.sem.template.activity.domain.entities;

import nl.tudelft.sem.template.activity.domain.GenderConstraint;
import nl.tudelft.sem.template.activity.domain.NetId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActivityTest {

    private Activity test;

    @BeforeEach
    public void setup() {
        test = new Competition(new NetId("123"), "TestActivity", 123L, 123L,
                false, GenderConstraint.NO_CONSTRAINT, false);
    }
    @Test
    void getNetId() {
        assertEquals(new NetId("123"), test.getNetId());
    }

    @Test
    void setNetId() {
        test.setNetId(new NetId("321"));
        assertEquals(new NetId("321"), test.getNetId());
    }

    @Test
    void getActivityName() {
        assertEquals("TestActivity", test.getActivityName());
    }

    @Test
    void setActivityName() {
        test.setActivityName("SomethingNew");
        assertEquals("SomethingNew", test.getActivityName());
    }

    @Test
    void getBoatId() {
        assertEquals(123L, test.getBoatId());
    }

    @Test
    void setBoatId() {
        test.setBoatId(321L);
        assertEquals(321L, test.getBoatId());
    }

    @Test
    void getStartTime() {
        assertEquals(123L, test.getStartTime());
    }

    @Test
    void setStartTime() {
        test.setStartTime(321L);
        assertEquals(321L, test.getStartTime());
    }

    @Test
    void testToString() {
        String toString = test.toString();
        assertEquals(
                "The activity is created by: 123\n"
                        + " The name is: TestActivity\n"
                        + " The boatId is: 123\n"
                        + " The start time is: 123\n"
                        + " Allow Amateurs: false\n"
                        + " Gender Constraint: NO_CONSTRAINT\n"
                        + " singleOrganization: false",
                toString
        );
    }
}
