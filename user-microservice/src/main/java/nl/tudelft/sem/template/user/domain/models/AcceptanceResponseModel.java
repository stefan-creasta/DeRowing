package nl.tudelft.sem.template.user.domain.models;

import nl.tudelft.sem.template.user.domain.NetId;
import nl.tudelft.sem.template.user.domain.Position;

public class AcceptanceResponseModel {
    boolean isAccepted;
    long activityId;
    NetId requestee;
    Position position;
}
