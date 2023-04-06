import org.junit.Test;

import static org.junit.Assert.*;

public class LibraryDatabaseTest {

    public Book a = new Book("The Fear", "4867564321651", "Jonathan");
    public Book b = new Book("Witcher", "5587974321542", "Nathan");
    public Book c = new Book("Alice in the Wonderland", "6933214565763", "Anne");
    public Book d = new Book("Happiness", "7976567843212", "Maria");
    public Book e = new Book("Cap", "8681234325465", "Carlos");
    public Book f = new Book("My Boots", "9127648739201", "Ray");
    public Book g = new Book("The Fear", "4867564321651", "Jonathan");
    public Book h = new Book("The Fear", "4867564321651", "Jonathan");

    @Test
    public void testConstructor() {
        //Negative capacity or 0
        //assertThrows(IllegalArgumentException.class ,() -> new LibraryDatabase(-1));
        //assertThrows(IllegalArgumentException.class ,() -> new LibraryDatabase(0));
    }

    @Test
    public void testAddEmptyList() {
        LibraryDatabase library = new LibraryDatabase(5);
        //Adding to an empty list
        Book[] expected = {a, null, null, null, null};
        library.add(a);
        assertArrayEquals(expected, library.toArray());
    }

    @Test
    public void testAddInOrder() {
        LibraryDatabase library = new LibraryDatabase(5);
        //Adding books in order
        Book[] expected = {a, b, c, d, null};
        library.add(a);
        library.add(b);
        library.add(c);
        library.add(d);
        assertArrayEquals(expected, library.toArray());
    }

    @Test
    public void testAddOutOfOrder() {
        LibraryDatabase library = new LibraryDatabase(5);
        //Adding books out of order keeping sorting
        Book[] expected = {a, b, c, d, null};
        library.add(d);
        library.add(c);
        library.add(b);
        library.add(a);
        assertArrayEquals(expected, library.toArray());
    }

    @Test
    public void testAddBookInFullArray() {
        LibraryDatabase library = new LibraryDatabase(5);
        //Adding books out of order keeping sorting
        Book[] expected = {a, b, c, d, e, f};
        library.add(d);
        library.add(c);
        library.add(b);
        library.add(a);
        library.add(e);
        library.add(f);
        assertArrayEquals(expected, library.toArray());
    }

    @Test
    public void testRemoveFromBeginning() {
        LibraryDatabase library = new LibraryDatabase(5);
        //Remove from beginning
        Book[] expected = {b, c, d, null, null};
        library.add(a);
        library.add(b);
        library.add(c);
        library.add(d);
        Book removed  = library.remove("4867564321651");
        assertArrayEquals(expected, library.toArray());
        assertEquals(removed, a);
    }

    @Test
    public void testRemoveFromMiddle() {
        LibraryDatabase library = new LibraryDatabase(5);
        //Remove from beginning
        Book[] expected = {a, b, d, null, null};
        library.add(a);
        library.add(b);
        library.add(c);
        library.add(d);
        Book removed = library.remove("6933214565763");
        assertArrayEquals(expected, library.toArray());
        assertEquals(removed, c);
    }

    @Test
    public void testRemoveFromLast() {
        LibraryDatabase library = new LibraryDatabase(5);
        //Remove from beginning
        Book[] expected = {a, b, c, null, null};
        library.add(a);
        library.add(b);
        library.add(c);
        library.add(d);
        Book removed = library.remove("7976567843212");
        assertArrayEquals(expected, library.toArray());
        assertEquals(removed, d);
    }

    @Test
    public void testSize() {
        LibraryDatabase library = new LibraryDatabase(5);
        library.add(a);
        library.add(b);
        library.add(c);
        assertEquals(3, library.size());
        library.remove("4867564321651");
        assertEquals(2, library.size());
        library.add(d);
        library.add(f);
        library.add(g);
        assertEquals(5, library.size());
    }

    @Test
    public void testRandomSelection() {
        //Empty list
        LibraryDatabase library = new LibraryDatabase(5);
        assertNull(library.randomSelection());
        //Full list
        library.add(a);
        library.add(b);
        library.add(d);
        library.add(c);
        library.add(e);
        double aOccurrences = 0;
        for (int i = 0; i < 10000; i++) {
            if (a == library.randomSelection()){
                aOccurrences += 1.0;
            }
        }
        System.out.println("Test for randomSelection() among 5 elements.\n" +
                "Expected: as close to 20%\n" +
                "Actual: " + (aOccurrences / 10000) * 100 + " %");
    }

    @Test
    public void testFindByTitle() {
        LibraryDatabase library = new LibraryDatabase(5);
        library.add(a);
        library.add(b);
        library.add(d);
        library.add(c);
        library.add(e);
        assertEquals(c, library.findByTitle("Alice in the Wonderland"));
        assertEquals(a, library.findByTitle("The Fear"));
        assertEquals(d, library.findByTitle("Happiness"));
        assertEquals(null, library.findByTitle("Non Existent"));
    }

    @Test
    public void testFindByISBN() {
        LibraryDatabase library = new LibraryDatabase(5);
        library.add(a);
        library.add(b);
        library.add(d);
        library.add(c);
        library.add(e);
        assertEquals(c, library.findByISBN("6933214565763"));
        assertEquals(a, library.findByISBN("4867564321651"));
        assertEquals(b, library.findByISBN("5587974321542"));
        assertEquals(null, library.findByISBN("8786756432123"));
    }

    @Test
    public void testGetAllByAuthor() {
        LibraryDatabase library = new LibraryDatabase(5);
        Book[] expected = {a, g, h, null, null, null, null, null};
        library.add(a); //same author
        library.add(b);
        library.add(d);
        library.add(c);
        library.add(e);
        library.add(f);
        library.add(g); //same author
        library.add(h); //same author
        assertArrayEquals(expected, library.getAllByAuthor("Jonathan"));
    }

    @Test
    public void removeDuplicates() {
        LibraryDatabase library = new LibraryDatabase(5);
        Book[] expected = {h, b, c, d, e, f, null, null};
        library.add(a); //same ISBN
        library.add(b);
        library.add(d);
        library.add(c);
        library.add(e);
        library.add(f);
        library.add(g); //same ISBN
        library.add(h); //same ISBN
        library.removeDuplicates();
        assertArrayEquals(expected, library.toArray());
    }

    @Test
    public void removeMultipleDuplicates() {
        LibraryDatabase library = new LibraryDatabase(5);
        Book[] expected = {h, b, c, d, e, f, null, null, null, null, null, null};
        library.add(a); //same ISBN
        library.add(b);
        library.add(d);
        library.add(c);
        library.add(c);
        library.add(c);
        library.add(e);
        library.add(f);
        library.add(f);
        library.add(f);
        library.add(g); //same ISBN
        library.add(h); //same ISBN
        library.removeDuplicates();
        assertArrayEquals(expected, library.toArray());
    }

    @Test
    public void testToArray() {
        LibraryDatabase library = new LibraryDatabase(5);
        Book[] expected = {a, b, c, d, e};
        library.add(e);
        library.add(b);
        library.add(a);
        library.add(c);
        library.add(d);
        assertArrayEquals(expected, library.toArray());
    }
}