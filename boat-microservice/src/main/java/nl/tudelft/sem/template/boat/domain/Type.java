package nl.tudelft.sem.template.boat.domain;

public enum Type {
    C4(1),
    PLUS4(2),
    PLUS8(3);
    public final int value;
    private Type(int value) {
        this.value = value;
    }
}
