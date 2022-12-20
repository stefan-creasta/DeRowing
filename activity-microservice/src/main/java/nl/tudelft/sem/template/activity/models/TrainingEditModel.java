package nl.tudelft.sem.template.activity.models;

import lombok.Data;
import nl.tudelft.sem.template.activity.domain.Type;

@Data
public class TrainingEditModel {
    private String trainingName;
    private long startTime;
    private Type type;
    private int numPeople;
    private long id;
}
