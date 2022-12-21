package nl.tudelft.sem.template.boat.models;

import java.util.List;
import lombok.Data;
import nl.tudelft.sem.template.boat.domain.Position;

@Data
public class BoatEmptyPositionsModel {
    List<Position> positionList;
}
