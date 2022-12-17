package nl.tudelft.sem.template.boat.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Rower {
    private NetId netId;
    private Certificate certificate;
}
