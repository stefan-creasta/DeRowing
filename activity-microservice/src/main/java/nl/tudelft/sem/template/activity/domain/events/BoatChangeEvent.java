package nl.tudelft.sem.template.activity.domain.events;

import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Position;

public class BoatChangeEvent {
    long boatId;
    Position position;
    NetId acceptee;

    /**
     * A constructor.
     *
     * @param boatId the id of the boat
     * @param position the position of the boat
     * @param acceptee the one getting accepted
     */
    public BoatChangeEvent(long boatId, Position position, NetId acceptee) {
        this.boatId = boatId;
        this.position = position;
        this.acceptee = acceptee;
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

    public NetId getAcceptee() {
        return acceptee;
    }

    public void setAcceptee(NetId acceptee) {
        this.acceptee = acceptee;
    }
}
