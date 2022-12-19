package nl.tudelft.sem.template.boat.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Rowers {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    long id;

    protected HashMap<Position, List<NetId>> currentRowers;

    public Rowers(HashMap<Position, List<NetId>> currentRowers) {
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
