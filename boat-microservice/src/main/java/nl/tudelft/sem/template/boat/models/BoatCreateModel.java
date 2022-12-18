package nl.tudelft.sem.template.boat.models;

import lombok.Data;
import nl.tudelft.sem.template.boat.domain.Type;

@Data
public class BoatCreateModel {
    private String name;
    private Type type;
    private int cox;
    private int coach;
    private int port;
    private int starboard;
    private int sculling;
}
