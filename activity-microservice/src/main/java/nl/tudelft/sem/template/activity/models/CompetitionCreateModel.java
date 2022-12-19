package nl.tudelft.sem.template.activity.models;

import lombok.Data;
import nl.tudelft.sem.template.activity.domain.GenderConstraint;
import nl.tudelft.sem.template.activity.domain.NetId;

@Data
public class CompetitionCreateModel {
    //private NetId netId;
    private String competitionName;
    private GenderConstraint genderConstraint;
    private long boatId;
    private boolean allowAmateurs;
    private boolean singleOrganization;
    private long startTime;

    /**
     * The constructor of the competitionCreateModel.
     *
     * @param competitionName the name of the competition
     * @param genderConstraint the genderConstraint of the competition
     * @param boatId the id of the boat
     * @param allowAmateurs whether this competition allow amateurs
     * @param singleOrganization whether this competition only support one organization
     * @param startTime the startTime
     */
    public CompetitionCreateModel(String competitionName, GenderConstraint genderConstraint, long boatId, boolean allowAmateurs, boolean singleOrganization, long startTime) {
        this.competitionName = competitionName;
        this.genderConstraint = genderConstraint;
        this.boatId = boatId;
        this.allowAmateurs = allowAmateurs;
        this.singleOrganization = singleOrganization;
        this.startTime = startTime;
    }
}
