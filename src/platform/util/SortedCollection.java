package platform.util;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Provides an ordered collection of elements.
 * @param <E> type of element, must be self-comparable
 */
public class SortedCollection<E extends Comparable<? super E>> extends AbstractCollection<E> {

    private LinkedList<E> list;
    
    /**
     * Creates a new empty ordered collection.
     */
    public SortedCollection() {
        list = new LinkedList<>();
    }
    
    /**
     * Creates a new ordered collection with specified elements.
     * @param other an existing collection of elements, not null
     */
    public SortedCollection(Collection<? extends E> other) {
        this();
        addAll(other);
    }
    
    @Override
    public boolean add(E e) {
        ListIterator<E> iterator = list.listIterator();
        while (iterator.hasNext()) {
            E next = iterator.next();
            if (e.compareTo(next) < 0) {
                iterator.previous();
                iterator.add(e);
                return true;
            }
        }
        list.add(e);
        return true;
    }
    
    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }
    
    @Override
    public int size() {
        return list.size();
    }

    /**
     * @return a reversed view of elements, not null
     */
    public Iterable<E> descending() {
        return () -> list.descendingIterator();
    }
    
}
