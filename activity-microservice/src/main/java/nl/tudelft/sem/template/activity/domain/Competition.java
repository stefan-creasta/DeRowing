package nl.tudelft.sem.template.activity.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Competition extends Activity {
    @Column
    private boolean allowAmateurs;
    @Column
    private GenderConstraint genderConstraint;
    @Column
    private boolean singleOrganization;


    /**
     * Constructor for Competition.
     *
     * @param netId the netId of the user
     * @param activityName the name of the activity
     * @param boatId the id of the boat
     * @param startTime the start time of the activity
     * @param allowAmateurs whether amateurs are allowed
     * @param genderConstraint what genders are allowed
     * @param singleOrganization whether only one organization is allowed
     */
    public Competition(NetId netId, String activityName, long boatId, long startTime,
                       boolean allowAmateurs, GenderConstraint genderConstraint,
                       boolean singleOrganization) {
        super(netId, activityName, boatId, startTime);
        this.allowAmateurs = allowAmateurs;
        this.genderConstraint = genderConstraint;
        this.singleOrganization = singleOrganization;
    }
}
