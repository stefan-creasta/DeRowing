package nl.tudelft.sem.template.activity.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import lombok.NoArgsConstructor;

@MappedSuperclass
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
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

    public NetId getNetId() {
        return netId;
    }

    public void setNetId(NetId netId) {
        this.netId = netId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public long getBoatId() {
        return boatId;
    }

    public void setBoatId(long boatId) {
        this.boatId = boatId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * A method provide string format information.
     *
     * @return a string contains information about the activity.
     */
    public String toString() {
        return "The competition is created by: " + netId.getNetId() + "\n The name is: "
                + activityName  + "\n The boatId is: "
                + boatId + "\n The start time is: " + startTime;
    }
}
