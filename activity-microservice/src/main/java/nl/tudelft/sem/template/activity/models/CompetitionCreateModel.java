package nl.tudelft.sem.template.activity.models;

import lombok.Data;
import nl.tudelft.sem.template.activity.domain.GenderConstraint;

@Data
public class CompetitionCreateModel {
    private String competitionName;
    private GenderConstraint genderConstraint;
    private boolean allowAmateurs;
    private boolean singleOrganization;
    private long startTime;
}
