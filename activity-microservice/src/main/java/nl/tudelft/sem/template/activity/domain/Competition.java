package nl.tudelft.sem.template.activity.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Competition extends Activity {
    @Column
    private boolean allowAmateurs;
    @Column
    private GenderConstraint genderConstraint;
    @Column
    private boolean singleOrganization;

    public Competition(NetId owner, long boatId, long startTime, boolean allowAmateurs, GenderConstraint genderConstraint, boolean singleOrganization) {
        super(owner, boatId, startTime);
        this.allowAmateurs = allowAmateurs;
        this.genderConstraint = genderConstraint;
        this.singleOrganization = singleOrganization;
    }
}
