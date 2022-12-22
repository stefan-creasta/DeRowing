package nl.tudelft.sem.template.boat.domain.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.tudelft.sem.template.boat.builders.Director;
import nl.tudelft.sem.template.boat.domain.Boat;
import nl.tudelft.sem.template.boat.domain.NetId;
import nl.tudelft.sem.template.boat.domain.Position;
import nl.tudelft.sem.template.boat.domain.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoatTest {

    private transient Director director;

    @BeforeEach
    public void setup() {
        director = new Director();
    }

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
        Boat boat = director.constructBoat(Type.PLUS4);
        boat.addRowerToPosition(Position.COX, netId);
        assertEquals(rowers, boat.getRowers());
    }

    @Test
    void removePosition() {
        HashMap<Position, Integer> requiredRowers = new HashMap<>();
        requiredRowers.put(Position.COX, 0);
        requiredRowers.put(Position.COACH, 1);
        requiredRowers.put(Position.PORT, 2);
        requiredRowers.put(Position.SCULLING, 0);
        requiredRowers.put(Position.STARBOARD, 2);
        Boat boat = director.constructBoat(Type.C4);
        boat.removePosition(Position.COX);
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
        Boat boat = director.constructBoat(Type.PLUS4);
        boat.addRowerToPosition(Position.COX, netId);
        assertTrue(boat.removeRower(netId));
        assertEquals(rowers, boat.getRowers());
    }

    @Test
    void cannotRemoveRower() {
        Boat boat = director.constructBoat(Type.C4);
        NetId netId = new NetId("a");
        assertFalse(boat.removeRower(netId));
    }

    @Test
    void cannotAddRower() {
        Boat boat = director.constructBoat(Type.C4);
        assertFalse(boat.canRowerBeAdded(Position.SCULLING));
    }

    @Test
    void canAddRower() {
        Boat boat = director.constructBoat(Type.PLUS8);
        assertTrue(boat.canRowerBeAdded(Position.COX));
    }
}