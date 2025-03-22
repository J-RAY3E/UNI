package org.example.readerManager;

import java.util.LinkedList;
import java.util.List;

/**
 * Manages unique ID generation for entities in the collection.
 */
public final class ManagerId {

    private final List<Integer> ids = new LinkedList<>();
    private Integer currIdx = 0;

    /**
     * Generates a unique ID that is not in use.
     * @return A unique ID.
     */
    public Integer generateID() {
        do {
            ++currIdx;
        } while (!isValidID(currIdx));
        addID(currIdx);
        return currIdx;
    }

    /**
     * Checks if an ID is valid and not already in use.
     * @param id The ID to check.
     * @return True if the ID is valid, false otherwise.
     */
    public boolean isValidID(Integer id) {
        return id != null && !ids.contains(id);
    }

    /**
     * Adds a new ID to the list of used IDs.
     * @param id The ID to add.
     */
    public void addID(Integer id) {
        ids.add(id);
    }
}
