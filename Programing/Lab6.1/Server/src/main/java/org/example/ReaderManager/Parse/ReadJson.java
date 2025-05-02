package org.example.ReaderManager.Parse;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import org.example.Classes.Worker;
import org.example.ReaderManager.Inputs.FileInputManager;
import org.example.ReaderManager.Validators.ValidationWorker;
import org.example.Storage.CollectionManager;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles reading and parsing JSON data into the collection.
 */
public final class ReadJson {

    private final FileInputManager fileInputManager;

    /**
     * Constructs a ReadJSON instance for reading data.
     * @param dataFilePath the path to the JSON file.
     * @throws FileNotFoundException if the file does not exist.
     */
    public ReadJson(String dataFilePath) throws FileNotFoundException {
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

            json = json.trim();
            if (!json.startsWith("[") || !json.endsWith("]")) {
                System.err.println("Error: El JSON no es un array válido.");
                return;
            }

            String[] objects = json.substring(1, json.length() - 1).split("},\\s*\\{");

            List<Worker> validWorkers = new ArrayList<>();
            int index = 0;

            for (String obj : objects) {
                try {
                    if (index > 0) obj = "{" + obj;
                    if (index < objects.length - 1) obj = obj + "}";

                    Worker worker = objectMapper.readValue(obj, Worker.class);

                    if (storageManager.getManagerID().isValidID(worker.getId())
                            && new ValidationWorker(false).validate(worker)) {
                        storageManager.getManagerID().addID(worker.getId());
                        validWorkers.add(worker);
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                index++;
            }

            storageManager.getCollection().addAll(validWorkers);
            System.out.println("Data loaded successfully");

        } catch (Exception e) {
            System.err.println("Error general al leer JSON: " + e.getMessage());
        }
    }


}
