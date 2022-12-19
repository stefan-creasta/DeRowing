package nl.tudelft.sem.template.activity.models;

import lombok.Data;
import nl.tudelft.sem.template.activity.domain.GenderConstraint;
import nl.tudelft.sem.template.activity.domain.Type;

@Data
public class CompetitionEditModel {
    private String competitionName;
    private GenderConstraint genderConstraint;
    private boolean allowAmateurs;
    private boolean singleOrganization;
    private String organization;
    private long startTime;
    private Type type;
    private int numPeople;
    private long id;
}
