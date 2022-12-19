package nl.tudelft.sem.template.activity.domain.entities;

import nl.tudelft.sem.template.activity.domain.NetId;
import org.junit.jupiter.api.BeforeEach;

class TrainingTest {

    private Training test;

    @BeforeEach
    public void setup() {
        test = new Training(new NetId("123"), "TestActivity", 123L, 123L);
    }
}