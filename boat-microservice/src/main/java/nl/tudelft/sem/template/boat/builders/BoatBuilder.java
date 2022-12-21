package nl.tudelft.sem.template.boat.builders;

import nl.tudelft.sem.template.boat.domain.Boat;
import nl.tudelft.sem.template.boat.domain.Type;

public abstract class BoatBuilder {
    private transient Boat boat;

    /**
     * Basic constructor for the BoatBuilder class.
     */
    public BoatBuilder() {
    }

    /**
     * Reset method which instantiates a new boat for this constructor.
     */
    abstract void reset();

    /**
     * Basic setter for the cox field.
     *
     * @param value the number of required people who must be a cox
     */
    abstract void setCox(int value);

    /**
     * Basic setter for the coach field.
     *
     * @param value the number of required people who must be a coach
     */
    abstract void setCoach(int value);

    /**
     * Basic setter for the port field.
     *
     * @param value the number of required people who must be a port rower
     */
    abstract void setPort(int value);

    /**
     * Basic setter for the starboard field.
     *
     * @param value the number of required people who must be a starboard rower
     */
    abstract void setStarboard(int value);

    /**
     * Basic setter for the sculling field.
     *
     * @param value the number of required people who must be a sculling rower
     */
    abstract void setSculling(int value);

    /**
     * The method which returns the boat which was built.
     *
     * @return the actual boat
     */
    public abstract Boat getBoat();
}
