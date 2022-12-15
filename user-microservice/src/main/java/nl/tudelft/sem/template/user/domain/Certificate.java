package nl.tudelft.sem.template.user.domain;

public enum Certificate {
    C4(1),
    PLUS4(2),
    PLUS8(3);

    public final int value;

    Certificate(int value) {
        this.value = value;
    }
}
