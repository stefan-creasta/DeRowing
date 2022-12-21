package nl.tudelft.sem.template.boat.builders;

import java.util.HashMap;
import java.util.List;
import nl.tudelft.sem.template.boat.domain.Boat;
import nl.tudelft.sem.template.boat.domain.NetId;
import nl.tudelft.sem.template.boat.domain.Position;
import nl.tudelft.sem.template.boat.domain.RequiredRowers;
import nl.tudelft.sem.template.boat.domain.Rowers;
import nl.tudelft.sem.template.boat.domain.Type;

public abstract class BoatBuilder {
    private transient String name;
    private transient Type type;
    private transient Boat boat;
    private transient Rowers rowers;
    private transient RequiredRowers requiredRowers;

    /**
     * Basic constructor for the BoatBuilder class.
     *
     * @param name the name for the Boat to be constructed
     */
    public BoatBuilder(String name) {
        this.name = name;
        this.type = Type.C4;
        this.boat = new Boat();
    }

    abstract void buildCox(int value);

    abstract void buildPort(int value);

    abstract void buildStarboard(int value);

    abstract void buildSculling(int value);

    public abstract Boat getBoat();

    public HashMap<Position, List<NetId>> getRowers() {
        return this.rowers.getCurrentRowers();
    }

    public HashMap<Position, Integer> getRequiredRowers() {
        return this.requiredRowers.getAmountOfPositions();
    }

    public void setRowers(HashMap<Position, List<NetId>> value) {
        this.rowers.setCurrentRowers(value);
    }

    public void setRequiredRowers(HashMap<Position, Integer> value) {
        this.requiredRowers.setAmountOfPositions(value);
    }

    public Rowers getRowersClass() {
        return this.rowers;
    }

    public RequiredRowers getRequiredRowersClass() {
        return this.requiredRowers;
    }
}
