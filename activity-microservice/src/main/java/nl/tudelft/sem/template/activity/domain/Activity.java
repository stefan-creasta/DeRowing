package nl.tudelft.sem.template.activity.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.NoArgsConstructor;

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


    /**
     * Constructor for Activity.
     *
     * @param netId      the netId of the user
     * @param activityName the name of the activity
     * @param boatId    the id of the boat
     * @param startTime the start time of the activity
     */
    public Activity(NetId netId, String activityName, long boatId, long startTime) {
        this.netId = netId;
        this.activityName = activityName;
        this.boatId = boatId;
        this.startTime = startTime;
    }
}
