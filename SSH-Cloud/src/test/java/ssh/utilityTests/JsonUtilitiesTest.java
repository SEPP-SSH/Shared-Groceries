package ssh.utilityTests;

import ssh.entities.House;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ssh.utilities.JsonUtilities;

import java.io.File;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class JsonUtilitiesTest {

    @Test
    void testReadJsonFile() throws Exception {
        // make a temporary JSON for testing
        File temp = new File("test-output/house.json");

        // make the output directory if it doesn't already exist
        if (!temp.getParentFile().exists()) {
            temp.getParentFile().mkdirs();
        }

        // write sample objects to JSON
        ObjectMapper mapper = new ObjectMapper();
        List<House> houses = List.of(
                new House() {{ setHouseId(1); setHouseAddress("10 Downing Street"); }},
                new House() {{ setHouseId(2); setHouseAddress("Thames House"); }}
        );
        mapper.writeValue(temp, houses);

        // read in the JSON file we've just wrote out
        List<House> returnedHouses = JsonUtilities.readJsonFile(temp.getAbsolutePath(), House[].class);

        // check result
        assertNotNull(returnedHouses);
        assertEquals(2, returnedHouses.size());
        assertEquals("10 Downing Street", returnedHouses.get(0).getHouseAddress());
        assertEquals("Thames House", returnedHouses.get(1).getHouseAddress());

        System.out.println("Success: successfully read in: " + temp.getAbsolutePath());
    }

    @Test
    void testJsonFileNotFound() {
        // test what happens when reading in a JSON that doesn't exist
        Exception exception = assertThrows(Exception.class, () -> {
            JsonUtilities.readJsonFile("nonexistent.json", House[].class);
        });

        String expectedMessage = "nonexistent.json";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        System.out.println("Success: handled non-existent file correctly");
    }
}