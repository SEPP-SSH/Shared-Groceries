package com.example.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Reads a JSON file and maps it to a list of objects of the given type.
     *
     * @param filePath The path to the JSON file.
     * @param clazz    The class type of the objects in the JSON array.
     * @param <T>      The type parameter.
     * @return A list of objects of type T.
     * @throws IOException If the file cannot be read or parsed.
     */
    public static <T> List<T> readJsonFile(String filePath, Class<T[]> clazz) throws IOException {
        T[] objects = mapper.readValue(new File(filePath), clazz); // Deserialize JSON array
        return List.of(objects); // Convert the array to a List
    }
}
