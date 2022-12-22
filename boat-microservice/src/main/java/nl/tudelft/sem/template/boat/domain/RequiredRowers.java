package nl.tudelft.sem.template.boat.domain;

import java.util.HashMap;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RequiredRowers {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
	long id;

    protected HashMap<Position, Integer> amountOfPositions;

    /**
     * Empty constructor for RequiredRowers class.
     */
    public RequiredRowers() {
        this.amountOfPositions = new HashMap<>();
        for (Position position : Position.values()) {
            this.amountOfPositions.put(position, Integer.MIN_VALUE);
        }
    }

    /**
     * Basic constructor for RequiredRowers class.
     */
    public RequiredRowers(HashMap<Position, Integer> currentRowers) {
        this.amountOfPositions = currentRowers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequiredRowers)) {
            return false;
        }
        RequiredRowers rr = (RequiredRowers) o;
        return Objects.equals(amountOfPositions, rr.amountOfPositions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.amountOfPositions);
    }
}
