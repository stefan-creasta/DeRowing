package nl.tudelft.sem.template.boat.domain;

import org.hibernate.hql.spi.PositionalParameterInformation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BoatTest {

    @Test
    void addRowerToPosition() {
        Boat boat = new Boat(Type.C4);
        Map<Position, User> rowers = new HashMap<>();
        User user = new User();
        rowers.put(Position.COX, user);
        boat.addRowerToPosition(Position.COX, user);
        assertTrue(rowers.equals(boat.getRowers()));
    }

    @Test
    void removePosition() {
    }

    @Test
    void removeRower() {
        Boat boat = new Boat(Type.C4);
        Map<Position, User> rowers = new HashMap<>();
        User user = new User();
        boat.addRowerToPosition(Position.COX, user);
        boat.removeRower(user);
        assertTrue(rowers.equals(boat.getRowers()));
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }
}