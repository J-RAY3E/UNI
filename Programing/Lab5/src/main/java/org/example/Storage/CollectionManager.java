package org.example.storage;

import org.example.classes.Worker;
import org.example.readerManager.ManagerId;
import org.example.readerManager.parse.ReadJson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * Manages the collection of Worker objects, providing functionality
 * for data loading, retrieval, and storage operations.
 */
public final class CollectionManager {

    private final LocalDateTime dataCreation;
    private final String classList;
    private final String localFile;
    private final SortedLinkedList<Worker> collection;
    private final ManagerId managerID;

    /**
     * Constructs a CollectionManager and initializes storage-related fields.
     */
    public CollectionManager() {
        this.collection = new SortedLinkedList<>();
        this.dataCreation = LocalDateTime.now();
        this.classList = collection.getClass().toString();
        this.managerID = new ManagerId();
        this.localFile = System.getenv("data");
    }

    /**
     * Loads data from a JSON file into the collection.
     */
    public void load() {
        try {
            new ReadJson(this.localFile).loadData(this);
        } catch (Exception e) {
            System.out.println("No data loaded in the collection" + e.getMessage());
        }
    }

    /**
     * Gets the file path of the data source.
     * @return the file path as a string.
     */
    public String getLocalFile() {
        return this.localFile;
    }

    /**
     * Gets the collection of Worker objects.
     * @return the sorted linked list of workers.
     */
    public List<Worker> getCollection() {
        return collection;
    }

    /**
     * Gets the ManagerID instance for handling unique identifiers.
     * @return the ManagerID instance.
     */
    public ManagerId getManagerID() {
        return managerID;
    }

    /**
     * Gets the name of the class that represents the collection.
     * @return the class name.
     */
    public String getClassList() {
        return this.classList;
    }

    /**
     * Provides information about the collection.
     * @return a formatted string with collection details.
     */
    public String getInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.format(
                "Data creation: %s%nNumber of elements: %d%nStorage: %s%n",
                this.dataCreation.format(formatter), this.collection.size(), this.getClassList()
        );
    }
}

