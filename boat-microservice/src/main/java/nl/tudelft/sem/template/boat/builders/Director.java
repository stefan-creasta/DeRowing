package nl.tudelft.sem.template.boat.builders;

import nl.tudelft.sem.template.boat.domain.Boat;
import nl.tudelft.sem.template.boat.domain.Type;

public class Director {
    private BoatBuilder builderC4;
    private BoatBuilder builderPlus4;
    private BoatBuilder builderPlus8;

    /**
     * Basic constructor for the Director, the class which can construct a boat using builders,
     * depending on the type of boat.
     */
    public Director() {
        builderC4 = new BuilderC4();
        builderPlus4 = new BuilderPlus4();
        builderPlus8 = new BuilderPlus8();
    }

    /**
     * Method which constructs a boat with pre-defined available positions for each task
     * (cox, coach etc.) depending on the boat's desired type.
     *
     * @param type the boat's type
     * @return the actual boat
     */
    public Boat constructBoat(Type type) {
        switch (type) {
            case C4:
                builderC4.reset();
                builderC4.setCoach(1);
                builderC4.setPort(2);
                builderC4.setStarboard(2);
                builderC4.setSculling(0);
                return builderC4.getBoat();
            case PLUS4:
                builderPlus4.reset();
                builderPlus4.setCox(1);
                builderPlus4.setCoach(1);
                builderPlus4.setPort(2);
                builderPlus4.setStarboard(2);
                builderPlus4.setSculling(0);
                return builderPlus4.getBoat();
            default:
                builderPlus8.reset();
                builderPlus8.setCox(1);
                builderPlus8.setCoach(1);
                builderPlus8.setPort(4);
                builderPlus8.setStarboard(4);
                builderPlus8.setSculling(0);
                return builderPlus8.getBoat();
        }
    }
}
