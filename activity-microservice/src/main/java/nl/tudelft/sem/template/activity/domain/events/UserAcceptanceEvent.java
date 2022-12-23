package nl.tudelft.sem.template.activity.domain.events;

import lombok.Data;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Position;

@Data
public class UserAcceptanceEvent {
    boolean isAccepted;
    Position position;
    NetId eventRequester;
    long activityId;

    /**
     * A constructor.
     *  @param isAccepted if user is accepted
     * @param position the owner of the activity
     * @param eventRequester the user requesting to join the activity
     * @param activityId the activity id
     */
    public UserAcceptanceEvent(boolean isAccepted, Position position, NetId eventRequester, long activityId) {
        this.isAccepted = isAccepted;
        this.position = position;
        this.eventRequester = eventRequester;
        this.activityId = activityId;
    }
}
