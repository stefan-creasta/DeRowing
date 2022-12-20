package nl.tudelft.sem.template.activity.domain.events;

import lombok.Data;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Position;

@Data
public class UserJoinEvent {
    private final NetId owner;
    private final Position position;
    private final long activityId;

    /**
     * Constructor for UserJoinEvent.
     *
     * @param owner the netId of the user
     * @param position the position of the user
     * @param activityId the id of the activity
     */
    public UserJoinEvent(NetId owner, Position position, long activityId) {
        this.owner = owner;
        this.position = position;
        this.activityId = activityId;
    }
}
