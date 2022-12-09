package nl.tudelft.sem.template.activity.domain;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
public class Competition extends Activity {
    @Column
    private boolean allowAmateurs;
    @Column
    private GenderConstraint genderConstraint;
    @Column
    private boolean singleOrganization;

    public Competition(NetId netId, String activityName, long boatId, long startTime, boolean allowAmateurs, GenderConstraint genderConstraint, boolean singleOrganization) {
        super(netId, activityName, boatId, startTime);
        this.allowAmateurs = allowAmateurs;
        this.genderConstraint = genderConstraint;
        this.singleOrganization = singleOrganization;
    }
}
