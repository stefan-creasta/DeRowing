package nl.tudelft.sem.template.boat.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Convert;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;

@Entity
@Table(name = "boats")
@Getter
@Setter
@NoArgsConstructor
public class Boat {
    /**
     * Identifier for the boat.
     */
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    //@Id
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "type", nullable = false)
    private Type type;

    // TODO: create connection to Users table
    @Convert(converter = RowersAttributeConverter.class)
    private Rowers rowers;
    @Convert(converter = RequiredRowersAttributeConverter.class)
    private RequiredRowers requiredRowers;

    /**
     * Create new boat.
     *
     * @param type the type of the boat
     */
    public Boat(String name, Type type, int cox, int coach, int port, int starboard, int sculling) {
        this.name = name;
        this.type = type;
        HashMap<Position, List<Rower>> newMapForRowers = new HashMap<>();
        newMapForRowers.put(Position.COX, new ArrayList<>());
        newMapForRowers.put(Position.COACH, new ArrayList<>());
        newMapForRowers.put(Position.PORT, new ArrayList<>());
        newMapForRowers.put(Position.STARBOARD, new ArrayList<>());
        newMapForRowers.put(Position.SCULLING, new ArrayList<>());
        this.rowers = new Rowers(newMapForRowers);

        HashMap<Position, Integer> newMapForRequiredRowers = new HashMap<>();
        newMapForRequiredRowers.put(Position.COX, cox);
        newMapForRequiredRowers.put(Position.COACH, coach);
        newMapForRequiredRowers.put(Position.PORT, port);
        newMapForRequiredRowers.put(Position.STARBOARD, starboard);
        newMapForRequiredRowers.put(Position.SCULLING, sculling);
        this.requiredRowers = new RequiredRowers(newMapForRequiredRowers);
    }

    public Map<Position, List<Rower>> getRowers() {
        return this.rowers.currentRowers;
    }

    public HashMap<Position, Integer> getRequiredRowers() {
        return this.requiredRowers.amountOfPositions;
    }

    /**
     * Adds rower to boat.
     *
     * @param rower the user to be added
     */
    public void addRowerToPosition(Position position, Rower rower) {
        if (this.rowers.currentRowers.get(position).size() < this.requiredRowers.amountOfPositions.get(position)) {
            this.rowers.currentRowers.get(position).add(rower);
            int val = requiredRowers.amountOfPositions.get(position);
            requiredRowers.amountOfPositions.replace(position, val - 1);
        }
    }

    /**
     * Removes the rower(s) from a certain position of the boat.
     *
     * @param position the position to be removed
     */
    public void removePosition(Position position) {
        requiredRowers.amountOfPositions.remove(position);
        requiredRowers.amountOfPositions.put(position, 0);
        rowers.currentRowers.replace(position, new ArrayList<>());
    }

    /**
     * Removes rower from the boat.
     *
     * @param rower the user to be removed
     * @return true, if the user was successfully removed, false otherwise
     */
    public boolean removeRower(Rower rower) {
        // find the key that the User is mapped to
        for (Map.Entry<Position, List<Rower>> a : rowers.currentRowers.entrySet()) {
            if (a.getValue().contains(rower)) {
                a.getValue().remove(rower);
                int val = requiredRowers.amountOfPositions.get(a.getKey());
                requiredRowers.amountOfPositions.replace(a.getKey(), val + 1);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the user is eligible to be a rower on the boat.

     * @param type the user's certificate
     * @param position the user's position
     * @return true whether the user is eligible, false otherwise
     */
    public boolean canRowerBeAdded(Type type, Position position) {
        if (type.value < this.type.value) {
            return false;
        }
        return (requiredRowers.amountOfPositions.get(position) > 0);
    }

    /**
     * Compare this Boat object to another Object.
     *
     * @param o the other object
     * @return true/false based on whether the 2 objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Boat)) {
            return false;
        }
        Boat boat = (Boat) o;
        if (!(id == boat.id && name.equals(boat.name) && type == boat.type)) {
            return false;
        }
        return Objects.equals(rowers, boat.rowers) && Objects.equals(requiredRowers, boat.requiredRowers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, rowers, requiredRowers);
    }
}
