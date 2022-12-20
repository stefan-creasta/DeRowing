package nl.tudelft.sem.template.activity.models;

import lombok.Data;

@Data
public class BoatDeleteModel {
    long id;

    public BoatDeleteModel(long id) {
        this.id = id;
    }
}
