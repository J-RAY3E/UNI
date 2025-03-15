package org.example.ReaderManager.Parse;

import org.example.ReaderManager.Inputs.FileInputManager;
import org.example.ReaderManager.TypeValidation.ValidationWorker;
import org.example.Storage.CollectionManager;



import org.example.Classes.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

public class ReadJSON {

    final FileInputManager fileInputManager;
    public ReadJSON (String  DataVariable ) throws FileNotFoundException {
        this.fileInputManager = new FileInputManager(DataVariable);
    }

    public void loadData(CollectionManager storageManager) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            String json = fileInputManager.readAll();
            List<Worker> workers = objectMapper.readValue(json, new TypeReference<>() {
            });

            for (Worker worker : workers) {
                if(new ValidationWorker(false).validate(worker)){
                    storageManager.addElement(worker);
                }
            }

            System.out.println("Data loaded successfully.");
        } catch (Exception e) {
            System.out.println("Error at moment reading file, there is no type JSON");
        }

    }
}
