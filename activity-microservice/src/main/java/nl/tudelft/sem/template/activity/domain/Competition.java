package nl.tudelft.sem.template.activity.domain;

import nl.tudelft.sem.template.activity.models.Position;

import java.util.Map;

public class Competition extends Activity {
    private long id;
    private boolean allowAmateurs;
    private GenderConstraint genderConstraint;
    private boolean singleOrganization;

    public Competition(NetId owner, Map<Position, NetId> attendees, long startTime) {
        super(owner, attendees, startTime);
    }
}
