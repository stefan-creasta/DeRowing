package nl.tudelft.sem.template.activity.domain;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
public class Training extends Activity{

    public Training(NetId netId, String activityName, long boatId, long startTime) {
        super(netId, activityName, boatId, startTime);
    }
}
