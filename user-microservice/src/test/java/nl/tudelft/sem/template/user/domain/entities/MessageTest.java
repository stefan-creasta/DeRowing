package nl.tudelft.sem.template.user.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import nl.tudelft.sem.template.user.domain.Position;
import nl.tudelft.sem.template.user.domain.entities.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MessageTest {
    Message message;

    @BeforeEach
    public void setup() {
	message = new Message("rithik", "vluong", 2L, "qwerqwer", Position.COACH);
    }

    @Test
    public void testGetReceiver() {
	assertEquals("rithik", message.getReceiver());
    }

    @Test
    public void testSetReceiver() {
	message.setReceiver("hminh");
	assertEquals("hminh", message.getReceiver());
    }

    @Test
    public void testGetActivityId() {
	assertEquals(2L, message.getActivityId());
    }

    @Test
    public void testSetActivityId() {
	message.setActivityId(3L);
	assertEquals(3L, message.getActivityId());
    }

    @Test
    public void getContent() {
	assertEquals("qwerqwer", message.getContent());
    }

    @Test
    public void setContent() {
	message.setContent("I want to join this competition");
	assertEquals("I want to join this competition", message.getContent());
    }
}
