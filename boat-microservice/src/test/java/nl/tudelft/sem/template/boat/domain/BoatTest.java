package nl.tudelft.sem.template.boat.domain;

import nl.tudelft.sem.template.user.domain.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BoatTest {

    @Test
    void addRowerToPosition() {
        Boat boat = new Boat(Type.C4, 1, 1, 1, 1, 1);
        Map<Position, List<User>> rowers = new HashMap<>();
        User user = new User();
        List<User> list = new ArrayList<>();
        list.add(user);
        rowers.put(Position.COX, list);
        rowers.put(Position.COACH, new ArrayList<>());
        rowers.put(Position.PORT, new ArrayList<>());
        rowers.put(Position.STARBOARD, new ArrayList<>());
        rowers.put(Position.SCULLING, new ArrayList<>());
        boat.addRowerToPosition(Position.COX, user);
        assertEquals(rowers, boat.getRowers());
    }

    @Test
    void removePosition() {
        Boat boat = new Boat(Type.C4, 1, 1, 1, 1, 1);
        HashMap<Position, Integer> requiredRowers = new HashMap<>();
        requiredRowers.put(Position.COX, 1);
        requiredRowers.put(Position.COACH, 1);
        requiredRowers.put(Position.PORT, 1);
        requiredRowers.put(Position.STARBOARD, 1);
        requiredRowers.put(Position.SCULLING, 0);
        boat.removePosition(Position.SCULLING);
        assertEquals(requiredRowers, boat.getRequiredRowers());
    }

    @Test
    void canRemoveRower() {
        Boat boat = new Boat(Type.C4, 1, 1, 1, 1, 1);
        HashMap<Position, List<User>> rowers = new HashMap<>();
        rowers.put(Position.COX, new ArrayList<>());
        rowers.put(Position.COACH, new ArrayList<>());
        rowers.put(Position.PORT, new ArrayList<>());
        rowers.put(Position.STARBOARD, new ArrayList<>());
        rowers.put(Position.SCULLING, new ArrayList<>());
        User user = new User();
        boat.addRowerToPosition(Position.COX, user);
        assertTrue(boat.removeRower(user));
        assertEquals(rowers, boat.getRowers());
    }

    @Test
    void cannotRemoveRower() {
        Boat boat = new Boat(Type.C4, 1, 1, 1, 1, 1);
        User user = new User();
        assertFalse(boat.removeRower(user));
    }

    @Test
    void canRowerBeAddedFalseType() {
        Boat boat = new Boat(Type.PLUS4, 1, 1, 1, 1, 1);
        assertFalse(boat.canRowerBeAdded(Type.C4, Position.SCULLING));
    }

    @Test
    void canRowerBeAddedFalsePosition() {
        Boat boat = new Boat(Type.C4, 1, 1, 1, 1, 0);
        assertFalse(boat.canRowerBeAdded(Type.C4, Position.SCULLING));
    }

    @Test
    void canRowerBeAddedFalsePositionTrue() {
        Boat boat = new Boat(Type.C4, 1, 1, 1, 1, 1);
        assertTrue(boat.canRowerBeAdded(Type.PLUS4, Position.SCULLING));
    }
}