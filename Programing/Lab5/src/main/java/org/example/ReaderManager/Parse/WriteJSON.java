package org.example.ReaderManager.Parse;

import org.example.Storage.CollectionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class WriteJSON {
    private final String inputFileName;
    private final CollectionManager storageManager;

    public WriteJSON(CollectionManager storageManager) {
        this.inputFileName = System.getenv("data");
        this.storageManager = storageManager;
    }
    public void saveCollectionToJson() {
        try {

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            String jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.storageManager.getCollection());

            try (BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(System.getenv(this.inputFileName)))) {
                writer.write(jsonResult.getBytes());
                writer.flush();
            }

            System.out.println("Collection was successfully saved" + inputFileName);
        } catch (IOException e) {
            System.err.println("Error meanwhile saving the file JSON: " + e.getMessage());
        }
    }
}