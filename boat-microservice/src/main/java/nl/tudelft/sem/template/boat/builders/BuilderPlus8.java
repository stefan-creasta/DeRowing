package nl.tudelft.sem.template.boat.builders;

import java.util.HashMap;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import nl.tudelft.sem.template.boat.domain.Boat;
import nl.tudelft.sem.template.boat.domain.NetId;
import nl.tudelft.sem.template.boat.domain.Position;
import nl.tudelft.sem.template.boat.domain.RequiredRowers;
import nl.tudelft.sem.template.boat.domain.Rowers;
import nl.tudelft.sem.template.boat.domain.Type;

@Getter
@Setter
public class BuilderPlus8 extends BoatBuilder {
    private String name;
    private Type type;
    private Boat boat;
    private Rowers rowers;
    private RequiredRowers requiredRowers;

    public BuilderPlus8(String name) {
        super(name);

        rowers = new Rowers();
        requiredRowers = new RequiredRowers();
        requiredRowers.getAmountOfPositions().put(Position.COACH, 1);
        buildCox(1);
        buildPort(4);
        buildStarboard(4);
        buildSculling(0);

        boat.setRowers(rowers);
        boat.setRequiredRowers(requiredRowers);
    }

    public HashMap<Position, List<NetId>> getRowers() {
        return this.rowers.getCurrentRowers();
    }

    public HashMap<Position, Integer> getRequiredRowers() {
        return this.requiredRowers.getAmountOfPositions();
    }

    public void buildCox(int value) {
        requiredRowers.getAmountOfPositions().put(Position.COX, value);
    }

    public void buildPort(int value) {
        requiredRowers.getAmountOfPositions().put(Position.PORT, value);
    }

    public void buildStarboard(int value) {
        requiredRowers.getAmountOfPositions().put(Position.STARBOARD, value);
    }

    public void buildSculling(int value) {
        requiredRowers.getAmountOfPositions().put(Position.SCULLING, value);
    }
}
