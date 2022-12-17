package nl.tudelft.sem.template.boat.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class BoatTest {

    @Test
    void addRowerToPosition() {
        Map<Position, List<Rower>> rowers = new HashMap<>();
        Rower rower = new Rower();
        List<Rower> list = new ArrayList<>();
        list.add(rower);
        rowers.put(Position.COX, list);
        rowers.put(Position.COACH, new ArrayList<>());
        rowers.put(Position.PORT, new ArrayList<>());
        rowers.put(Position.STARBOARD, new ArrayList<>());
        rowers.put(Position.SCULLING, new ArrayList<>());
        Boat boat = new Boat(Type.C4, 1, 1, 1, 1, 1);
        boat.addRowerToPosition(Position.COX, rower);
        assertEquals(rowers, boat.getRowers());
    }

    @Test
    void removePosition() {
        HashMap<Position, Integer> requiredRowers = new HashMap<>();
        requiredRowers.put(Position.COX, 1);
        requiredRowers.put(Position.COACH, 1);
        requiredRowers.put(Position.PORT, 1);
        requiredRowers.put(Position.STARBOARD, 1);
        requiredRowers.put(Position.SCULLING, 0);
        Boat boat = new Boat(Type.C4, 1, 1, 1, 1, 1);
        boat.removePosition(Position.SCULLING);
        assertEquals(requiredRowers, boat.getRequiredRowers());
    }

    @Test
    void canRemoveRower() {
        HashMap<Position, List<Rower>> rowers = new HashMap<>();
        rowers.put(Position.COX, new ArrayList<>());
        rowers.put(Position.COACH, new ArrayList<>());
        rowers.put(Position.PORT, new ArrayList<>());
        rowers.put(Position.STARBOARD, new ArrayList<>());
        rowers.put(Position.SCULLING, new ArrayList<>());
        Rower rower = new Rower();
        Boat boat = new Boat(Type.C4, 1, 1, 1, 1, 1);
        boat.addRowerToPosition(Position.COX, rower);
        assertTrue(boat.removeRower(rower));
        assertEquals(rowers, boat.getRowers());
    }

    @Test
    void cannotRemoveRower() {
        Boat boat = new Boat(Type.C4, 1, 1, 1, 1, 1);
        Rower user = new Rower();
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