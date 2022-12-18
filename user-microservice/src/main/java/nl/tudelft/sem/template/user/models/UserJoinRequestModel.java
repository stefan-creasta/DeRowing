package nl.tudelft.sem.template.user.models;

import lombok.Data;
import nl.tudelft.sem.template.user.domain.NetId;
import nl.tudelft.sem.template.user.domain.Position;

@Data
public class UserJoinRequestModel {
    private NetId owner;
    private Position position;
    private long activityId;
}
