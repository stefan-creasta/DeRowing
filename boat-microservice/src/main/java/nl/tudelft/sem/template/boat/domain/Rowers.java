package nl.tudelft.sem.template.boat.domain;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Rowers {
    protected Map<Position, List<Rower>> currentRowers;
    public Rowers(Map<Position, List<Rower>> currentRowers) {
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
