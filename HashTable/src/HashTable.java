/**
 * HashTable implementation.
 * @author Jonathan F Da Silva
 */

import java.util.NoSuchElementException;

public class HashTable<T> {

    private Entry<T>[] table;
    private int tableSize;

    private int size;

    /**
     * Creates a HashTable with a default capacity.
     */
    public HashTable() {
        table = new Entry[13];
        tableSize = 13;
    }

    /**
     * Creates a HashTable with size capacity.
     * @param size the size of the HashTable
     */
    public HashTable(int size) {
        if (size < 0)
            throw new IllegalArgumentException();
        table = new Entry[size];
        tableSize = size;
    }

    /**
     * Get the value associated with a specific key.
     * @param key the specific key
     * @return the value associated.
     */
    public T get(String key) {
        Entry<T> e = table[hash(key)];
        // Loop though a "Linked List" of entries.
        while (e != null) {
            if (e.getKey().equals(key)) {
                return e.getValue();
            }
            e = e.getNext();
        }
        throw new NoSuchElementException();
    }

    /**
     * Adds a key-value pair object to the hashtable.
     * @param key the key
     * @param value the value
     */
    public void put(String key, T value) {
        Entry<T> e = table[hash(key)];
        // If there is nothing in the index, create a new entry
        if (e == null) {
            table[hash(key)] = new Entry<>(key, value);
            size += 1;
        }
        else {
            // Loop through the "Linked List"
            while (e.getNext() != null && !e.getKey().equals(key)) {
                e = e.getNext();
            }
            // If entry found by the loop is has the same key set the specific value for the given value.
            if (e.getKey().equals(key)) {
                e.setValue(value);
            }
            // Else just create another entry
            else {
                e.setNext(new Entry<>(key, value));
                size += 1;
            }
        }

        if (loadFactor() == 1) {
            rehash();
        }
    }

    /**
     * Removes a key from the HashTable.
     * @param key the key to be searched.
     * @return the value associated with the key.
     */
    public T remove(String key) {
        Entry<T> e = table[hash(key)];
        // If index is not empty
        if (e != null) {
            // If first entry has the same key. Set the table to point to the next Entry.
            if (e.getKey().equals(key)) {
                Entry<T> removed = table[hash(key)];
                table[hash(key)] = e.getNext();
                size -= 1;
                return removed.getValue();
            }
            else {
                Entry<T> removed;
                // Loop through the next Entries
                while (e.getNext() != null) {
                    // If an Entry has the same key, remove/
                    if (e.getNext().getKey().equals(key)) {
                        removed = e.getNext();
                        e.setNext(e.getNext().getNext());
                        size -= 1;
                        return removed.getValue();
                  }
                  e = e.getNext();
                }
                throw new NoSuchElementException();
            }
        } else throw new NoSuchElementException();
    }

    /**
     * Return the amount of elements is present in the HashTable.
     * @return
     */
    public int size() {
        return size;
    }

    // Rehash the HashTable
    private void rehash() {
        Entry<T>[] oldTable = table;

        tableSize = tableSize * 2;
        table = new Entry[tableSize];
        setSize(0);
        // Loop that rehash the whole table.
        for (Entry<T> e : oldTable) {
            while (e != null) {
                put(e.getKey(), e.getValue());
                e = e.getNext();
            }
        }
    }

    private int hash(String key) {
        return Math.abs(key.hashCode() % tableSize);
    }

    private int loadFactor() {
        return this.size() / tableSize;
    }

    private void setSize(int size) {
        this.size = size;
    }

    /**
     * Entry class that holds Key and T value.
     * @param <T> The type to be held by the Entry
     */
    public class Entry<T> implements Comparable<Entry> {
        private String key;

        private T value;

        private Entry<T> next;

        public Entry(String key, T value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }


        public void setNext(Entry<T> next) {
            this.next = next;
        }

        public Entry<T> getNext() {
            return next;
        }

        @Override
        public int compareTo(Entry o) {
            // If T value is an Integer
            if (o.getValue() instanceof Integer) {
                int i = (Integer)o.getValue();
                int result = i - (Integer)this.getValue();
                // If both have the same ranking, then use Spring's compareTo()
                if (result == 0)
                    return this.getKey().compareTo(o.getKey());
                else return result;
            }
            else return 0;
        }
    }
}
