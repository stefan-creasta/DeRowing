package nl.tudelft.sem.template.boat.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Rowers {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    long id;

    public Rowers() {}

    protected HashMap<Position, List<NetId>> currentRowers;

    public Rowers(HashMap<Position, List<NetId>> currentRowers) {
        this.currentRowers = currentRowers;
    }

    public HashMap<Position, List<NetId>> getCurrentRowers() {
        return currentRowers;
    }

    public void setCurrentRowers(HashMap<Position, List<NetId>> currentRowers) {
        this.currentRowers = currentRowers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rowers)) {
            return false;
        }
        Rowers rr = (Rowers) o;
        return Objects.equals(currentRowers, rr.currentRowers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.currentRowers);
    }
}
