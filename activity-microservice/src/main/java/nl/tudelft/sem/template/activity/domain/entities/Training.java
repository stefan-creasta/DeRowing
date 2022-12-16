package nl.tudelft.sem.template.activity.domain.entities;

import javax.persistence.Entity;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.activity.domain.NetId;

@Entity(name = "Training")
@NoArgsConstructor
public class Training extends Activity {

    public Training(NetId netId, String activityName, long boatId, long startTime, int numPeople) {
        super(netId, activityName, boatId, startTime, numPeople);
    }
}
