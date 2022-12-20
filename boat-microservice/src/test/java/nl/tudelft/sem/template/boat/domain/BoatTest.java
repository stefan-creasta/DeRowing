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
        Map<Position, List<NetId>> rowers = new HashMap<>();
        NetId netId = new NetId("a");
        List<NetId> list = new ArrayList<>();
        list.add(netId);
        rowers.put(Position.COX, list);
        rowers.put(Position.COACH, new ArrayList<>());
        rowers.put(Position.PORT, new ArrayList<>());
        rowers.put(Position.STARBOARD, new ArrayList<>());
        rowers.put(Position.SCULLING, new ArrayList<>());
        Boat boat = new Boat("boat", Type.C4, 1, 1, 1, 1, 1);
        boat.addRowerToPosition(Position.COX, netId);
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
        Boat boat = new Boat("boat", Type.C4, 1, 1, 1, 1, 1);
        boat.removePosition(Position.SCULLING);
        assertEquals(requiredRowers, boat.getRequiredRowers());
    }

    @Test
    void canRemoveRower() {
        HashMap<Position, List<NetId>> rowers = new HashMap<>();
        rowers.put(Position.COX, new ArrayList<>());
        rowers.put(Position.COACH, new ArrayList<>());
        rowers.put(Position.PORT, new ArrayList<>());
        rowers.put(Position.STARBOARD, new ArrayList<>());
        rowers.put(Position.SCULLING, new ArrayList<>());
        NetId netId = new NetId();
        Boat boat = new Boat("boat", Type.C4, 1, 1, 1, 1, 1);
        boat.addRowerToPosition(Position.COX, netId);
        assertTrue(boat.removeRower(netId));
        assertEquals(rowers, boat.getRowers());
    }

    @Test
    void cannotRemoveRower() {
        Boat boat = new Boat("boat", Type.C4, 1, 1, 1, 1, 1);
        NetId netId = new NetId();
        assertFalse(boat.removeRower(netId));
    }

    @Test
    void canRowerBeAddedFalsePosition() {
        Boat boat = new Boat("boat", Type.PLUS4, 0, 1, 1, 1, 1);
        assertFalse(boat.canRowerBeAdded(Position.COX));
    }

    @Test
    void canRowerBeAddedFalsePosition2() {
        Boat boat = new Boat("boat", Type.C4, 1, 1, 1, 1, 0);
        assertFalse(boat.canRowerBeAdded(Position.SCULLING));
    }

    @Test
    void canRowerBeAddedFalsePositionTrue() {
        Boat boat = new Boat("boat", Type.C4, 1, 1, 1, 1, 1);
        assertTrue(boat.canRowerBeAdded(Position.SCULLING));
    }
}