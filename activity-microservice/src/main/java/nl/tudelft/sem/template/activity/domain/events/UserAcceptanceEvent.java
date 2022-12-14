package nl.tudelft.sem.template.activity.domain.events;

import lombok.Data;
import nl.tudelft.sem.template.activity.domain.NetId;

@Data
public class UserAcceptanceEvent {
    boolean isAccepted;
    NetId eventOwner;
    NetId eventRequester;

    /**
     * A constructor.
     *
     * @param isAccepted if user is accepted
     * @param eventOwner the owner of the activity
     * @param eventRequester the user requesting to join the activity
     */
    public UserAcceptanceEvent(boolean isAccepted, NetId eventOwner, NetId eventRequester) {
        this.isAccepted = isAccepted;
        this.eventOwner = eventOwner;
        this.eventRequester = eventRequester;
    }
}
