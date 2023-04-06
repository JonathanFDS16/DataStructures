import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;



public class HashTableTest {

    /**
     * Testing put(), get(), remove() & size() all together.
     *  1. When putting an K,V object size should increase.
     *  2. If adding an element with same K size should not increase, only its value.
     *  3. Removing an K,V should return V and decrease HashTable size.
     *  If Key does not exist a NoSuchElementException should be thrown.
     */

    @Test
    public void hashTableTest() {
        HashTable<Integer> table = new HashTable<>();
        // 1.
        table.put("one", 1);
        table.put("two", 2);
        table.put("three", 3);
        assertEquals(3, table.size());
        //2.
        table.put("two", 10);
        assertEquals(3, table.size());
        assertEquals((Integer)10 , table.get("two"));
        // 3.
        assertEquals((Integer)3, table.remove("three"));
        assertEquals(2, table.size());
        assertThrows(NoSuchElementException.class, () -> {
            table.get("four");
        });
    }
}