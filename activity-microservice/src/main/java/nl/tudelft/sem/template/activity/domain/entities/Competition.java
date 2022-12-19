package nl.tudelft.sem.template.activity.domain.entities;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.activity.domain.GenderConstraint;
import nl.tudelft.sem.template.activity.domain.NetId;

@Entity(name = "Activity")
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

    /**
     * A method providing string format information.
     *
     * @return a string which contains all information about the competition
     */
    public String toString() {
        return super.toString() + "\n Allow Amateurs: " + allowAmateurs
                + "\n Gender Constraint: " + genderConstraint
                + "\n singleOrganization: " + singleOrganization;
    }

    public boolean isAllowAmateurs() {
        return allowAmateurs;
    }

    public void setAllowAmateurs(boolean allowAmateurs) {
        this.allowAmateurs = allowAmateurs;
    }

    public GenderConstraint getGenderConstraint() {
        return genderConstraint;
    }

    public void setGenderConstraint(GenderConstraint genderConstraint) {
        this.genderConstraint = genderConstraint;
    }

    public boolean isSingleOrganization() {
        return singleOrganization;
    }

    public void setSingleOrganization(boolean singleOrganization) {
        this.singleOrganization = singleOrganization;
    }

    /**
     * Method to judge whether two competitions are equal.
     *
     * @param o another competition to be compared with
     * @return a boolean value which shows the result
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Competition that = (Competition) o;
        return allowAmateurs == that.allowAmateurs && singleOrganization == that.singleOrganization
                && genderConstraint == that.genderConstraint;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), allowAmateurs, genderConstraint, singleOrganization);
    }
}
