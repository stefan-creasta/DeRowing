package nl.tudelft.sem.template.activity.models;

import lombok.Data;

@Data
public class BoatDeleteModel {
    long boatId;

    public BoatDeleteModel(long boatId) {
        this.boatId = boatId;
    }
}
