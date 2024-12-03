package ssh.unitTests.handlers;

import org.junit.jupiter.api.*;
import ssh.entities.*;
import ssh.handlers.*;
import ssh.utilities.HibernateUtility;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HousemateHandlerTests {

    private HousemateHandler housemateHandler;
    private HouseHandler houseHandler;

    private House house;
    private Housemate housemate;

    @BeforeAll
    void setUp() throws Exception {
        // Initialize handlers
        housemateHandler = new HousemateHandler(HibernateUtility.getSessionFactory());
        houseHandler = new HouseHandler(HibernateUtility.getSessionFactory());

        // Prepopulate the database with one house
        house = new House();
        house.setHouseAddress("10 Downing Street");
        houseHandler.create(house);

        // Prepopulate the database with one housemate
        housemate = new Housemate();
        housemate.setHousemateForename("Nathan");
        housemate.setHousemateSurname("Drake");
        housemate.setHousemateImg("nathan.png");
        housemate.setHouse(house);
        housemateHandler.create(housemate);
    }

    @BeforeEach
    void cleanDatabase() throws Exception {
        // Clean up housemates before each test
        List<Housemate> housemates = housemateHandler.getAll();
        for (Housemate housemate : housemates) {
            housemateHandler.deleteById(housemate.getHousemateId());
        }

        // Recreate the single housemate for prototype constraints
        housemateHandler.create(housemate);
    }

    @Test
    void testCreateHousemate() {
        // Arrange
        Housemate newHousemate = new Housemate();
        newHousemate.setHousemateForename("Elena");
        newHousemate.setHousemateSurname("Fisher");
        newHousemate.setHousemateImg("elena.png");
        newHousemate.setHouse(house);

        // Act
        housemateHandler.create(newHousemate);

        // Assert
        Housemate fetchedHousemate = housemateHandler.getById(newHousemate.getHousemateId());
        assertNotNull(fetchedHousemate);
        assertEquals("Elena", fetchedHousemate.getHousemateForename());
        assertEquals("Fisher", fetchedHousemate.getHousemateSurname());
        assertEquals(house.getHouseId(), fetchedHousemate.getHouse().getHouseId());

        // Cleanup
        housemateHandler.deleteById(newHousemate.getHousemateId());
    }

    @Test
    void testGetAllHousemates() {
        // Act
        List<Housemate> housemates = housemateHandler.getAll();

        // Assert
        assertEquals(1, housemates.size());
        assertEquals(housemate.getHousemateId(), housemates.get(0).getHousemateId());
        assertEquals("Nathan", housemates.get(0).getHousemateForename());
    }

    @Test
    void testGetHousemateById() {
        // Act
        Housemate fetchedHousemate = housemateHandler.getById(housemate.getHousemateId());

        // Assert
        assertNotNull(fetchedHousemate);
        assertEquals(housemate.getHousemateId(), fetchedHousemate.getHousemateId());
        assertEquals("Nathan", fetchedHousemate.getHousemateForename());
        assertEquals("Drake", fetchedHousemate.getHousemateSurname());
    }

    @Test
    void testGetHousematesByHouse() {
        // Act
        List<Housemate> housemates = housemateHandler.getByHouse(house.getHouseId());

        // Assert
        assertEquals(1, housemates.size());
        assertEquals(housemate.getHousemateId(), housemates.get(0).getHousemateId());
        assertEquals(house.getHouseId(), housemates.get(0).getHouse().getHouseId());
    }

    @Test
    void testDeleteHousemateById() {
        // Act
        housemateHandler.deleteById(housemate.getHousemateId());

        // Assert
        assertNull(housemateHandler.getById(housemate.getHousemateId()));

        // Recreate the housemate to maintain prototype constraints
        housemateHandler.create(housemate);
    }
}
