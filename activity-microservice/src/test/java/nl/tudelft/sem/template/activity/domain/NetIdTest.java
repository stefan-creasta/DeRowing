package nl.tudelft.sem.template.activity.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NetIdTest {

    private NetId id;

    @BeforeEach
    public void setup() {
        id = new NetId("123");
    }

    @Test
    void getNetId() {
        Assertions.assertEquals("123", id.getNetId());
    }

    @Test
    void setNetId() {
        id.setNetId("333");
        Assertions.assertEquals("333", id.getNetId());
    }

    @Test
    void testEquals() {
        NetId n = new NetId("123");
        Assertions.assertEquals(n, id);
    }

    @Test
    void testHashCode() {
        NetId n = new NetId("123");
        Assertions.assertEquals(n.hashCode(), id.hashCode());
    }
}