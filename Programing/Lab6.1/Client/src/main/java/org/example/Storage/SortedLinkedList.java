package Storage;

import java.util.Collections;
import java.util.LinkedList;


/**
 * A sorted linked list implementation that maintains its elements in a sorted order.
 * @param <T> the type of elements in this list, which must be comparable.
 */
public final class SortedLinkedList<T extends Comparable<T>> extends LinkedList<T> {

    /**
     * Adds an element to the list while ensuring it remains sorted.
     * @param element the element to be added.
     * @return true if the element was added successfully.
     */
    @Override
    public boolean add(T element) {
        super.add(element);
        Collections.sort(this);
        return true;
    }

    /**
     * Sets an element at a specified position while ensuring the list remains sorted.
     * @param index the index at which the element should be set.
     * @param element the new element.
     * @return the element that was set.
     */
    @Override
    public T set(int index, T element) {
        super.set(index, element);
        Collections.sort(this);
        return element;
    }
}
