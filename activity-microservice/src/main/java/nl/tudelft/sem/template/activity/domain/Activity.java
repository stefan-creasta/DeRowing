package nl.tudelft.sem.template.activity.domain;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public abstract class Activity {
    @Id
    @Convert(converter = NetIdConverter.class)
    private NetId owner;
    @Column
    private String activityName;
    @Column
    private long boatId;
    @Column
    private long startTime;

    public Activity(NetId owner, long boatId, long startTime) {
        this.owner = owner;
        this.boatId = boatId;
        this.startTime = startTime;
    }
}
