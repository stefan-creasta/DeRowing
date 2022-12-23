package nl.tudelft.sem.template.user.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import nl.tudelft.sem.template.user.domain.entities.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NetIdTest {
    NetId netId;

    @BeforeEach
    public void setup() {
	netId = new NetId("vluong");
    }

    @Test
    public void testGetNetId() {
	assertEquals("vluong", netId.getNetId());
    }

    @Test
    public void testSetId() {
	netId.setNetId("hminh");
	assertEquals("hminh", netId.getNetId());
    }

    @Test
    public void testNotEqual() {
	NetId netId2 = new NetId("hminh");
	assertNotEquals(netId, netId2);
    }

    @Test
    public void testEqual() {
	NetId netId1 = new NetId("vluong");
	assertEquals(netId, netId1);
    }

    @Test
    public void testEqualItselft() {
	assertEquals(netId, netId);
    }
}
