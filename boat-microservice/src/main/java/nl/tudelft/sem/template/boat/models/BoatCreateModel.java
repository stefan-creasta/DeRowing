package nl.tudelft.sem.template.boat.models;

import lombok.Data;
import nl.tudelft.sem.template.boat.domain.Type;

@Data
public class BoatCreateModel {
    private Type type;
}
