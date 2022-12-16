package nl.tudelft.sem.template.boat.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RequiredRowers {
    protected HashMap<Position, Integer> amountOfPositions;
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
