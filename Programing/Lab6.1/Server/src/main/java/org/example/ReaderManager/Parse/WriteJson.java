package org.example.ReaderManager.Parse;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.Storage.CollectionManager;


import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Handles saving the collection data to a JSON file.
 */
public final class WriteJson {

    private final String inputFileName;
    private final CollectionManager storageManager;

    /**
     * Constructs a WriteJSON instance for saving collection data.
     * @param storageManager the CollectionManager containing the data to be saved.
     */
    public WriteJson(CollectionManager storageManager) {
        this.storageManager = storageManager;
        this.inputFileName = storageManager.getLocalFile();
    }

    /**
     * Saves the current state of the collection to a JSON file.
     */
    public void saveCollectionToJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            String jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.storageManager.getCollection());

            try (BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(this.inputFileName))) {
                writer.write(jsonResult.getBytes());
                writer.flush();
            }

            System.out.println("Collection was successfully saved: " + inputFileName);
        } catch (IOException e) {
            System.err.println("Error while saving the JSON file: " + e.getMessage());
        }
    }
}
