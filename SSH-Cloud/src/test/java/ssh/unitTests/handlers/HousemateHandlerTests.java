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
    private Housemate housemate;
    private House house;

    @BeforeAll
    void setUp() throws Exception {
        housemateHandler = new HousemateHandler(HibernateUtility.getSessionFactory());
        houseHandler = new HouseHandler(HibernateUtility.getSessionFactory());

        house = new House();
        house.setHouseAddress("22 Downing Street");
        houseHandler.create(house);

        housemate = new Housemate();
        housemate.setHousemateForename("Nathan");
        housemate.setHousemateSurname("Drake");
        housemate.setHousemateImg("nathan.png");
        housemate.setHouse(house);
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
    }

    @Test
    void testGetAllHousemates() {
        List<Housemate> housemates = housemateHandler.getAll();
        boolean flag = false;
        for (Housemate returnedHousemate : housemates){
            if (housemate.getHousemateId() == returnedHousemate.getHousemateId()){
                flag = true;
                break;
            }
        }
        assertTrue(flag);
    }

    @Test
    void testGetHousemateById() {
        Housemate fetchedHousemate = housemateHandler.getById(housemate.getHousemateId());
        assertNotNull(fetchedHousemate);
        assertEquals(housemate.getHousemateId(), fetchedHousemate.getHousemateId());
    }

    @Test
    void testGetHousematesByHouse() {
        List<Housemate> housemates = housemateHandler.getByHouse(house.getHouseId());
        boolean flag = false;
        for (Housemate returnedHousemate : housemates){
            if (housemate.getHousemateId() == returnedHousemate.getHousemateId()){
                if (house.getHouseId() == returnedHousemate.getHouse().getHouseId()){
                    flag = true;
                    break;
                }
            }
        }
        assertTrue(flag);
    }

    @Test
    void testDeleteHousemateById() {
        assertNotNull(housemateHandler.getById(housemate.getHousemateId()));
        housemateHandler.deleteById(housemate.getHousemateId());
        assertNull(housemateHandler.getById(housemate.getHousemateId()));
        housemateHandler.create(housemate);
    }
}