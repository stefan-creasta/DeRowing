package nl.tudelft.sem.template.user.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.sem.template.user.domain.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    private User sut;

    @BeforeEach
    public void setup() {
        sut = new User(new NetId("123"), Gender.MALE, Certificate.PLUS4, "Proteus", false);
    }

    @Test
    void testToString() {
        String toString = sut.toString();
        assertEquals(
                "Your NetId: 123\n"
                        + " Gender: MALE\n"
                        + " Certification: PLUS4\n"
                        + " Organization: Proteus\n"
                        + " Status: Professional", toString);
    }
}
