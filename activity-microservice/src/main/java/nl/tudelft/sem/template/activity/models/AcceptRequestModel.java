package nl.tudelft.sem.template.activity.models;

import lombok.Data;
import nl.tudelft.sem.template.activity.domain.NetId;
import nl.tudelft.sem.template.activity.domain.Position;

@Data
public class AcceptRequestModel {
    boolean isAccepted;
    long activityId;
    NetId requestee;
    Position position;
}

