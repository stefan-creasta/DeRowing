package nl.tudelft.sem.template.activity.models;

import lombok.Data;
import nl.tudelft.sem.template.activity.domain.Position;

@Data
public class JoinRequestModel {
    private long activityId;
    private Position position;
}
