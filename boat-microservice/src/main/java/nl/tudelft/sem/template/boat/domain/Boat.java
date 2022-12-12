package nl.tudelft.sem.template.boat.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "boats")
@Getter @Setter @NoArgsConstructor
public class Boat {
    /**
     * Identifier for the boat.
     */
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "type", nullable = false)
    private Type type;

    // TODO: create connection to Users table
    @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "users")
    private Map<Position, List<User>> rowers;

    @OneToMany
    private HashMap<Position, Integer> requiredRowers;
    /**
     * Create new boat.
     *
     * @param type the type of the boat
     */
    public Boat(Type type, int cox, int coach, int port, int starboard, int sculling) {
        this.type = type;
        this.rowers = new HashMap<String, List<User>>();
        this.requiredRowers = new HashMap<Position, Integer>();
        this.requiredRowers.put(Position.COX, cox);
        this.requiredRowers.put(Position.COACH, coach);
        this.requiredRowers.put(Position.PORT, port);
        this.requiredRowers.put(Position.STARBOARD, starboard);
        this.requiredRowers.put(Position.SCULLING, sculling);

    }

    public Map<Position, List<User>> getRowers() {
        return this.rowers;
    }

    public HashMap<Position, Integer> getRequiredRowers() {
        return this.requiredRowers;
    }
    /**
     * Adds rower to boat.
     *
     * @param user the user to be added
     */
    public void addRowerToPosition(Position position, User user) {
        List<User> newList = rowers.get(position);
        rowers.remove(position);
        newList.add(user);
        rowers.put(position, newList);
        int val = requiredRowers.get(position);
        requiredRowers.replace(position, val - 1);
    }

    /**
     * Removes the rower(s) from a certain position of the boat.
     *
     * @param position the position to be removed
     */
    public void removePosition(Position position) {
        requiredRowers.remove(position);
        requiredRowers.put(position, 0);
        rowers.replace(position, new ArrayList<User>());
    }

    /**
     * Removes rower from the boat.
     *
     * @param user the user to be removed
     * @return true, if the user was successfully removed, false otherwise
     */
    public boolean removeRower(User user) {
        // find the key that the User is mapped to
        int pos = -1;
        for(auto a : rowers) {
            if(a.second.contains(user)) {
                a.second.remove(user);
                int val = requiredRowers.get(a.first);
                requiredRowers.replace(key, val + 1);
                return true;
            }
        }
        return false;
        /**rowers.forEach((key, value) -> {
            if (value.contains(user)) {
               value.remove(user);
               int val = requiredRowers.get(key);
               requiredRowers.replace(key, val + 1);
               return true;
            }
        });

        return false;**/
    }

    /**
     * Checks whether the user is eligible to be a rower on the boat
     * @param type the user's certificate
     * @param position the user's position
     * @return true whether the user is eligile, false otherwise
     */
    public boolean canRowerBeAdded(Type type, Position position) {
        if(type.value < this.type.value) {
            return false;
        }
        return (requiredRowers.get(position) > 0);
    }

    /**
     * Compare this Boat object to another Object.
     *
     * @param o the other object
     * @return true/false based on whether the 2 objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Boat)) return false;
        Boat boat = (Boat) o;
        if(!(id == boat.id && type == boat.type))
            return false;
        return Objects.equals(rowers, boat.rowers) && Objects.equals(requiredRowers, boat.requiredRowers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, rowers, requiredRowers);
    }
}
