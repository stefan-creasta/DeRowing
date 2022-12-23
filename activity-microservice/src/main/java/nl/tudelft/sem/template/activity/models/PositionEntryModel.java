package nl.tudelft.sem.template.activity.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.activity.domain.Position;

@Data
@NoArgsConstructor
public class PositionEntryModel {
    private Position position;

    /**
     * Constructor for PositionEntryModel.
     *
     * @param position position of the user
     */
    public PositionEntryModel(Position position) {
        this.position = position;
    }
}
