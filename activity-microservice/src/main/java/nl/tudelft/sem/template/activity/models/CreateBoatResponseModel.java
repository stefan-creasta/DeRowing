package nl.tudelft.sem.template.activity.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateBoatResponseModel {
    private long boatId;

    public CreateBoatResponseModel(long boatId) {
        this.boatId = boatId;
    }
}
