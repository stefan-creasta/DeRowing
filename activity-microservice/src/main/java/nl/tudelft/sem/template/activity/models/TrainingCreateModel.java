package nl.tudelft.sem.template.activity.models;

import lombok.Data;

@Data
public class TrainingCreateModel {
    private String trainingName;
    private long startTime;
    private long boatId;

    /**
     * The constructor for trainingCreateModel.
     *
     * @param trainingName The name of the training
     * @param startTime The startTime of the training
     * @param boatId The id of the boat
     */
    public TrainingCreateModel(String trainingName, long startTime, long boatId) {
        this.trainingName = trainingName;
        this.startTime = startTime;
        this.boatId = boatId;
    }
}
