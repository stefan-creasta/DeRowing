package nl.tudelft.sem.template.user.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import nl.tudelft.sem.template.user.domain.NetId;
import nl.tudelft.sem.template.user.domain.Position;

@Data
@AllArgsConstructor
public class UserJoinRequestModel {
    private NetId owner;
    private Position position;
    private long activityId;
}
