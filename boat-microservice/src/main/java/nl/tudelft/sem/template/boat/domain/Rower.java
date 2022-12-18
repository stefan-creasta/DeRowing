package nl.tudelft.sem.template.boat.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Id;

@Embeddable
public class Rower {
    @Id
    @EmbeddedId
    private NetId netId;
}
