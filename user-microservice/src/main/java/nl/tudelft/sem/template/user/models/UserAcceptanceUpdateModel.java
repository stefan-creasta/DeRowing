package nl.tudelft.sem.template.user.models;

import lombok.Data;
import nl.tudelft.sem.template.user.domain.NetId;
import nl.tudelft.sem.template.user.domain.Position;

@Data
public class UserAcceptanceUpdateModel {
    boolean isAccepted;
    Position position;
    NetId eventRequester;

    /**
     * A constructor.
     *
     * @param isAccepted if user is accepted
     * @param position the owner of the activity
     * @param eventRequester the user requesting to join the activity
     */
    public UserAcceptanceUpdateModel(boolean isAccepted, Position position, NetId eventRequester) {
        this.isAccepted = isAccepted;
        this.eventRequester = eventRequester;
		this.position = position;
    }
}
