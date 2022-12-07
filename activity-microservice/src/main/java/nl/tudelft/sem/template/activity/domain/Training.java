package nl.tudelft.sem.template.activity.domain;

import nl.tudelft.sem.template.activity.models.Position;

import java.util.Map;

public class Training extends Activity{
    private long id;

    public Training(NetId owner, Map<Position, NetId> attendees, long startTime) {
        super(owner, attendees, startTime);
    }
}
