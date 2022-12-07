package nl.tudelft.sem.template.activity.domain;

import nl.tudelft.sem.template.activity.models.Position;

import java.util.List;
import java.util.Map;

public abstract class Activity {
    private NetId owner;
    private Map<Position, NetId> attendees;
    private long startTime;

    public Activity(NetId owner, Map<Position, NetId> attendees, long startTime) {
        this.owner = owner;
        this.attendees = attendees;
        this.startTime = startTime;
    }
}
