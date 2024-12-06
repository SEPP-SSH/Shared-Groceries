package ssh.utilityTests;

import ssh.entities.House;
import ssh.entities.Store;
import ssh.handlers.HouseHandler;
import ssh.handlers.StoreHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.File;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DatabaseExporterTest {

    @Test
    void testGenerateJsonForHouses() throws Exception {
        // mock the handler
        HouseHandler houseHandler = Mockito.mock(HouseHandler.class);

        // mock data for the houses
        List<House> mockHouses = List.of(
                new House() {{setHouseId(1); setHouseAddress("10 Downing Street"); }}
        );

        // mock the handler behaviour
        when(houseHandler.getAll()).thenReturn(mockHouses);

        // serialise the objects to the JSON file
        ObjectMapper mapper = new ObjectMapper();
        File outputFile = new File("test-output/house.json");

        // make the output directory if it doesn't already exist
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs();
        }

        // output to JSON
        mapper.writeValue(outputFile, mockHouses);

        // success of test depends on whether file exists
        assertTrue(outputFile.exists());

        // check output is correct
        String expectedOutput = "[{\"house_id\":1,\"house_address\":\"10 Downing Street\"}]";
        StringBuilder actualOutput = new StringBuilder();
        try {
            File readBackIn = new File("test-output/house.json");
            Scanner reader = new Scanner(readBackIn);
            while (reader.hasNextLine()) {
                actualOutput.append(reader.nextLine());
            }
            reader.close();
        } catch (Exception e) {
            fail(); // failure
        }
        assertEquals(expectedOutput, actualOutput.toString());

        System.out.println("Test JSON for Houses at: " + outputFile.getAbsolutePath());
    }

    @Test
    void testGenerateJsonForStores() throws Exception {
        // mock the handler
        StoreHandler storeHandler = Mockito.mock(StoreHandler.class);

        // mock data for the stores
        List<Store> mockStores = List.of(
                new Store() {{ setStoreId(1); setStoreName("SuperMart"); setStoreLogo("supermart.png"); }}
        );

        // mock the handler behaviour
        when(storeHandler.getAll()).thenReturn(mockStores);

        // serialise the objects to the JSON file
        ObjectMapper mapper = new ObjectMapper();
        File outputFile = new File("test-output/store.json");

        // make the output directory if it doesn't already exist
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs();
        }

        // output to JSON
        mapper.writeValue(outputFile, mockStores);

        // success of test depends on whether file exists
        assertTrue(outputFile.exists());

        // check output is correct
        String expectedOutput = "[{\"store_id\":1,\"store_name\":\"SuperMart\",\"store_logo\":\"supermart.png\"}]";
        StringBuilder actualOutput = new StringBuilder();
        try {
            File readBackIn = new File("test-output/store.json");
            Scanner reader = new Scanner(readBackIn);
            while (reader.hasNextLine()) {
                actualOutput.append(reader.nextLine());
            }
            reader.close();
        } catch (Exception e) {
            fail(); // failure
        }
        assertEquals(expectedOutput, actualOutput.toString());

        System.out.println("Test JSON for Stores at: " + outputFile.getAbsolutePath());
    }
}