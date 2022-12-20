package nl.tudelft.sem.template.activity.models;

import lombok.Data;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Position;

@Data
public class InformJoinRequestModel {
    private long activityId;
    private NetId owner;
    private Position position;
}
