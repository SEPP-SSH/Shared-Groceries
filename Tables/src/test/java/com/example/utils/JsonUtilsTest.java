package com.example.utils;

import com.example.entities.House;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonUtilsTest {

    @Test
    void testReadJsonFile() throws Exception {
        // Prepare a temporary JSON file for testing
        File tempJsonFile = new File("test-output/house.json");

        // Ensure output directory exists
        if (!tempJsonFile.getParentFile().exists()) {
            tempJsonFile.getParentFile().mkdirs();
        }

        // Write sample JSON content to the file
        ObjectMapper mapper = new ObjectMapper();
        List<House> houses = List.of(
                new House() {{ setHouseId(1); setHouseAddress("123 Main Street"); }},
                new House() {{ setHouseId(2); setHouseAddress("456 Elm Street"); }}
        );
        mapper.writeValue(tempJsonFile, houses);

        // Read JSON file using JsonUtils
        List<House> readHouses = JsonUtils.readJsonFile(tempJsonFile.getAbsolutePath(), House[].class);

        // Assertions to verify correctness
        assertNotNull(readHouses);
        assertEquals(2, readHouses.size());
        assertEquals("123 Main Street", readHouses.get(0).getHouseAddress());
        assertEquals("456 Elm Street", readHouses.get(1).getHouseAddress());

        System.out.println("Read JSON file successfully: " + tempJsonFile.getAbsolutePath());
    }

    @Test
    void testJsonFileNotFound() {
        // Test behavior when the JSON file does not exist
        Exception exception = assertThrows(Exception.class, () -> {
            JsonUtils.readJsonFile("nonexistent-file.json", House[].class);
        });

        String expectedMessage = "nonexistent-file.json";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        System.out.println("Handled non-existent JSON file correctly.");
    }
}
