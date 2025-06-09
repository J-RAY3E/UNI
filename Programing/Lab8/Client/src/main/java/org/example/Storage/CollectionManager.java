package org.example.Storage;


import org.example.Classes.Worker;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Manages the collection of Worker objects, providing functionality
 * for data loading, retrieval, and storage operations.
 */
public final class CollectionManager {

    private final LocalDateTime dataCreation;
    private final String classList;
    private final List<Worker> collection;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * Constructs a CollectionManager and initializes storage-related fields.
     */
    public CollectionManager() {
        this.collection = new LinkedList<>();
        this.dataCreation = LocalDateTime.now();
        this.classList = collection.getClass().toString();

    }

}

