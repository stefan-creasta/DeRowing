package nl.tudelft.sem.template.boat.models;

import lombok.Data;
import nl.tudelft.sem.template.boat.domain.NetId;
import nl.tudelft.sem.template.boat.domain.Position;

@Data
public class BoatRowerEditModel {
    private long boatId;
    private Position position;
    private NetId netId;
}
