package nl.tudelft.sem.template.boat.domain;

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
    private Map<Position, User> rowers;

    /**
     * Create new boat.
     *
     * @param type the type of the boat
     */
    public Boat(Type type) {
        this.type = type;
    }

    /**
     * Add rower to boat.
     *
     * @param user the user to be added
     */
    public void addRowerToPosition(Position position, User user) {
        rowers.put(position, user);
    }

    /**
     * Removes rower from a certain position of the boat.
     *
     * @param position the position to be removed
     * @return the position that was removed
     */
    public Position removePosition(Position position) { return rowers.remove(position); }

    /**
     * Removes rower from the boat.
     *
     * @param user the user to be removed
     * @return the user that was removed
     */
    public User removeRower(User user) {
        // find the key that the User is mapped to
        int pos = -1;
        rowers.forEach((key, value) -> {
            if (value.equals(user)) {
               pos = key;
            }
        });

        return rowers.remove(pos); // remove the User mapped to the found key
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
        return id == boat.id && type == boat.type && Objects.equals(rowers, boat.rowers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, rowers);
    }
}
