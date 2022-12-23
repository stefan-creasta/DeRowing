package nl.tudelft.sem.template.activity.models;

import lombok.Data;
import nl.tudelft.sem.template.activity.domain.Position;

@Data
public class TrainingFindModel {
    private String trainingName;
    private Position preferredPosition;

    public TrainingFindModel(String trainingName, Position preferredPosition) {
        this.preferredPosition = preferredPosition;
        this.trainingName = trainingName;
    }
}
