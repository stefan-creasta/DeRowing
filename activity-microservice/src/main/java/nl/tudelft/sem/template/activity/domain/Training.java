package nl.tudelft.sem.template.activity.domain;

import javax.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Training extends Activity {

    public Training(NetId netId, String activityName, long boatId, long startTime) {
        super(netId, activityName, boatId, startTime);
    }
}
