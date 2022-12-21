package nl.tudelft.sem.template.boat.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    public Boat(String name, Type type) {
        this.name = name;
        this.type = type;
        HashMap<Position, List<NetId>> newMapForRowers = new HashMap<>();
        newMapForRowers.put(Position.COX, new ArrayList<>());
        newMapForRowers.put(Position.COACH, new ArrayList<>());
        newMapForRowers.put(Position.PORT, new ArrayList<>());
        newMapForRowers.put(Position.STARBOARD, new ArrayList<>());
        newMapForRowers.put(Position.SCULLING, new ArrayList<>());
        this.rowers = new Rowers(newMapForRowers);

        assignPositionLimits();
    }

    /**
     * Method for assigning the required number of rowers for each position based on the boat type.
     */
    private void assignPositionLimits() {
        HashMap<Position, Integer> newMapForRequiredRowers = new HashMap<>();
        newMapForRequiredRowers.put(Position.COACH, 1); // all boats have 1 coach

        // assign the required number of rowers for each position based on the boat type
        switch (this.type) {
            case C4:
                newMapForRequiredRowers.put(Position.PORT, 2);
                newMapForRequiredRowers.put(Position.STARBOARD, 2);
                newMapForRequiredRowers.put(Position.SCULLING, 0);
                break;
            case PLUS4:
                newMapForRequiredRowers.put(Position.COX, 1);
                newMapForRequiredRowers.put(Position.PORT, 2);
                newMapForRequiredRowers.put(Position.STARBOARD, 2);
                newMapForRequiredRowers.put(Position.SCULLING, 0);
                break;
            case PLUS8:
                newMapForRequiredRowers.put(Position.COX, 1);
                newMapForRequiredRowers.put(Position.PORT, 4);
                newMapForRequiredRowers.put(Position.STARBOARD, 4);
                newMapForRequiredRowers.put(Position.SCULLING, 0);
                break;
            default:
                break;
        }

        this.requiredRowers = new RequiredRowers(newMapForRequiredRowers);
    }

    public Map<Position, List<NetId>> getRowers() {
        return this.rowers.currentRowers;
    }

    public HashMap<Position, Integer> getRequiredRowers() {
        return this.requiredRowers.amountOfPositions;
    }

    /**
     * Adds rower to boat.
     *
     * @param currentNetId the user's netId to be added
     */
    public void addRowerToPosition(Position position, NetId currentNetId) {
        if (this.rowers.currentRowers.get(position).size() < this.requiredRowers.amountOfPositions.get(position)) {
            this.rowers.currentRowers.get(position).add(currentNetId);
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
     * @param currentNetId the user's netId to be removed
     * @return true, if the user was successfully removed, false otherwise
     */
    public boolean removeRower(NetId currentNetId) {
        // find the key that the User is mapped to
        for (Map.Entry<Position, List<NetId>> a : rowers.currentRowers.entrySet()) {
            if (a.getValue().contains(currentNetId)) {
                a.getValue().remove(currentNetId);
                int val = requiredRowers.amountOfPositions.get(a.getKey());
                requiredRowers.amountOfPositions.replace(a.getKey(), val + 1);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the user can be added as a rower on the boat.

     * @param position the user's position
     * @return true whether the user is eligible, false otherwise
     */
    public boolean canRowerBeAdded(Position position) {
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
