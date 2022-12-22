package nl.tudelft.sem.template.activity.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ActivityCancelModel {
    long id;

    public ActivityCancelModel(long id) {
        this.id = id;
    }
}
