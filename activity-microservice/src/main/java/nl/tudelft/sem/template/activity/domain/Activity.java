package nl.tudelft.sem.template.activity.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@MappedSuperclass
@NoArgsConstructor
public abstract class Activity {
    @Id
    @EmbeddedId
    private NetId netId;
    @Column
    private String activityName;
    @Column
    private long boatId;
    @Column
    private long startTime;

    public Activity(NetId netId, String activityName, long boatId, long startTime) {
        this.netId = netId;
        this.activityName = activityName;
        this.boatId = boatId;
        this.startTime = startTime;
    }
}
