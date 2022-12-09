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
}
