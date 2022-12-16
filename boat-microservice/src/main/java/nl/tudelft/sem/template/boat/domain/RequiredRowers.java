package nl.tudelft.sem.template.boat.domain;

import javax.persistence.Entity;import javax.persistence.GeneratedValue;import javax.persistence.GenerationType;import javax.persistence.Id;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
public class RequiredRowers {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	long id;

    protected HashMap<Position, Integer> amountOfPositions;

	public RequiredRowers() {}

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

	public HashMap<Position, Integer> getAmountOfPositions() {
		return amountOfPositions;
	}

	public void setAmountOfPositions(HashMap<Position, Integer> amountOfPositions) {
		this.amountOfPositions = amountOfPositions;
	}
}
