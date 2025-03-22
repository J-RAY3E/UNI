package org.example.ReaderManager.Parse;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.Classes.Worker;
import org.example.ReaderManager.Inputs.FileInputManager;
import org.example.ReaderManager.Validators.ValidationWorker;
import org.example.Storage.CollectionManager;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Handles reading and parsing JSON data into the collection.
 */
public final class ReadJSON {

    private final FileInputManager fileInputManager;

    /**
     * Constructs a ReadJSON instance for reading data.
     * @param dataFilePath the path to the JSON file.
     * @throws FileNotFoundException if the file does not exist.
     */
    public ReadJSON(String dataFilePath) throws FileNotFoundException {
        this.fileInputManager = new FileInputManager(dataFilePath);
    }

    /**
     * Loads data from the JSON file into the CollectionManager.
     * @param storageManager the CollectionManager to load data into.
     */
    public void loadData(CollectionManager storageManager) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            String json = fileInputManager.readAll();
            List<Worker> workers = objectMapper.readValue(json, new TypeReference<List<Worker>>() {});

            for (Worker worker : workers) {
                if (storageManager.getManagerID().isValidID(worker.getId())
                        && new ValidationWorker(false).validate(worker)) {
                    storageManager.getManagerID().addID(worker.getId());
                    storageManager.getCollection().add(worker);
                }
            }
            System.out.println("Data loaded successfully.");
        } catch (Exception e) {
            System.err.println("Error reading JSON data: " + e.getMessage());
        }
    }
}
