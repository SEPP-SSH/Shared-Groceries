package com.example.export;

import com.example.entities.*;
import com.example.repositories.HouseRepository;
import com.example.repositories.StoreRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.util.List;

import static org.mockito.Mockito.*;

class GenerateJsonFilesTest {

    @Test
    void testGenerateJsonForHouses() throws Exception {
        // Mock the repository
        HouseRepository houseRepository = Mockito.mock(HouseRepository.class);

        // Mock data for houses
        List<House> mockHouses = List.of(
                new House() {{setHouseId(1); setHouseAddress("123 Main Street"); }}
        );

        // Mock the repository behavior
        when(houseRepository.getAll()).thenReturn(mockHouses);

        // Serialize data to JSON
        ObjectMapper mapper = new ObjectMapper();
        File outputFile = new File("test-output/house.json");

        // Ensure output directory exists
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs();
        }

        // Write JSON to file
        mapper.writeValue(outputFile, mockHouses);

        // Verify repository call
        verify(houseRepository, times(1)).getAll();

        // Assert that file exists
        assert outputFile.exists();

        System.out.println("Generated test JSON file for houses: " + outputFile.getAbsolutePath());
    }

    @Test
    void testGenerateJsonForStores() throws Exception {
        // Mock the repository
        StoreRepository storeRepository = Mockito.mock(StoreRepository.class);

        // Mock data for stores
        List<Store> mockStores = List.of(
                new Store() {{ setStoreId(1); setStoreName("SuperMart"); setStoreLogo("supermart.png"); }}
        );

        // Mock the repository behavior
        when(storeRepository.getAll()).thenReturn(mockStores);

        // Serialize data to JSON
        ObjectMapper mapper = new ObjectMapper();
        File outputFile = new File("test-output/store.json");

        // Ensure output directory exists
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs();
        }

        // Write JSON to file
        mapper.writeValue(outputFile, mockStores);

        // Verify repository call
        verify(storeRepository, times(1)).getAll();

        // Assert that file exists
        assert outputFile.exists();

        System.out.println("Generated test JSON file for stores: " + outputFile.getAbsolutePath());
    }
}
