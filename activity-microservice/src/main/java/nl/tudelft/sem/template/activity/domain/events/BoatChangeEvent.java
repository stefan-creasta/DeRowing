package nl.tudelft.sem.template.activity.domain.events;

import nl.tudelft.sem.template.activity.domain.Position;

public class BoatChangeEvent {
    long boatId;
    Position position;

    /**
     * A constructor.
     *
     * @param boatId the id of the boat
     * @param position the position of the boat
     */
    public BoatChangeEvent(long boatId, Position position) {
        this.boatId = boatId;
        this.position = position;
    }

    public long getBoatId() {
        return boatId;
    }

    public void setBoatId(long boatId) {
        this.boatId = boatId;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
