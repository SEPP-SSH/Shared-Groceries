package ssh.unitTests.handlers;

import org.junit.jupiter.api.*;
import ssh.entities.House;
import ssh.handlers.HouseHandler;
import ssh.utilities.HibernateUtility;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HouseHandlerTests {

    private HouseHandler houseHandler;
    private House house;

    @BeforeAll
    void setUp() throws Exception {
        // Initialize handler with Hibernate session factory
        houseHandler = new HouseHandler(HibernateUtility.getSessionFactory());

        // Prepopulate the database with one house
        house = new House();
        house.setHouseAddress("10 Downing Street");
        houseHandler.create(house);
    }

    @BeforeEach
    void cleanDatabase() throws Exception {
        // Clean up houses before each test
        List<House> houses = houseHandler.getAll();
        for (House house : houses) {
            houseHandler.deleteById(house.getHouseId());
        }

        // Recreate the single house for prototype constraints
        houseHandler.create(house);
    }

    @Test
    void testCreateHouse() {
        // Arrange
        House newHouse = new House();
        newHouse.setHouseAddress("221B Baker Street");

        // Act
        houseHandler.create(newHouse);

        // Assert
        House fetchedHouse = houseHandler.getById(newHouse.getHouseId());
        assertNotNull(fetchedHouse);
        assertEquals("221B Baker Street", fetchedHouse.getHouseAddress());

        // Cleanup
        houseHandler.deleteById(newHouse.getHouseId());
    }

    @Test
    void testGetAllHouses() {
        // Act
        List<House> houses = houseHandler.getAll();

        // Assert
        assertEquals(1, houses.size());
        assertEquals(house.getHouseId(), houses.get(0).getHouseId());
        assertEquals("10 Downing Street", houses.get(0).getHouseAddress());
    }

    @Test
    void testGetHouseById() {
        // Act
        House fetchedHouse = houseHandler.getById(house.getHouseId());

        // Assert
        assertNotNull(fetchedHouse);
        assertEquals(house.getHouseId(), fetchedHouse.getHouseId());
        assertEquals("10 Downing Street", fetchedHouse.getHouseAddress());
    }

    @Test
    void testDeleteHouseById() {
        // Act
        houseHandler.deleteById(house.getHouseId());

        // Assert
        assertNull(houseHandler.getById(house.getHouseId()));

        // Recreate the house to maintain prototype constraints
        houseHandler.create(house);
    }
}
