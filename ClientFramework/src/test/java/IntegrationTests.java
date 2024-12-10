import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import ssh.entities.Basket;
import ssh.entities.House;
import ssh.entities.Store;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntegrationTests {
    @BeforeAll
    static void setUp() throws Exception {
        for (int counter = 0; counter < 90; counter++){
            System.out.println("Waiting for SSH Cloud server to spin up. " + (90 - counter) + " seconds remaining.");
            Thread.sleep(1000);
        }
    }

    @Test
    void placeholderTest() {
        assertTrue(true);
    }
}
