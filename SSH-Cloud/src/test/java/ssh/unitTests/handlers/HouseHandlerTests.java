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
        houseHandler = new HouseHandler(HibernateUtility.getSessionFactory());

        house = new House();
        house.setHouseAddress("10 Downing Street");
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
    }

    @Test
    void testGetAllHouses() {
        List<House> houses = houseHandler.getAll();
        boolean flag = false;
        for (House returnedHouse : houses){
            if (house.getHouseId() == returnedHouse.getHouseId()){
                flag = true;
                break;
            }
        }
        assertTrue(flag);
    }

    @Test
    void testGetHouseById() {
        House fetchedHouse = houseHandler.getById(house.getHouseId());
        assertNotNull(fetchedHouse);
        assertEquals(house.getHouseId(), fetchedHouse.getHouseId());
    }

    @Test
    void testDeleteHouseById() {
        assertNotNull(houseHandler.getById(house.getHouseId()));
        houseHandler.deleteById(house.getHouseId());
        assertNull(houseHandler.getById(house.getHouseId()));
        houseHandler.create(house);
    }
}
