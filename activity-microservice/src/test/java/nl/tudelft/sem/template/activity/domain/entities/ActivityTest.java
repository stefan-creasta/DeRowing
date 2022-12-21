package nl.tudelft.sem.template.activity.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import nl.tudelft.sem.template.activity.domain.GenderConstraint;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ActivityTest {

    private Activity test;

    @BeforeEach
    public void setup() {
        test = new Training(new NetId("123"), "name", 123L, 123L, 1,
                Type.C4);
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
        assertEquals("name", test.getActivityName());
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
                        + " The name is: name\n"
                        + " The boatId is: 123\n"
                        + " The start time is: 123",
                toString
        );
    }
}
